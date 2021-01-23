package com.example.cnxqin.vo.output;

import com.example.cnxqin.entity.User;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

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

    public static UserVo valueOf(User user){
        return UserVo.builder().build()
                .setUserId(user.getId())
                .setUserName(user.getName());
    }
}
