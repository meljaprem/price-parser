package com.prem.priceparser.rabbitmq.senders;

import com.prem.priceparser.configs.RabbitMQConfig;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import javax.annotation.PostConstruct;

@Slf4j
@Setter
@Getter
public abstract class RabbitMqSender<T> {

    public RabbitMqSender(RabbitTemplate rabbitTemplate,
                          RabbitMQConfig rabbitMQConfig,
                          Jackson2JsonMessageConverter converter) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMQConfig = rabbitMQConfig;
        this.converter = converter;
    }

    protected final RabbitTemplate rabbitTemplate;
    protected final RabbitMQConfig rabbitMQConfig;
    protected final Jackson2JsonMessageConverter converter;

    public abstract void sendMessageToQueue(T t);

    public abstract String getExchange();


    @PostConstruct
    private void setConverter() {
        rabbitTemplate.setMessageConverter(converter);
    }

}
