package com.prem.priceparser.controllers;

import com.prem.priceparser.domain.Job;
import com.prem.priceparser.domain.JobResult;
import com.prem.priceparser.domain.enums.ShopName;
import com.prem.priceparser.rabbitmq.senders.InboundRabbitMqSender;
import com.prem.priceparser.rabbitmq.senders.OutboundRabbitMqSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 01.06.2018
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class TestConroller {

    private final InboundRabbitMqSender inboundSender;
    private final OutboundRabbitMqSender outboundSender;

    @GetMapping("/in")
    public ResponseEntity<String> sendToInbound(@RequestParam(required = true) String shop,
                                                @RequestParam(required = true) String code) {
        inboundSender.sendMessageToQueue(new Job(1L, ShopName.valueOf(shop), code));
        return new ResponseEntity<>("SENT!", HttpStatus.OK );
    }

    @GetMapping("/out")
    public ResponseEntity<?> sendToOutbound() {
        outboundSender.sendMessageToQueue(new JobResult(213L, ShopName.ROZETKA, 123.23, true, null));
        return new ResponseEntity<>("SENT!", HttpStatus.OK );
    }
}
