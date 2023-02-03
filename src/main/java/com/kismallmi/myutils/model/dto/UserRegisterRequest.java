package com.kismallmi.myutils.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @version 1.0
 * @Author wangjian
 * @since 2023/2/2
 */
@Data
public class UserRegisterRequest implements Serializable{

    private String userName;

    private String userAccount;

    private String userPassword;

    private String checkPassword;

    private static final long serialVersionUID = -114864342490648427L;
}
