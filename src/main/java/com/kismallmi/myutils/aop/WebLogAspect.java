package com.kismallmi.myutils.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kismallmi.myutils.entity.Dict;
import com.kismallmi.myutils.entity.Report;
import com.kismallmi.myutils.service.DictService;
import com.kismallmi.myutils.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @version 1.0
 * @Author wangjian
 * @since 2023/2/3
 */
@Aspect
@Component
@Slf4j
public class WebLogAspect{

    /**
     * 对controller下的接口进行日志记录
     */
    @Pointcut("@annotation(LogAnnotation)")
    public void webLog() {

    }


    private static List<String> typeList;

    static {
        String[] types = {
                "int", "double", "long", "short", "byte",
                "boolean", "char", "float", "String", "Byte",
                "Boolean", "Character", "Short", "Float", "Integer",
                "Long", "Double"};
        typeList = new ArrayList<String>(Arrays.asList(types));
    }

    @Around("webLog()")
    public Object around(ProceedingJoinPoint point) throws Exception {
        Object result = null;
        boolean isSuccess = true;
        try {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();
            String method = request.getMethod();
            String remoteAddr = request.getRemoteAddr();
            String requestURL = request.getRequestURL().toString();
            Signature signature = point.getSignature();


            JSONObject object = new JSONObject();
            object.put("methodType", method);
            object.put("remoteAddr", remoteAddr);
            object.put("requestURL", requestURL);
            object.put("method", String.format("%s.%s()", signature.getDeclaringTypeName(), signature.getName()));
            Object[] args = point.getArgs();
            result = point.proceed(args);
            Method m = ((MethodSignature) point.getSignature()).getMethod();
            LogAnnotation param = m.getAnnotation(LogAnnotation.class);
            String[] values = param.value();
            System.out.println(Arrays.toString(values));
            log.info("后置通知启动,记录执行结果:{}", object);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            isSuccess = false;
            log.error("异常通知启动,失败原因:{}", throwable.getMessage());
        } finally {
            Dict dict = new Dict();


            Map<String,String> map = getMethodRequestParameters(point, isSuccess);
            log.info("{}",map.toString());

            log.info("{}", StringEscapeUtils.unescapeJava(JSONObject.toJSONString(map)));

        }

        return result;
    }

    /**
     * 获取方法的请求参数的名称-值
     * k-v
     * @param point
     * @param isSuccess
     * @return
     */
    private Map getMethodRequestParameters(ProceedingJoinPoint point, boolean isSuccess) {
        Signature signature = point.getSignature();
        Object[] args = point.getArgs();
        log.info("最终通知启动,插入数据库:{},请求是否成功:{}", JSONObject.toJSON(args), isSuccess);
        MethodSignature methodSignature = (MethodSignature) signature;
        String[] parameterNames = methodSignature.getParameterNames();


        Map paramMap = new HashMap();
        if (parameterNames != null && parameterNames.length > 0 && args != null && args.length > 0) {
            for (int i = 0; i < parameterNames.length; i++) {
                //去除request/response
                if (args[i] instanceof ServletRequest || args[i] instanceof ServletResponse)
                    continue;
                //去除数组的形式
                String arg = args[i].getClass().getSimpleName();
                if (arg.contains("[]"))
                    arg = arg.replace("[]", "");

                //考虑入参要么为基础类型参数，要么为对象类型。以下方法都适合解析出来
                if (typeList.contains(arg)) {
                    //基本数据类型
                    paramMap.put(parameterNames[i], JSON.toJSONString(args[i]));
                } else {
                    // 文件类型获取上传的名称
                    if (args[i] instanceof MultipartFile) {
                        MultipartFile file = (MultipartFile) args[i];
                        paramMap.put(parameterNames[i], JSON.toJSONString(file.getOriginalFilename()));
                    }
                    //实体类
                    paramMap = JSON.parseObject(JSON.toJSONString(args[i]));

                }
            }

        }
        return paramMap;
    }

    /**
     * 获取参数类型和参数明
     *
     * @param method
     * @return
     */
    private Map<String,String> getMethodArgumentTypeName(MethodSignature method) {
        Map<String,String> map = new HashMap<>();
        String[] argTypeNames = method.getParameterNames();
        Class[] parameterTypes = method.getParameterTypes();
        for (int i = 0; i < parameterTypes.length; i++) {
            map.put(parameterTypes[i].getName(), argTypeNames[i]);
        }
        return map;
    }


    public enum RequestType{

        /*
         根据第一级路径判断来源
         */

        ;

    }
}
