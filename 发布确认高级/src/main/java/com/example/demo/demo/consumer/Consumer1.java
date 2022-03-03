package com.example.demo.demo.consumer;

import com.example.demo.demo.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * @author 温黎明
 * @version 1.0
 * @date 2022/3/1 9:35
 */
@Slf4j
@Component
public class Consumer1 {

    @RabbitListener(queues = ConfirmConfig.CONFIRM_QUEUE_NAME)
    public void receiveConfirmMessage(Message message) throws UnsupportedEncodingException {
        log.info("接收到的队列confirm.queue消息为:{}",new String(message.getBody(),"UTF-8"));
    }
}
