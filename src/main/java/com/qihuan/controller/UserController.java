package com.qihuan.controller;

import com.qihuan.pojo.User;
import com.qihuan.service.UserService;
import com.qihuan.tools.Const;
import com.qihuan.tools.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * UserController
 * Created by Qi on 2017/3/14.
 */
@Api(value = "用户管理", tags = {"用户管理API"}, description = "用户管理接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "注册用户")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Result register(@RequestParam String userName,
                           @RequestParam String password,
                           @RequestParam String email) {
        User user = new User();
        user.setUsername(userName);
        user.setPassword(password);
        user.setEmail(email);
        userService.register(user);
        return Result.create(0, Const.SUCCESS, user);
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Result createAuthenticationToken(@RequestParam String userName, @RequestParam String password) throws AuthenticationException {
        final String token = userService.login(userName, password);
        return Result.create(0, Const.SUCCESS, token);
    }

    @RequestMapping(value = "refresh", method = RequestMethod.GET)
    public Result refreshAndGetAuthenticationToken(HttpServletRequest request) throws AuthenticationException {
        String token = request.getHeader(tokenHeader);
        String refreshedToken = userService.refresh(token);
        if (refreshedToken == null) {
            return Result.create(1, Const.FAIL, null);
        } else {
            return Result.create(0, Const.SUCCESS, refreshedToken);
        }
    }

    @ApiOperation(value = "获取用户列表")
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/userList", method = RequestMethod.GET)
    @ResponseBody
    public Result userList(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "10") int size) {
        return Result.create(0, Const.SUCCESS, userService.userList(page, size));
    }

    @ApiOperation(value = "根据用户名获取用户")
    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public Result userList(@RequestParam String userName) {
        return Result.create(0, Const.SUCCESS, userService.getUser(userName));
    }
}
