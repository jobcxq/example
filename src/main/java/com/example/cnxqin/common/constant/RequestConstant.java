package com.example.cnxqin.common.constant;

/**
 * @author cnxqin
 * @desc
 * @date 2021/01/22 23:33
 */
public class RequestConstant {

    public static final String IGNORE_LOGIN_CHECK = "login";

    public static final String REQUEST_DATA = "REQUEST_DATA";

    public static final String TOKEN = "Token";

    public static final String USER_ID = "User-Id";

    public static final String SIGN = "SIGN";

    public static final String REGULAR_PHONE_NO = "^1[0-9]{10}$";

    /**
     * 请求参数加密 KEY
     */
    public static final String PARAM_MD5_SECRET_KEY = "bd9b95f2589a42e49fc06fc7622e1746";

    /**
     * 参数校验，忽略的签名值
     */
    public static final String PARAM_CHECK_IGNORE = "49c14052552a4b39abf77d8dcfb06e93";

    /**
     * token 加密秘钥
     */
    public static final String TOKEN_SECRET = "6bbc4b231e6445a0bc06007f78f126c7";

    /**
     * 设置 Token 过期时间（秒）
     */
    public static final Long TOKEN_EXPIRE_DATE = 24 * 60 * 60 * 1000L;
}
