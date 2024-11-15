package com.example.springboot.Controller;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @ResponseBody
    @GetMapping("/hello")
    public String hello(){
        final Long access = redisTemplate.opsForValue().increment("access");
        return String.format("Hello from myApp;Access times:%s",access);
    }
}
