package com.prem.priceparser.rabbitmq.senders;

import com.prem.priceparser.configs.RabbitMQConfig;
import com.prem.priceparser.domain.Job;
import com.prem.priceparser.domain.enums.ShopName;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Setter
@Slf4j
@Service
public class InboundRabbitMqSender extends RabbitMqSender<Job> {

    private ThreadLocal<ShopName> shopNameHolder;

    public InboundRabbitMqSender(RabbitTemplate rabbitTemplate, RabbitMQConfig rabbitMQConfig, Jackson2JsonMessageConverter converter) {
        super(rabbitTemplate, rabbitMQConfig, converter);
        this.shopNameHolder = new ThreadLocal<>();
    }

    @Override
    public void sendJobToQueue(Job job) {
        log.debug("Sending object: {} to exchange: {} ", job, getExchange() );
        shopNameHolder.set(job.getShop());
        job.setShop(null);
        rabbitTemplate.convertAndSend(getExchange(), "", job, getPostProcessor());
        shopNameHolder.remove();
    }

    public void sendJobsToQueue(List<Job> jobs) {
        log.debug("Sending list of jobs to exchange: {} ", getExchange() );
        log.trace("List: {}", jobs);
        rabbitTemplate.convertAndSend(getExchange(), rabbitMQConfig.getOutboundResultsQueueName(), jobs);
    }

    private MessagePostProcessor getPostProcessor(){
        return m -> {
            m.getMessageProperties().setHeader("shop", shopNameHolder.get().name());
            return m;
        } ;
    }

    @Override
    public String getExchange() {
        return rabbitMQConfig.getInboundExchangeName();
    }
}
