package com.example.cnxqin.vo.input;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author cnxqin
 * @desc
 * @date 2021/01/24 18:56
 */
@Data
public class LoginInput {

    @NotEmpty
    private String phoneNo;

    @NotEmpty
    private String verifyCode;

}
