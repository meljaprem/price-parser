package com.prem.priceparser.rabbitmq.consumers;

import com.prem.priceparser.domain.Job;
import com.prem.priceparser.services.managers.JobManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 02.06.2018
 */

@Component
@Slf4j
@PropertySource("classpath:rabbitmq.properties")
@RequiredArgsConstructor
public class InboundConsumer {

    private final JobManager jobManager;

    @RabbitListener(queues = "${inbound.queue.rozetka}")
    public void receiveInboundRozetka(Job job) {
        log.debug("Rozetka job Received: {}", job);
        jobManager.executeRozetkaJob(job);
    }

    @RabbitListener(queues = "${inbound.queue.comfy}")
    public void receiveInboundComfy(Job job) {
        log.debug("Comfy job received: {}", job);
        jobManager.executeComfyJob(job);
    }
}
