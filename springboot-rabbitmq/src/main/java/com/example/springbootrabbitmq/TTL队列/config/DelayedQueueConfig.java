package com.example.springbootrabbitmq.TTL队列.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 温黎明
 * @version 1.0
 * @date 2022/2/28 11:08
 */
@Configuration
public class DelayedQueueConfig {
    //交换机
    public static final String DELAYED_EXCHANGE="delayed_exchange";
    //队列
    public static final String DELAYED_QUEUE="delayed_queue";
    //routingkey
    public static final String DELAYED_ROUTINGKEY="delayed_routingkey";
    //声明队列
    @Bean
    public Queue delayedQueue(){
        return new Queue(DELAYED_QUEUE);
    }
    //声明交换机，基于插件的交换机
    @Bean
    public CustomExchange delayedExchange(){
        Map<String,Object> map = new HashMap<>();
        map.put("x-delayed-type","direct");

        /*
          CustomExchange()参数:
          参数一:交换机的名称
          参数二:交换机的类型
          参数三:是否需要实例化
          参数四:是否需要自动删除
          参数五:其他参数
         */
        return new CustomExchange(DELAYED_EXCHANGE,"x-delayed-message",true,false,map);
    }
    //绑定DELAYED_QUEUE队列到DELAYED_EXCHANGE
    @Bean
    public Binding DQueueToBingingDExchange(@Qualifier("delayedQueue") Queue DQueue,
                                            @Qualifier("delayedExchange") CustomExchange dxchange){
        return BindingBuilder.bind(DQueue).to(dxchange).with(DELAYED_ROUTINGKEY).noargs();

    }
}
