package com.prem.priceparser.rabbitmq.senders;

import com.prem.priceparser.configs.RabbitMQConfig;
import com.prem.priceparser.domain.JobResult;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Slf4j
@Service
public class OutboundRabbitMqSender extends RabbitMqSender<JobResult> {
    public OutboundRabbitMqSender(RabbitTemplate rabbitTemplate,
                                  RabbitMQConfig rabbitMQConfig,
                                  Jackson2JsonMessageConverter converter) {
        super(rabbitTemplate, rabbitMQConfig, converter);
    }

    @Override
    public void sendJobToQueue(JobResult result) {
        log.debug("Sending object: {} to exchange: {} ", result, getExchange() );
        rabbitTemplate.convertAndSend(getExchange(), rabbitMQConfig.getOutboundResultsQueueName(), result);
    }

    @Override
    public String getExchange() {
        return rabbitMQConfig.getOutboundExchangeName();
    }

}
