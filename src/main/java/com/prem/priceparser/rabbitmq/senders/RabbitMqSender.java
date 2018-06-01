package com.prem.priceparser.rabbitmq.senders;

import com.prem.priceparser.configs.RabbitMQConfig;
import com.prem.priceparser.domain.Job;
import com.prem.priceparser.domain.JobResult;
import com.prem.priceparser.domain.enums.ShopName;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import javax.annotation.PostConstruct;

@Slf4j
@Setter
@Getter
public abstract class RabbitMqSender {

    public RabbitMqSender(RabbitTemplate rabbitTemplate,
                          RabbitMQConfig rabbitMQConfig,
                          Jackson2JsonMessageConverter converter) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMQConfig = rabbitMQConfig;
        this.converter = converter;
        this.shopNameHolder = new ThreadLocal<>();
    }

    protected ThreadLocal<ShopName> shopNameHolder;
    protected final RabbitTemplate rabbitTemplate;
    protected final RabbitMQConfig rabbitMQConfig;
    protected final Jackson2JsonMessageConverter converter;

    public void sendMessageToQueue(Job job){
        shopNameHolder.set(job.getShop());
        job.setShop(null);
        rabbitTemplate.convertAndSend(getExchange(), "", job,  getPostProcessor() );
        shopNameHolder.remove();
    }

    public void sendMessageToQueue(JobResult result){
        shopNameHolder.set(result.getShop());
        result.setShop(null);
        rabbitTemplate.convertAndSend(getExchange(), "", result,  getPostProcessor() );
        shopNameHolder.remove();
    }

    private MessagePostProcessor getPostProcessor(){
            return m -> {
                m.getMessageProperties().setHeader("shop", shopNameHolder.get().name());
                return m;
            } ;
    }

    public abstract String getExchange();


    @PostConstruct
    private void setConverter(){
        rabbitTemplate.setMessageConverter(converter);
    }

}
