package com.example.springbootrabbitmq.TTL队列.consumer;

import com.example.springbootrabbitmq.TTL队列.config.DelayedQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author 温黎明
 * @version 1.0
 * @date 2022/2/28 11:30
 */
//消费者 基于插件的延迟消息
@Slf4j
@Component
public class DelayedQueueConsumer {

    //监听消息
    @RabbitListener(queues = DelayedQueueConfig.DELAYED_QUEUE)
    public void receiveDelayedQueue(Message message){
        String msg = new String(message.getBody());
        log.info("当前时间:{},收到延迟队列的消息:{}",new Date().toString(),message);
    }

}
