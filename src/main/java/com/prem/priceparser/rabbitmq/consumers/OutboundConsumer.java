package com.prem.priceparser.rabbitmq.consumers;

import com.prem.priceparser.domain.JobResult;
import com.prem.priceparser.domain.entity.Product;
import com.prem.priceparser.domain.entity.ShopPrice;
import com.prem.priceparser.exceptions.ExceptionErrorCode;
import com.prem.priceparser.exceptions.GenericBusinessException;
import com.prem.priceparser.repository.ShopPriceRepository;
import com.prem.priceparser.services.ProductService;
import com.prem.priceparser.services.managers.JobResultManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Set;

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
    private final JobResultManager manager;

    @RabbitListener(queues = "${outbound.queue.results}")
    public void receiveInboundRozetka(JobResult result) {
        log.debug("Job results received: {}", result);
        manager.parseResult(result);
    }

}
