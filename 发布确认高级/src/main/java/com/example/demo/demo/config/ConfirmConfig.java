package com.example.demo.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 温黎明
 * @version 1.0
 * @date 2022/3/1 9:21
 */
//配置类
@Configuration
public class ConfirmConfig {

    //交换机
    public static final String CONFIRM_EXCHANGE_NAME="confirm_exchange";
    //队列
    public static final String CONFIRM_QUEUE_NAME="confirm_queue";
    //RoutingKey
    public static final String CONFIRM_ROUTINGKEY_NAME="key1";

    //声明交换机
    @Bean
    public DirectExchange exchange(){
        return ExchangeBuilder.directExchange(CONFIRM_EXCHANGE_NAME).durable(true).
                withArgument("alternate-exchange",BackUpExchangeConfig.BackUP_EXCHANGE_NAME).build();
    }

    //声明队列
    @Bean
    public Queue queue(){
        return QueueBuilder.durable(CONFIRM_QUEUE_NAME).build();
    }

    //绑定
    @Bean
    public Binding queuetobindingexchange(@Qualifier("exchange") DirectExchange exchange,
                                          @Qualifier("queue") Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(CONFIRM_ROUTINGKEY_NAME);
    }

}
