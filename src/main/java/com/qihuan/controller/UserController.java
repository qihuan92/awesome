package com.qihuan.controller;

import com.qihuan.pojo.User;
import com.qihuan.service.UserService;
import com.qihuan.tools.Const;
import com.qihuan.tools.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController
 * Created by Qi on 2017/3/14.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/register")
    public Result register(@RequestParam String userName,
                           @RequestParam String password,
                           @RequestParam String nickName) {
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        user.setNickName(nickName);
        userService.register(user);
        return Result.create(0, Const.SUCCESS, user);
    }

    @RequestMapping("/userList")
    @ResponseBody
    public Result userList(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "10") int size) {
        return Result.create(0, Const.SUCCESS, userService.userList(page, size));
    }

    @RequestMapping("/getUser")
    public Result userList(@RequestParam String userName) {
        return Result.create(0, Const.SUCCESS, userService.getUser(userName));
    }
}
