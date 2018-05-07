package com.prem.priceparser.rabbitmq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RabbitMqSender {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;

    public void sendMessageToQueue(){
        rabbitTemplate.convertAndSend("TestExchange" ,"routing-test", "Hello from spring application!");
    }
}
