package com.soul.rat.web.controller;

import com.soul.rat.biz.config.RabbitMqConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器
 *
 * @author zhujx
 * @date 2021/12/31 15:44
 */
@RestController
@RequestMapping("/test")
public class TestController {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/send")
    public String sendTest(@RequestParam String message) {
        rabbitTemplate.convertAndSend(RabbitMqConfig.MY_DIRECT_EXCHANGE, RabbitMqConfig.MY_DIRECT_ROUTING, message);
        return "成功";
    }

}
