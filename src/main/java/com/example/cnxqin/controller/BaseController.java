package com.example.cnxqin.controller;

import com.example.cnxqin.common.exception.ErrorCode;
import com.example.cnxqin.vo.output.Response;

/**
 * @author cnxqin
 * @desc
 * @date 2021/01/22 22:54
 */
public class BaseController {

    protected Response success(){
        return new Response();
    }

    protected Response success(Object object){
        return new Response(object);
    }

    protected Response fail(ErrorCode errorCode){
        return new Response(errorCode);
    }


}
