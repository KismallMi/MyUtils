package com.kismallmi.myutils.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

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

    @Around("webLog()")
    public Object around(ProceedingJoinPoint point) {
        Object result = null;
        boolean isSuccess=true;
        try {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();
            String method = request.getMethod();
            String remoteAddr = request.getRemoteAddr();
            String requestURL = request.getRequestURL().toString();
            Signature signature = point.getSignature();


            JSONObject object = new JSONObject();
            object.put("methodType",method);
            object.put("remoteAddr",remoteAddr);
            object.put("requestURL",requestURL);
            object.put("method",String.format("%s.%s()",signature.getDeclaringTypeName(),signature.getName()));
            Object[] args = point.getArgs();
            result = point.proceed(args);
            log.info("后置通知启动,记录执行结果:{}",object);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            isSuccess=false;
            log.error("异常通知启动,失败原因:{}",throwable.getMessage());
        }finally {
            log.info("最终通知启动,插入数据库:{},请求是否成功:{}", JSONObject.toJSON((Arrays.stream(point.getArgs()).findFirst())),isSuccess);
        }

        return result;
    }

}
