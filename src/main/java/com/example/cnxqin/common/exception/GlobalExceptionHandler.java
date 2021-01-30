package com.example.cnxqin.common.exception;

import com.example.cnxqin.vo.output.Response;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author cnxqin
 * @desc 全局异常处理器
 * @date 2021/01/24 20:43
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 校验异常
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Response exceptionHandler(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        return Response.fail(getErrorMessage(bindingResult));
    }

    private String getErrorMessage(BindingResult bindingResult){
        return String.format("[%s] %s", bindingResult.getFieldError().getField(), bindingResult.getFieldError().getDefaultMessage());
    }

}
