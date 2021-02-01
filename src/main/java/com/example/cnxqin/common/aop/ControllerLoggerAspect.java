package com.example.cnxqin.common.aop;

import com.alibaba.fastjson.JSONObject;
import com.example.cnxqin.common.exception.BusinessException;
import com.example.cnxqin.vo.output.Response;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author cnxqin
 * @desc
 * @date 2021/01/23 00:37
 */
@Aspect
@Order(Ordered.LOWEST_PRECEDENCE)
@Component
public class ControllerLoggerAspect {

    private static final Logger log = LoggerFactory.getLogger(ControllerLoggerAspect.class);

    @Pointcut(
            "execution(* com.example.cnxqin.*.*Controller.*(..)) " +
                    "|| execution(* com.example.cnxqin.controller.*.*Controller.*(..))" +
                    "|| execution(* com.example.cnxqin.controller.*Controller.*(..))")
    public void apiPointcut() {
    }

    @Around("apiPointcut()")
    public Response around(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String className = joinPoint.getTarget().getClass().getName();
        String method = joinPoint.getSignature().getName();
        String methodParam = JSONObject.toJSONString(joinPoint.getArgs());

        StringBuilder sb = new StringBuilder();
        sb.append("- request: [").append(className).append(".").append(method).append("] ")
                .append("[").append(request.getMethod()).append("] ")
                .append("- params: ").append(methodParam).append(" ");

        Object[] args = joinPoint.getArgs();
        Response response = null;
        try {
            response = (Response)joinPoint.proceed(args);
        }catch (BusinessException e){
            sb.append("\n- response: ").append(System.currentTimeMillis() - start).append("ms").append(" - ").append(e.getMessage());
            log.warn(sb.toString(), e);
            return Response.exception(e);
        }catch (Exception e){
            log.error(sb.toString(), e);
            return Response.fail(e.getMessage());
        }
        sb.append("\n- response: ").append(System.currentTimeMillis() - start).append("ms").append(" - ");
        if(Objects.nonNull(response)){
            sb.append(JSONObject.toJSONString(response));
        }
        log.info(sb.toString());
        return response;
    }

}
