package com.example.demo.demo.consumer;

import com.example.demo.demo.config.BackUpExchangeConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * @author 温黎明
 * @version 1.0
 * @date 2022/3/2 9:14
 */
@Slf4j
@Component
public class WarningConsumer {

    //接收报警消息
    @RabbitListener(queues = BackUpExchangeConfig.WARNING_QUEUEE_NAME)
    public void recevieWarningMessage(Message message) throws UnsupportedEncodingException {
        log.info("报警消息发现不可路由消息:{}", new String(message.getBody(),"UTF-8"));
    }
}
