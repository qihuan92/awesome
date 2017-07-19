package com.qihuan.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Qi on 2017/6/29.
 */
@RestController
@RequestMapping(value = "/pomelo")
public class PomeloController {

    @Value("${pomelo.address}")
    private String address;

    @Autowired
    private PomeloService pomeloService;

    private final static Logger LOGGER = LoggerFactory.getLogger(PomeloController.class);

    private boolean flag;

    @RequestMapping("/login")
    public String hello(@RequestParam String phone, @RequestParam String password) {
        return pomeloService.login(phone, password);
    }
}
