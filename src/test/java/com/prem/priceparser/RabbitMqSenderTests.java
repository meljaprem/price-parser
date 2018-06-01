package com.prem.priceparser;


import com.prem.priceparser.domain.Job;
import com.prem.priceparser.domain.JobResult;
import com.prem.priceparser.domain.enums.ShopName;
import com.prem.priceparser.rabbitmq.senders.RabbitMqSender;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest

public class RabbitMqSenderTests {

    @Autowired
    @Qualifier("inboundRabbitMqSender")
    RabbitMqSender sender;

    @Test
    public void sendMessageToInbound(){
        log.info("Sending message...");
        sender.sendMessageToQueue(new Job(213L, ShopName.ROZETKA, "SomeCode"));
    }

    @Test
    public void sendMessageToOutbound(){
        log.info("Sending message...");
        sender.sendMessageToQueue(new JobResult(213L, ShopName.ROZETKA, 123.23, true, "grn"));
    }
}
