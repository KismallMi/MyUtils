package com.kismallmi.myutils.service;

import com.kismallmi.myutils.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface UserService extends IService<User>{
    /**
     *用户注册
     * @param userName 用户名
     * @param userAccount 账户
     * @param userPassword 密码
     * @param checkPassword 校验密码
     * @param userRole 用户角色
     * @return 新用户id
     */
    long userRegister(String userName, String userAccount, String userPassword, String checkPassword,String userRole);
}
