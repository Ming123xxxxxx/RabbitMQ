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
 * @date 2022/2/28 10:19
 */
@Configuration
public class QCConfig {

    //普通队列的名称QC
    public static final String C_NORMAL_QUEUE="QC";
    //死信交换机名称
    public static final String Y_DEAD_EXCHANGE="Y";

    //声明QC队列
    //声明普通队列QA的TTL为10s
    @Bean("CQueue")
    public Queue aqueue(){
        Map<String, Object> map = new HashMap<>(3);
        //设置死信交换机
        map.put("x-dead-letter-exchange",Y_DEAD_EXCHANGE);
        //设置死信RoutingKey
        map.put("x-dead-letter-routing-key","YD");
        return QueueBuilder.durable(C_NORMAL_QUEUE).withArguments(map).build();
    }

    @Bean
    public Binding DQueueToBindingXExchange(@Qualifier("CQueue") Queue Cqueue,
                                            @Qualifier("XExchange") DirectExchange XExchange){
        return BindingBuilder.bind(Cqueue).to(XExchange).with("XC");

    }

}
