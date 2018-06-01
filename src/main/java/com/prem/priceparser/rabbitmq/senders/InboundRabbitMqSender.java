package com.prem.priceparser.rabbitmq.senders;

import com.prem.priceparser.configs.RabbitMQConfig;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Slf4j
@Service("inboundRabbitMqSender")
public class InboundRabbitMqSender extends RabbitMqSender {

    public InboundRabbitMqSender(RabbitTemplate rabbitTemplate, RabbitMQConfig rabbitMQConfig, Jackson2JsonMessageConverter converter) {
        super(rabbitTemplate, rabbitMQConfig, converter);
    }

    @Override
    public String getExchange() {
        return rabbitMQConfig.getInboundExchangeName();
    }
}
