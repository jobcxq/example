package com.example.cnxqin.vo.output;

import com.example.cnxqin.entity.User;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * @author cnxqin
 * @desc
 * @date 2021/01/23 14:15
 */
@Data
@Builder
@Accessors(chain = true)
public class UserVo {

    private Long userId;
    private String userName;
    private String phone;

    private String token;

    public static UserVo valueOf(User user){
        if(Objects.isNull(user)){
            return null;
        }
        return UserVo.builder().build()
                .setUserId(user.getId())
                .setPhone(user.getPhoneNo())
                .setUserName(user.getName());
    }
}
