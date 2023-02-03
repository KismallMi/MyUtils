package com.kismallmi.myutils.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @version 1.0
 * @Author wangjian
 * @since 2023/2/2
 */
@Data
public class BaseResponse<T> implements Serializable{
    private int code;
    private T data;
    private String message;

    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public BaseResponse(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage());
    }
}
