package com.soul.rat.web.controller;

import com.soul.rat.biz.config.OssClientConfig;
import com.soul.rat.biz.config.RabbitMqConfig;
import com.soul.rat.common.api.BaseResult;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

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

    @Autowired
    private OssClientConfig ossClientConfig;

    @PostMapping("/upload/file")
    public BaseResult<?> uploadFile(@RequestPart("file") MultipartFile file) throws IOException {

        String name = file.getOriginalFilename();

        InputStream inputStream = file.getInputStream();

        String baseUrl = ossClientConfig.addFile(name, inputStream);

        return BaseResult.success().data(baseUrl);
    }

}
