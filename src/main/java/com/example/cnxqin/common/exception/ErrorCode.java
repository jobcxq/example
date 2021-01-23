package com.example.cnxqin.common.exception;

/**
 * @author cnxqin
 * @desc
 * @date 2021/01/22 22:51
 */
public enum ErrorCode {

    SUCCESS(1000,"成功"),
    SYSTEM_ERROR(1001,"服务器处理异常，请稍后再试"),
    TOKEN_INVALID(1002,"Token 不合法，请重新登录"),
    USER_ID_INVALID(1003,"User-Id 不能为空"),
    PARAM_INVALID(1004,"请求参数不合法"),

    ;

    public int code;

    public String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
