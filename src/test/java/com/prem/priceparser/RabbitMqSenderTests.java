package com.prem.priceparser;


import com.prem.priceparser.rabbitmq.RabbitMqSender;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest

public class RabbitMqSenderTests {

    @Autowired
    RabbitMqSender sender;

    @Test
    public void sendMessage(){
        log.info("Sending message...");
        sender.sendMessageToQueue("Hello from spring application!");
    }
}
