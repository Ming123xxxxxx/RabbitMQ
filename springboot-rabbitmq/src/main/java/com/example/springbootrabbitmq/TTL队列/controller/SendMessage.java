package com.example.springbootrabbitmq.TTL队列.controller;

import com.example.springbootrabbitmq.TTL队列.config.DelayedQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author 温黎明
 * @version 1.0
 * @date 2022/2/28 9:32
 */
@Slf4j
@RestController
@RequestMapping("/ttl")
public class SendMessage {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //发消息
    @GetMapping("/sendMSG/{message}")
    public void sendMSG(@PathVariable String message){
        log.info("当前时间:{},发送一条消息给两个TTL队列:{}",new Date().toString(),message);
        rabbitTemplate.convertAndSend("X","XA","消息来自于TTL为10s的队列:"+message);
        rabbitTemplate.convertAndSend("X","XB","消息来自于TTL为40s的队列:"+message);
    }

    //开始发消息和TTL
    @GetMapping("/sendExpirationMSG/{message}/{ttlTime}")
    public void sendExpirationMSG(@PathVariable String message,@PathVariable String ttlTime){
        log.info("当前时间:{},发送一条时长为:{}毫秒TTL消息给队列QC,消息为:{}",new Date().toString(),ttlTime,message);
        rabbitTemplate.convertAndSend("X","XC",message,(e) -> {
            //设置发送消息时候的延迟时长
            e.getMessageProperties().setExpiration(ttlTime);
            return e;
        });
    }

    //开始发消息和TTL
    @GetMapping("/sendDelayMSG/{message}/{delayTime}")
    public void sendExpirationMSG(@PathVariable String message,@PathVariable Integer delayTime){
        log.info("当前时间:{},发送一条时长为:{}毫秒延迟消息给延迟队列delayed_queue,消息为:{}",new Date().toString(),delayTime,message);
        rabbitTemplate.convertAndSend(DelayedQueueConfig.DELAYED_EXCHANGE,DelayedQueueConfig.DELAYED_ROUTINGKEY,message,(e) -> {
            //设置发送消息时候的延迟时长 ,单位毫秒
            e.getMessageProperties().setDelay(delayTime);
            return e;
        });
    }
}
