package com.soul.rat.biz.service.impl;

import com.soul.rat.biz.config.RabbitMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author zhujx
 */
@Service
@Slf4j
public class ConsumeService {
    /**
     * 接收消息
     *
     * @param data
     */
    @RabbitListener(queues = RabbitMqConfig.MY_QUEUE)
    public void receive(String data) {
        try {
            log.info("TestDireQueue receive message================= {}", data);
        } catch (Exception ex) {
            log.error("err", ex);
        }
    }
}