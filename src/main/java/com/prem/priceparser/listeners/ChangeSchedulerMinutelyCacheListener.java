package com.prem.priceparser.listeners;

import com.prem.priceparser.domain.entity.Product;
import com.prem.priceparser.helpers.ProductUtils;
import com.prem.priceparser.listeners.events.ChangeProductScheduleStatusEvent;
import com.prem.priceparser.scheduling.ScheduledPriceChecker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 12.06.2018
 */

@Component
@Slf4j
public class ChangeSchedulerMinutelyCacheListener {

    @EventListener(condition = "#{event.product.scheduled " +
            "and event.product.scheduleType.equals(T(com.prem.priceparser.domain.enums.ScheduleType).MINUTE)}")
    public void addJobsToCache(ChangeProductScheduleStatusEvent event) {
        Product product = event.getProduct();
        ScheduledPriceChecker.minutelyJobsCache
                .addAll(ProductUtils.parseJobsFromProduct(product));
    }

    @EventListener(condition = "#{event.product.scheduled == false " +
            "and event.product.scheduleType.equals(T(com.prem.priceparser.domain.enums.ScheduleType).MINUTE)}")
    public void removeJobsfromCache(ChangeProductScheduleStatusEvent event) {
        Product product = event.getProduct();
        ScheduledPriceChecker.minutelyJobsCache
                .removeIf(job -> job.getProductId().equals(product.getId()));
    }
}
