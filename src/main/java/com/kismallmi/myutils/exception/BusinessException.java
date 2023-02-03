package com.kismallmi.myutils.exception;

import com.kismallmi.myutils.common.ErrorCode;

/**
 * @version 1.0
 * @Author wangjian
 * @since 2023/2/2
 */
public class BusinessException extends RuntimeException{
    private final int code;

    public BusinessException(String message, int code) {
        super(message);
        this.code = code;
    }

    public BusinessException(ErrorCode code) {
        super(code.getMessage());
        this.code = code.getCode();
    }

    public BusinessException(ErrorCode errorCode,String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    public int getCode() {
        return code;
    }
}
