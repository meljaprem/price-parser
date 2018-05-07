package com.prem.priceparser.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
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

    @Value("${routing.comfy}")
    private String comfyRouting;
    @Value("${routing.rozetka}")
    private String rozetkaRouting;


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
    TopicExchange inboundExchange() {
        return new TopicExchange(inboundExchangeName);
    }

    @Bean(name = "outboundExchange")
    TopicExchange outboundExchange() {
        return new TopicExchange(outboundExchangeName);
    }

    @Bean("outboundRozetkaRouting")
    Binding bindOutboundRozetka(@Qualifier("outboundRozetkaQueue") Queue queue,
                                @Qualifier("outboundExchange") TopicExchange exchange) {

        return BindingBuilder.bind(queue).to(exchange).with(rozetkaRouting);
    }

    @Bean("inboundRozetkaRouting")
    Binding bindOutboundComfy(@Qualifier("inboundRozetkaQueue") Queue queue,
                              @Qualifier("inboundExchange") TopicExchange exchange) {

        return BindingBuilder.bind(queue).to(exchange).with(rozetkaRouting);
    }

    @Bean("outboundComfyRouting")
    Binding bindInboundComfy(@Qualifier("outboundComfyQueue") Queue queue,
                             @Qualifier("outboundExchange") TopicExchange exchange) {

        return BindingBuilder.bind(queue).to(exchange).with(comfyRouting);
    }

    @Bean("inboundComfyRouting")
    Binding bindInboundRozetka(@Qualifier("inboundComfyQueue") Queue queue,
                               @Qualifier("inboundExchange") TopicExchange exchange) {

        return BindingBuilder.bind(queue).to(exchange).with(comfyRouting);
    }


}
