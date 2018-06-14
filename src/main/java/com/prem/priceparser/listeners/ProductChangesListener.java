package com.prem.priceparser.listeners;

import com.prem.priceparser.domain.Job;
import com.prem.priceparser.domain.entity.Product;
import com.prem.priceparser.domain.enums.ShopName;
import com.prem.priceparser.helpers.ProductUtils;
import com.prem.priceparser.listeners.events.AddShopEvent;
import com.prem.priceparser.listeners.events.ChangeProductScheduleStatusEvent;
import com.prem.priceparser.listeners.events.DeleteProductEvent;
import com.prem.priceparser.listeners.events.DeleteShopEvent;
import com.prem.priceparser.scheduling.ScheduleConditional;
import com.prem.priceparser.scheduling.ScheduledPriceChecker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 12.06.2018
 */

@Component
@Slf4j
@Conditional(ScheduleConditional.class)
public class ProductChangesListener {

    @EventListener(condition = "event.product.scheduled == true  && event.product.scheduleType.equals(T(com.prem.priceparser.domain.enums.ScheduleType).MINUTE)")
    public void addJobsToCache(ChangeProductScheduleStatusEvent event) {
        Product product = event.getProduct();
        log.debug("Adding product with id {} to checking scheduler", product.getId());
        ScheduledPriceChecker.minutelyJobsCache
                .addAll(ProductUtils.parseJobsFromProduct(product));
    }

    @EventListener(condition = "event.product.scheduled == false && event.product.scheduleType.equals(T(com.prem.priceparser.domain.enums.ScheduleType).MINUTE)")
    public void removeJobsFromCache(ChangeProductScheduleStatusEvent event) {
        Product product = event.getProduct();
        log.debug("Deleting product with id {} from checking scheduler", product.getId());
        deleteJobsFromCache(product);
    }

    @EventListener(condition = "event.product.scheduled == true && event.product.scheduleType.equals(T(com.prem.priceparser.domain.enums.ScheduleType).MINUTE)")
    public void removeJobsFromCache(DeleteProductEvent event) {
        Product product = event.getProduct();
        log.debug("Deleting product with id {} from checking scheduler", product.getId());
        deleteJobsFromCache(product);
    }

    @EventListener(condition = "event.product.scheduled == true && event.product.scheduleType.equals(T(com.prem.priceparser.domain.enums.ScheduleType).MINUTE)")
    public void removeJobsFromCache(DeleteShopEvent event) {
        ShopName shopToDelete = event.getShop();
        Product product = event.getProduct();
        log.debug("Deleting shop {} of product with id {} from checking scheduler", shopToDelete, product.getId());
        ScheduledPriceChecker.minutelyJobsCache
                .removeIf(job -> job.getProductId().equals(product.getId()) && job.getShop().equals(shopToDelete));
    }

    @EventListener(condition = "event.product.scheduled == true && event.product.scheduleType.equals(T(com.prem.priceparser.domain.enums.ScheduleType).MINUTE)")
    public void addJobsToCache(AddShopEvent event) {
        Product product = event.getProduct();
        ShopName shopToAdd = event.getShop();
        log.debug("Adding shop {} of product with id {} to checking scheduler", shopToAdd, product.getId());
        ScheduledPriceChecker.minutelyJobsCache
                .add(createNewJob(product, shopToAdd));
    }

    private Job createNewJob(Product product, ShopName shopToAdd) {
        return new Job(product.getId(), shopToAdd, product.getCodesMap().get(shopToAdd));
    }

    private void deleteJobsFromCache(Product product) {
        ScheduledPriceChecker.minutelyJobsCache
                .removeIf(job -> job.getProductId().equals(product.getId()));
    }
}
