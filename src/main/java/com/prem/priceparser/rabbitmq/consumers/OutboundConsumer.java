package com.prem.priceparser.rabbitmq.consumers;

import com.prem.priceparser.domain.JobResult;
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
public class OutboundConsumer {

    @RabbitListener(queues = "${outbound.queue.results}")
    public void receiveInboundRozetka(JobResult result) {
        log.debug("Job results received: {}", result);
        //TODO end method
    }

}
