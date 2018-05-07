package com.prem.priceparser.configs;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {


    private String topicExchangeName = "TestExchange";
    private String testQueue = "TestQueue";
    private String routingKey = "routing-test";

    @Bean
    public Queue getQueue(){
        return new Queue(testQueue);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(topicExchangeName);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {

        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }


}
