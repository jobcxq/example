package com.example.cnxqin.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cnxqin
 * @desc
 * @date 2021/01/22 22:06
 */
public class TokenUtils {

    private static final Logger log = LoggerFactory.getLogger(TokenUtils.class);

    //设置过期时间（秒）
    private static final long EXPIRE_DATE = 24 * 60 * 60 * 1000;

    //token秘钥
    private static final String TOKEN_SECRET = "ZCfasfhuaUUHufguGuwu2020BQWE";

    public static String token(Long userId, String account, String tokenSecret) {

        String token = "";
        try {
            //过期时间
            Date date = new Date(System.currentTimeMillis() + EXPIRE_DATE);
            //秘钥及加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            //设置头部信息
            Map<String, Object> header = new HashMap<>();
            header.put("typ", "JWT");
            header.put("alg", "HS256");
            //携带用户信息，生成签名
            token = JWT.create()
                    .withHeader(header)
                    .withClaim("userId", userId)
                    .withClaim("account", account)
                    .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return token;
    }

    /**
     * 验证token，通过返回true
     * @param token
     * @return
     */
    public static boolean verify(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        }catch (Exception e){
            log.warn("token 校验失败，toke:{}", token, e);
            return  false;
        }
    }
}
