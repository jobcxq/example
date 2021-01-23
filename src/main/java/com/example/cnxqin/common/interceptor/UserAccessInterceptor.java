package com.example.cnxqin.common.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.example.cnxqin.common.constant.ConfigConstant;
import com.example.cnxqin.common.constant.RequestConstant;
import com.example.cnxqin.common.exception.ErrorCode;
import com.example.cnxqin.common.util.TokenUtils;
import com.example.cnxqin.vo.output.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author cnxqin
 * @desc
 * @date 2021/01/22 21:41
 */
public class UserAccessInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(UserAccessInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String userId = request.getHeader(RequestConstant.USER_ID);
        if(StringUtils.isBlank(userId)){
            writeFailResponse(ErrorCode.USER_ID_INVALID, response);
            return false;
        }

        //TOKEN 有效性校验
        String token = request.getHeader(RequestConstant.TOKEN);
        if(StringUtils.isBlank(token)){
            writeFailResponse(ErrorCode.TOKEN_INVALID, response);
            return false;
        }

        if(!TokenUtils.verify(token)){
            writeFailResponse(ErrorCode.TOKEN_INVALID, response);
            return false;
        }

        //请求参数验签
        if(!verifyRequestData(request)){
            writeFailResponse(ErrorCode.PARAM_INVALID, response);
            return false;
        }
        return true;
    }

    /**
     * 验证请求参数是否一致
     * @param request
     * @return
     */
    private boolean verifyRequestData(HttpServletRequest request) {

        String requestData = (String) request.getAttribute(RequestConstant.REQUEST_DATA);
        if (StringUtils.isEmpty(requestData)) {
            return true;
        }

        String sign = request.getHeader(RequestConstant.SIGN);
        if(RequestConstant.PARAM_CHECK_IGNORE.equals(sign)){
            return true;
        }

        if (md5HashVerify(requestData, sign)) {
            return true;
        }
        return false;
    }

    /**
     * 验证 MD5 是否一致
     * @param requestData 请求数据
     * @param sign 前端参数签名
     * @return
     */
    private boolean md5HashVerify(String requestData, String sign) {
        String md5Hash = DigestUtils.md5DigestAsHex(String.format("%s_%s", requestData, RequestConstant.PARAM_MD5_SECRET_KEY).getBytes());
        if (md5Hash.equals(sign)) {
            return true;
        }
        log.info("md5HashVerify fail, sign=[{}], md5Hash=[{}],requestData=[{}]", sign, md5Hash, requestData);
        return false;
    }

    private static void writeFailResponse(ErrorCode errorCode, HttpServletResponse response) throws IOException {
        response.getOutputStream().write(JSONObject.toJSONString(new Response(errorCode)).getBytes("UTF-8"));
        response.setContentType("application/json;charset=UTF-8");
    }
}
