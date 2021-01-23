package com.example.cnxqin.common;

import com.alibaba.fastjson.JSONObject;
import com.example.cnxqin.common.constant.RequestConstant;
import org.springframework.util.DigestUtils;

import java.util.UUID;

/**
 * @author cnxqin
 * @desc
 * @date 2021/01/23 13:41
 */
public class RequestDateMd5Test {

    public static void main(String[] args) {

        String str = "dsds";
        System.out.println(JSONObject.toJSONString(str));

        System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));

        String md5 = DigestUtils.md5DigestAsHex(String.format("%s_%s", "id=1", RequestConstant.PARAM_MD5_SECRET_KEY).getBytes());
        System.out.println(md5);
        md5 = DigestUtils.md5DigestAsHex(String.format("%s_%s", "{\"userName\":\"樱花\"}", RequestConstant.PARAM_MD5_SECRET_KEY).getBytes());
        System.out.println(md5);

    }
}
