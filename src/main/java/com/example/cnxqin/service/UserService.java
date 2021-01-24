package com.example.cnxqin.service;

import com.alibaba.fastjson.JSONObject;
import com.example.cnxqin.dao.UserDao;
import com.example.cnxqin.entity.User;
import com.example.cnxqin.service.common.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @author cnxqin
 * @desc
 * @date 2021/01/20 23:35
 */
@Service
public class UserService extends BaseService<User>{

    @Autowired
    private UserDao userDao;

    @Autowired
    RedisService redisService;

    @Transactional
    public int updateUser(String name){
        return userDao.updateNameById(name,1L);
//        return userDao.updateNameByIdNative(name,1L);
    }

    public User getById(Long id){
        String key = "user_" + id;
        String value = redisService.get(key);
        if(Objects.nonNull(value)){
            return JSONObject.parseObject(value, User.class);
        }
        User user = super.getById(id);

        redisService.set(key, user, 600);
        return user;
    }
}
