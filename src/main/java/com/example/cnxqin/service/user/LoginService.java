package com.example.cnxqin.service.user;

import com.example.cnxqin.common.constant.RedisKeyConstant;
import com.example.cnxqin.common.constant.RequestConstant;
import com.example.cnxqin.common.constant.TimeConstant;
import com.example.cnxqin.common.exception.BusinessException;
import com.example.cnxqin.common.exception.ErrorCode;
import com.example.cnxqin.common.util.TokenUtils;
import com.example.cnxqin.service.common.RedisService;
import com.example.cnxqin.vo.input.LoginInput;
import com.example.cnxqin.vo.output.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author cnxqin
 * @desc
 * @date 2021/01/24 19:20
 */
@Service
public class LoginService {

    private static final Logger log = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private RedisService redisService;

    /**
     * 发送验证码
     *
     * @param phoneNo
     */
    public void sendLoginVerifyCode(String phoneNo) {
        String key = String.format(RedisKeyConstant.LOGIN_VERIFY_CODE_SEND_LIMIT, phoneNo);
        String verifyCode = redisService.get(key);

        if(StringUtils.isNoneBlank(verifyCode)){
            throw new BusinessException(ErrorCode.MULI_COMMIT);
        }

        int randomCode = (int)((Math.random() * 9 + 1) * 1000);
        verifyCode = String.valueOf(randomCode);
        log.info("verifyCode:{}", verifyCode);

        //保存数据库

        //发送短信

        //存 redis
        redisService.set(key, verifyCode, TimeConstant.ONE_MINUTE);
        key = String.format(RedisKeyConstant.LOGIN_VERIFY_CODE, phoneNo);
        redisService.set(key, verifyCode, TimeConstant.FIVE_MINUTE);
    }

    public UserVo loginByPhone(LoginInput input) {

        String key = String.format(RedisKeyConstant.LOGIN_VERIFY_CODE, input.getPhoneNo());
        String verifyCode = redisService.get(key);

        if(StringUtils.isBlank(verifyCode)){
            throw new BusinessException(ErrorCode.VERIFY_CODE_INVALID);
        }

        if(!StringUtils.equals(verifyCode, input.getVerifyCode())){
            throw new BusinessException(ErrorCode.VERIFY_CODE_ERROR);
        }

        redisService.delete(key);

        String token = TokenUtils.token(123L, input.getPhoneNo(), RequestConstant.TOKEN_SECRET);

        //保存用户信息
        UserVo userVo = UserVo.builder().build().setUserId(123L).setUserName("哈哈哈").setToken(token);
        return userVo;
    }
}
