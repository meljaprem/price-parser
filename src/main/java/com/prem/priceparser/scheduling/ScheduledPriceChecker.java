package com.prem.priceparser.scheduling;


import com.prem.priceparser.domain.Job;
import com.prem.priceparser.domain.entity.Product;
import com.prem.priceparser.domain.enums.ScheduleType;
import com.prem.priceparser.helpers.ProductUtils;
import com.prem.priceparser.rabbitmq.senders.InboundRabbitMqSender;
import com.prem.priceparser.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Conditional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@Slf4j
@RequiredArgsConstructor
@Conditional(ScheduleConditional.class)
public class ScheduledPriceChecker {

    private final ProductService productService;
    private final InboundRabbitMqSender inboundSender;

    public static CopyOnWriteArrayList<Job> minutelyJobsCache;

    @PostConstruct
    private void fillCacheForMinutelyJobs() {
        log.debug("Filling cache for minutely scheduling");
        List<Product> productsToCheck = productService.getAllScheduledByType(ScheduleType.MINUTE);
        if (productsToCheck.size() > 0) {
            List<Job> jobs = ProductUtils.parseJobsFromListOfProduct(productsToCheck);
            minutelyJobsCache = new CopyOnWriteArrayList<>(jobs);
            log.debug("Filled by {} jobs from {} products", jobs.size(), productsToCheck.size());
            log.trace("Jobs to minutely check: {}", jobs);
        } else {
            minutelyJobsCache = new CopyOnWriteArrayList<>();
            log.info("There are no products for minutely checking price, cache is empty now!");
        }
    }

    @Scheduled(fixedDelayString = "${scheduler.daily.delay}")
    public void dailyChecker() {
        log.info("Performing scheduled daily task");
        List<Product> dailyProducts = productService.getAllScheduledByType(ScheduleType.DAY);
        log.trace("There are {} products for check prices every DAY ");
        if (dailyProducts.size() > 0) {
            parseToJobsAndSendToQueue(dailyProducts);
        }
    }

    @Scheduled(fixedDelayString = "${scheduler.hourly.delay}")
    public void hourlyChecker() {
        log.info("Performing scheduled hourly task");
        List<Product> dailyProducts = productService.getAllScheduledByType(ScheduleType.HOUR);
        log.trace("There are {} products for check prices every HOUR ");
        if (dailyProducts.size() > 0) {
            parseToJobsAndSendToQueue(dailyProducts);
        }
    }

    @Scheduled(fixedDelayString = "${scheduler.minutely.delay}")
    public void minutelyChecker() {
        if (minutelyJobsCache.size()>0) {
            log.info("Performing scheduled minutely task");
            log.debug("There are {} jobs to send in queue", minutelyJobsCache.size());
            inboundSender.sendJobsToQueue(minutelyJobsCache);
        }
    }

    private void parseToJobsAndSendToQueue(List<Product> products) {
        List<Job> jobs = ProductUtils.parseJobsFromListOfProduct(products);
        log.debug("There are {} jobs to send in queue", jobs.size());
        inboundSender.sendJobsToQueue(jobs);
    }
}
