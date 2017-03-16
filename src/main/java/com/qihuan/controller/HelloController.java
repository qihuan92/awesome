package com.qihuan.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * HelloController
 * Created by Qi on 2017/3/13.
 */
@RestController
@RequestMapping("/test")
public class HelloController {
    @RequestMapping("/hello")
    public String hello(@RequestParam String user) {
        return "hello " + user;
    }
}
