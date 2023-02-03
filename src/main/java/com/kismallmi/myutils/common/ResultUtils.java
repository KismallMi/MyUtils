package com.kismallmi.myutils.common;

/**
 * @version 1.0
 * @Author wangjian
 * @since 2023/2/2
 */
public class ResultUtils{
    /**
     * 成功
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, data);
    }

    /**
     * 失败
     * @param code
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> error(ErrorCode code) {
        return new BaseResponse<>(code);
    }

    /**
     * 失败
     * @param code
     * @param message
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> error(int code,String message){
        return new BaseResponse<>(code,null,message);
    }

    /**
     * 失败
     * @param errorCode
     * @param message
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> error(ErrorCode errorCode,String message){
        return new BaseResponse<>(errorCode.getCode(),null,message);
    }
}
