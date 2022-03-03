package com.example.demo.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.rmi.runtime.Log;

import javax.annotation.PostConstruct;

/**
 * @author 温黎明
 * @version 1.0
 * @date 2022/3/1 9:49
 */
@Slf4j
@Component
public class MyCallBack implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnsCallback {

    @Autowired
    RabbitTemplate rabbitTemplate;

    //注入
    @PostConstruct
    public void initial(){
        //注入交换机回调接口
        rabbitTemplate.setConfirmCallback(this);
        //注入队列回退消息接口
        rabbitTemplate.setReturnsCallback(this);
    }

    //交换机确认回调方法
    /*
    1.发送消息  交换机接收到了回调
        参数一(correlationData):保存回调消息的ID及相关信息
        参数二(ack):交换机是否收到消息
        参数三(casue):失败的原因,成功则为null
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {

        if(ack){
            log.info("交换机已经收到Id为:{}的消息",(correlationData != null ? correlationData.getId():""));
        }else{
            log.info("交换机还未收到Id为:{}的消息,原因为:{}",(correlationData != null ? correlationData.getId():""),cause);

        }
    }
    //可以在当消息传递过程中不可达目的地时将消息返回给生产者
    //只有不可达目的地时才会回退
    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        log.error("消息:{},被交换机:{},退回了,退回原因:{},路由key:{}",
                returnedMessage.getMessage(),returnedMessage.getExchange(),returnedMessage.getReplyText(),returnedMessage.getRoutingKey());
    }
}
