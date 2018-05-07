package com.prem.priceparser.rabbitmq;

import com.prem.priceparser.configs.RabbitMQConfig;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Setter
@Getter
public class RabbitMqSender {

    public RabbitMqSender(RabbitTemplate rabbitTemplate,
                          RabbitMQConfig rabbitMQConfig,
                          @Qualifier(value = "outboundRozetkaQueue") Queue queue) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMQConfig = rabbitMQConfig;
        this.queue = queue;
    }

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQConfig rabbitMQConfig;
    private final Queue queue;

    public void sendMessageToQueue(Object objectToSend){
        rabbitTemplate.convertAndSend(rabbitMQConfig.getInboundExchangeName() ,rabbitMQConfig.getRozetkaRouting(), objectToSend);
    }
}
