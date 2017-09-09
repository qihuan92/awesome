package com.qihuan.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Qi on 2017/6/29.
 */
@RestController
@RequestMapping(value = "/pomelo")
public class PomeloController {

    @Autowired
    private PomeloService pomeloService;

    @RequestMapping("/login")
    public String hello(@RequestParam String phone, @RequestParam String password) {
        return pomeloService.login(phone, password);
    }

    @RequestMapping("/user")
    public String user(@RequestParam String gameId) {
        return pomeloService.user(gameId);
    }
}
