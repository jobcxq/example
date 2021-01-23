package com.example.cnxqin.controller;

import com.example.cnxqin.entity.User;
import com.example.cnxqin.service.UserService;
import com.example.cnxqin.vo.output.Response;
import com.example.cnxqin.vo.output.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author cnxqin
 * @desc
 * @date 2021/01/20 12:19
 */
@RestController
public class HelloController extends BaseController{

    private static final Logger log = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/public/hello")
    public Response sayHello(){
        log.info("hello world");
        User user = new User();
        user.setId(1L);
        user.setName("hello");
        log.info("你好！");
        return success(user);
    }

    @GetMapping("/get")
    public Response save(@RequestParam Long id){
        return success(UserVo.valueOf(userService.getById(id)));
    }

    @PostMapping("/save")
    public Response save(@RequestBody UserVo userVo){
        User user = new User();
        user.setName(userVo.getUserName());
        user.setAge(1);
        userService.saveUser(user);
        return success(userVo);
    }

    @GetMapping("/update")
    public Response update(){
        log.info("update");
        return success(userService.updateUser("哈哈hhhh哈"));
    }
}
