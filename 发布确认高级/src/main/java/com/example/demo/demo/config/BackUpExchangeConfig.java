package com.example.demo.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 温黎明
 * @version 1.0
 * @date 2022/3/2 8:55
 */
@Configuration
public class BackUpExchangeConfig {

    //备份交换机
    public static final String BackUP_EXCHANGE_NAME="back_up_exchange";

    //备份队列
    public static final String BackUP_QUEUEE_NAME="back_up_queue";

    //报警队列
    public static final String WARNING_QUEUEE_NAME="warning_queue";

    //备份交换机
    @Bean
    public FanoutExchange backupExchange(){
        return new FanoutExchange(BackUP_EXCHANGE_NAME);
    }

    //备份队列
    @Bean
    public Queue backupQueue(){
        return  QueueBuilder.durable(BackUP_QUEUEE_NAME).build();
    }

    //报警队列
    @Bean
    public Queue warningpQueue(){
        return  QueueBuilder.durable(WARNING_QUEUEE_NAME).build();
    }

    //绑定  备份队列绑定到备份交换机
    @Bean
    public Binding backupQueueBindingbackupExchange(@Qualifier("backupQueue") Queue queue,
                                                    @Qualifier("backupExchange") FanoutExchange exchange){
              return BindingBuilder.bind(queue).to(exchange);
    }

    //绑定  报警队列绑定到备份交换机
    @Bean
    public Binding wariningQueueBindingbackupExchange(@Qualifier("warningpQueue") Queue queue,
                                                    @Qualifier("backupExchange") FanoutExchange exchange){
        return BindingBuilder.bind(queue).to(exchange);
    }
}
