package com.example.demo.demo.controller;

import com.example.demo.demo.config.ConfirmConfig;
import com.example.demo.demo.config.MyCallBack;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author 温黎明
 * @version 1.0
 * @date 2022/2/28 9:32
 */
@Slf4j
@RestController
@RequestMapping("/public")
public class SendMessage {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //开始发消息 测试确认
    @GetMapping("/confirm/{message}")
    public void confirm(@PathVariable String message){

        //正确的
        CorrelationData correlationData = new CorrelationData("1");
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME,ConfirmConfig.CONFIRM_ROUTINGKEY_NAME,message,correlationData);
        log.info("发送消息内容为:{}",message);
        //错误的 ,routingkey错误
        CorrelationData correlationData2 = new CorrelationData("2");
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME,ConfirmConfig.CONFIRM_ROUTINGKEY_NAME+"123",message,correlationData2);
        log.info("发送消息内容为:{}",message);
    }

}
