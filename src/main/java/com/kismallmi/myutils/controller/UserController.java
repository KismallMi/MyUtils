package com.kismallmi.myutils.controller;

import com.kismallmi.myutils.common.BaseResponse;
import com.kismallmi.myutils.common.ErrorCode;
import com.kismallmi.myutils.common.ResultUtils;
import com.kismallmi.myutils.constant.UserConstant;
import com.kismallmi.myutils.exception.BusinessException;
import com.kismallmi.myutils.model.dto.UserRegisterRequest;
import com.kismallmi.myutils.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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



}
