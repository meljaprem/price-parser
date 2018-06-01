package com.prem.priceparser.configs;

import com.prem.priceparser.domain.enums.ShopName;
import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
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
    @Value("${outbound.queue.rozetka}")
    private String outboundRozetkaQueueName;
    @Value("${outbound.queue.comfy}")
    private String outboundComfyQueueName;

    @Bean(name = "inboundRozetkaQueue")
    public Queue inboundRozetkaQueue() {
        return new Queue(inboundRozetkaQueueName);
    }

    @Bean(name = "inboundComfyQueue")
    public Queue inboundComfyQueue() {
        return new Queue(inboundComfyQueueName);
    }

    @Bean(name = "outboundRozetkaQueue")
    public Queue outboundRozetkaQueue() {
        return new Queue(outboundRozetkaQueueName);
    }

    @Bean(name = "outboundComfyQueue")
    public Queue outboundComfyQueue() {
        return new Queue(outboundComfyQueueName);
    }


    @Bean(name = "inboundExchange")
    HeadersExchange inboundExchange() {
        return new HeadersExchange(inboundExchangeName);
    }

    @Bean(name = "outboundExchange")
    HeadersExchange outboundExchange() {
        return new HeadersExchange(outboundExchangeName);
    }

    @Bean("outboundRozetkaRouting")
    Binding bindOutboundRozetka(@Qualifier("outboundRozetkaQueue") Queue queue,
                                @Qualifier("outboundExchange") HeadersExchange exchange) {

        return BindingBuilder.bind(queue).to(exchange).where("shop").matches(ShopName.ROZETKA.name());
    }

    @Bean("inboundRozetkaRouting")
    Binding bindOutboundComfy(@Qualifier("inboundRozetkaQueue") Queue queue,
                              @Qualifier("inboundExchange") HeadersExchange exchange) {

        return BindingBuilder.bind(queue).to(exchange).where("shop").matches(ShopName.ROZETKA.name());
    }

    @Bean("outboundComfyRouting")
    Binding bindInboundComfy(@Qualifier("outboundComfyQueue") Queue queue,
                             @Qualifier("outboundExchange") HeadersExchange exchange) {

        return BindingBuilder.bind(queue).to(exchange).where("shop").matches(ShopName.COMFY.name());
    }

    @Bean("inboundComfyRouting")
    Binding bindInboundRozetka(@Qualifier("inboundComfyQueue") Queue queue,
                               @Qualifier("inboundExchange") HeadersExchange exchange) {

        return BindingBuilder.bind(queue).to(exchange).where("shop").matches(ShopName.COMFY.name());
    }

    @Bean
    public Jackson2JsonMessageConverter getConverter(){
       return new Jackson2JsonMessageConverter();
    }


}