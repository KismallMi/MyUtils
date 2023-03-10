package com.kismallmi.myutils.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kismallmi.myutils.aop.LogAnnotation;
import com.kismallmi.myutils.common.BaseResponse;
import com.kismallmi.myutils.common.ErrorCode;
import com.kismallmi.myutils.common.ResultUtils;
import com.kismallmi.myutils.constant.UserConstant;
import com.kismallmi.myutils.entity.Dict;
import com.kismallmi.myutils.exception.BusinessException;
import com.kismallmi.myutils.model.dto.UserRegisterRequest;
import com.kismallmi.myutils.service.DictService;
import com.kismallmi.myutils.service.UserService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @version 1.0
 * @Author wangjian
 * @since 2023/2/2
 */
@RestController
@RequestMapping("/user")
public class UserController{
    @Resource
    private UserService userService;
    @Autowired
    private DictService dictService;

    @PostMapping("/register")
    @LogAnnotation
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest){

        if (userRegisterRequest==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userName = userRegisterRequest.getUserName();
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();

        if (StringUtils.isAnyBlank(checkPassword,userAccount,userPassword)){
            return null;
        }

        long result = userService.userRegister(userName, userAccount, userPassword, checkPassword, UserConstant.DEFAULT_ROLE);

        return ResultUtils.success(result);
    }

    @DeleteMapping("/register/{name}")
    @LogAnnotation("name")
    public BaseResponse<String> userRegister(@PathVariable String name){
        return ResultUtils.success(name);

    }


    @GetMapping("/register")
    @LogAnnotation(value={"age","name"})
    public BaseResponse<Object> query(@RequestParam("age") String age, @RequestParam("name") String[] name){
        List<Dict> list = dictService.list();

        return ResultUtils.success(list);

    }


}
