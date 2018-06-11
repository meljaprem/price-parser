package com.prem.priceparser;


import com.prem.priceparser.domain.Job;
import com.prem.priceparser.domain.entity.Product;
import com.prem.priceparser.domain.enums.ShopName;
import com.prem.priceparser.helpers.ProductUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
@Ignore
public class RabbitMqSenderTests {

//    @Autowired
//    InboundRabbitMqSender sender;

//    @Test
//    public void sendMessageToInbound(){
//        log.info("Sending message...");
//        sender.sendJobToQueue(new Job(213L, ShopName.ROZETKA, "SomeCode"));
//    }
//
//    @Test
//    public void sendMessageToOutbound(){
//        log.info("Sending message...");
//        sender.sendJobToQueue(new JobResult(213L, ShopName.ROZETKA, 123.23, true, "grn"));
//    }
}
