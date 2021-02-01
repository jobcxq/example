package com.example.cnxqin.controller.user;

import com.example.cnxqin.common.constant.RequestConstant;
import com.example.cnxqin.controller.BaseController;
import com.example.cnxqin.service.user.LoginService;
import com.example.cnxqin.vo.input.LoginInput;
import com.example.cnxqin.vo.output.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author cnxqin
 * @desc
 * @date 2021/01/22 23:45
 */
@RestController
@RequestMapping("/login")
public class LoginController extends BaseController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/sendVerifyCode")
    public Response sendVerifyCode(@RequestParam @NotBlank @Pattern(regexp = RequestConstant.REGULAR_PHONE_NO) String phoneNo){
        loginService.sendLoginVerifyCode(phoneNo);
        return success();
    }

    @PostMapping("byPhone")
    public Response loginByPhone(@RequestBody @Validated LoginInput input){
        return success(loginService.loginByPhone(input));
    }


}
