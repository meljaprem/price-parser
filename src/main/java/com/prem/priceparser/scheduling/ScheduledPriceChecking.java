package com.prem.priceparser.scheduling;


import com.prem.priceparser.domain.Job;
import com.prem.priceparser.domain.entity.Product;
import com.prem.priceparser.domain.enums.ScheduleType;
import com.prem.priceparser.rabbitmq.senders.RabbitMqSender;
import com.prem.priceparser.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Conditional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@Conditional(ScheduleConditional.class)
public class ScheduledPriceChecking {

    private final ProductService productService;
    private final RabbitMqSender<Job> inboundSender;

    @Scheduled(fixedDelayString = "${scheduler.daily.delay}")
    public void dailyChecker() {
        log.debug("Performing scheduled daily task");
        List<Product> dailyProducts = productService.getAllScheduledByType(ScheduleType.DAY);

    }

    @Scheduled(fixedDelayString = "${scheduler.hourly.delay}")
    public void hourlyChecker() {
        log.debug("Performing scheduled hourly task");
    }

    @Scheduled(fixedDelayString = "${scheduler.minutely.delay}")
    public void minutelyChecker() {
        log.debug("Performing scheduled minutely task");
    }

}
