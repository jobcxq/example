package com.example.cnxqin.controller.user;

import com.example.cnxqin.common.constant.RequestConstant;
import com.example.cnxqin.common.util.TokenUtils;
import com.example.cnxqin.controller.BaseController;
import com.example.cnxqin.vo.output.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cnxqin
 * @desc
 * @date 2021/01/22 23:45
 */
@RestController
@RequestMapping("/login")
public class LoginController extends BaseController {

    @RequestMapping("/userName")
    public Response login(@RequestParam("userName") String userName, @RequestParam("password")String password){
        return success(TokenUtils.token(123L, userName, RequestConstant.TOKEN_SECRET));
    }

}
