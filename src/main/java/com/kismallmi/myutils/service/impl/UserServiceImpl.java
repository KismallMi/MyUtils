package com.kismallmi.myutils.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kismallmi.myutils.common.ErrorCode;
import com.kismallmi.myutils.entity.User;
import com.kismallmi.myutils.exception.BusinessException;
import com.kismallmi.myutils.service.UserService;
import com.kismallmi.myutils.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jasypt.util.digest.Digester;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;

/**
 *
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper,User>
        implements UserService{
    @Resource
    private UserMapper userMapper;

    /**
     * 盐值
     */
    private static final String SALT = "Jane";

    @Override
    public long userRegister(String userName, String userAccount, String userPassword, String checkPassword, String userRole) {
        if (StringUtils.isAnyBlank(userName, userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userName.length() > 16) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名过长");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入密码不一致");
        }

        synchronized (userAccount.intern()) {
            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(User::getUserAccount, userAccount);
            long count = userMapper.selectCount(wrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
            }

            //2.加密
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
            //插入数据
            User user = new User();
            user.setUserAccount(userAccount);
            user.setUserName(userName);
            user.setUserRole(userRole);
            user.setUserPassword(encryptPassword);
            boolean saveResult = this.save(user);
            if (!saveResult){
                throw new BusinessException(ErrorCode.SYSTEM_ERROR,"数据库错误");
            }
            return user.getId();

        }



    }


}




