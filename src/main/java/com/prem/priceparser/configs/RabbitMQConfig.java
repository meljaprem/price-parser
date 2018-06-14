package com.prem.priceparser.configs;

import com.prem.priceparser.domain.enums.ShopName;
import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:rabbitmq.properties")
@Getter
@Setter
public class RabbitMQConfig {

    @Value("${inbound.exchange}")
    private String inboundExchangeName;
    @Value("${inbound.queue.rozetka}")
    private String inboundRozetkaQueueName;
    @Value("${inbound.queue.comfy}")
    private String inboundComfyQueueName;

    @Value("${outbound.exchange}")
    private String outboundExchangeName;
    @Value("${outbound.queue.results}")
    private String outboundResultsQueueName;

    @Bean(name = "inboundRozetkaQueue")
    public Queue inboundRozetkaQueue() {
        return new Queue(inboundRozetkaQueueName);
    }

    @Bean(name = "inboundComfyQueue")
    public Queue inboundComfyQueue() {
        return new Queue(inboundComfyQueueName);
    }

    @Bean(name = "outboundResultsQueue")
    public Queue outboundResultsQueue() {
        return new Queue(outboundResultsQueueName);
    }


    @Bean(name = "inboundExchange")
    HeadersExchange inboundExchange() {
        return new HeadersExchange(inboundExchangeName);
    }

    @Bean(name = "outboundExchange")
    DirectExchange outboundExchange() {
        return new DirectExchange(outboundExchangeName);
    }


    @Bean("inboundRozetkaRouting")
    Binding bindOutboundComfy(@Qualifier("inboundRozetkaQueue") Queue queue,
                              @Qualifier("inboundExchange") HeadersExchange exchange) {

        return BindingBuilder.bind(queue).to(exchange).where("shop").matches(ShopName.ROZETKA.name());
    }

    @Bean("inboundComfyRouting")
    Binding bindInboundRozetka(@Qualifier("inboundComfyQueue") Queue queue,
                               @Qualifier("inboundExchange") HeadersExchange exchange) {

        return BindingBuilder.bind(queue).to(exchange).where("shop").matches(ShopName.COMFY.name());
    }

    @Bean("outboundResultsRouting")
    Binding bindInboundComfy(@Qualifier("outboundResultsQueue") Queue queue,
                             @Qualifier("outboundExchange") DirectExchange exchange) {

        return BindingBuilder.bind(queue).to(exchange).withQueueName();
    }
    @Bean
    public Jackson2JsonMessageConverter getConverter(){
        return new Jackson2JsonMessageConverter();
    }

}
