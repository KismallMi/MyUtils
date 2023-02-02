package com.kismallmi.myutils.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kismallmi.myutils.entity.User;
import com.kismallmi.myutils.service.UserService;
import com.kismallmi.myutils.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




