package com.example.cnxqin.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public static String token(Long userId, String account, String tokenSecret, Date expireDate) {

        String token = "";
        try {
            //秘钥及加密算法
            Algorithm algorithm = Algorithm.HMAC256(tokenSecret);
            //设置头部信息
            Map<String, Object> header = new HashMap<>();
            header.put("typ", "JWT");
            header.put("alg", "HS256");
            //携带用户信息，生成签名
            token = JWT.create()
                    .withHeader(header)
                    .withClaim("userId", userId)
                    .withClaim("account", account)
                    .withExpiresAt(expireDate)
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
     * @param userId
     * @return
     */
    public static boolean verify(String token, String userId, String tokenSecret){
        try {
            Algorithm algorithm = Algorithm.HMAC256(tokenSecret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            Claim claim = jwt.getClaim("userId");
            if(!claim.asLong().equals(new Long(userId))){
                return false;
            }
            return true;
        }catch (Exception e){
            log.warn("token 校验失败，toke:{}", token, e);
            return  false;
        }
    }
}
