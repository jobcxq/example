package com.example.cnxqin.common.exception;

import lombok.Data;

/**
 * @author cnxqin
 * @desc
 * @date 2021/01/24 19:35
 */
@Data
public class BusinessException extends RuntimeException{

    private int errorCode;
    private String message;

    public BusinessException(ErrorCode errorCode){
        super(String.format("[%s] %s", errorCode.code, errorCode.message));
        this.errorCode = errorCode.code;
        this.message = errorCode.message;
    }

    public BusinessException(String message){
        this.errorCode = ErrorCode.OTHER_ERROR.code;
        this.message = message;
    }

}
