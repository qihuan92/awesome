package com.qihuan.controller;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.GradiatedBackgroundProducer;
import cn.apiclub.captcha.gimpy.FishEyeGimpyRenderer;
import cn.apiclub.captcha.text.producer.NumbersAnswerProducer;
import com.qihuan.exception.ApiException;
import com.qihuan.tools.Result;
import com.qihuan.tools.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * HelloController
 * Created by Qi on 2017/3/13.
 */
@RestController
@RequestMapping("/test")
public class HelloController {

    private static int captchaExpires = 3 * 60; //超时时间3min
    private static int captchaW = 200;
    private static int captchaH = 60;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @RequestMapping("/hello")
    public String hello(@RequestParam String user) {
        return "hello " + user;
    }

    @RequestMapping(value = "/getCaptcha", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getCaptcha(HttpServletResponse response) {
        //生成验证码
        String uuid = UUID.randomUUID().toString();
        Captcha captcha = new Captcha.Builder(captchaW, captchaH)
                .addText(new NumbersAnswerProducer())
                .addBackground(new GradiatedBackgroundProducer())
                .gimp(new FishEyeGimpyRenderer())
                .build();

        //将验证码以<key,value>形式缓存到redis
        redisTemplate.opsForValue().set(uuid, captcha.getAnswer(), captchaExpires, TimeUnit.SECONDS);

        //将验证码key，及验证码的图片返回
        Cookie cookie = new Cookie("CaptchaCode", uuid);
        response.addCookie(cookie);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        try {
            ImageIO.write(captcha.getImage(), "png", bao);
            return bao.toByteArray();
        } catch (IOException e) {
            return null;
        }
    }

    @RequestMapping(value = "/checkCaptcha", method = RequestMethod.GET)
    public Result checkCaptcha(String captchaCode, String captchaValue) {
        String value = redisTemplate.opsForValue().get(captchaCode);
        if (value == null) {
            throw new ApiException(ResultEnum.UNKNOWN_ERROR);
        }
        redisTemplate.delete(captchaCode);
        if (!value.equals(captchaValue)) {
            throw new ApiException(ResultEnum.UNKNOWN_ERROR);
        }
        return Result.create(0, "success", null);
    }
}
