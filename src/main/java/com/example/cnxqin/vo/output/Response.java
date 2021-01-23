package com.example.cnxqin.vo.output;

import com.example.cnxqin.common.exception.ErrorCode;
import lombok.Data;

/**
 * @author cnxqin
 * @desc
 * @date 2021/01/22 22:59
 */
@Data
public class Response {

    private int code;
    private String message;

    private Object context;

    public Response(){
        this.code = ErrorCode.SUCCESS.code;
        this.message = ErrorCode.SUCCESS.message;
    }

    public Response(Object context){
        this.code = ErrorCode.SUCCESS.code;
        this.message = ErrorCode.SUCCESS.message;
        this.context = context;
    }

    public Response(ErrorCode errorCode){
        this.code = errorCode.code;
        this.message = errorCode.message;
    }

}
