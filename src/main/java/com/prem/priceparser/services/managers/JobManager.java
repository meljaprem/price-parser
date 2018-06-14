package com.prem.priceparser.services.managers;

import com.prem.priceparser.domain.Job;
import com.prem.priceparser.domain.JobResult;
import com.prem.priceparser.domain.enums.ShopName;
import com.prem.priceparser.rabbitmq.senders.RabbitMqSender;
import com.prem.priceparser.services.pricecheckers.PriceChecker;
import com.prem.priceparser.services.pricecheckers.qualifiers.ComfyChecker;
import com.prem.priceparser.services.pricecheckers.qualifiers.RozetkaChecker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 02.06.2018
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class JobManager {

    @RozetkaChecker
    private final PriceChecker rozetkaPriceChecker;
    @ComfyChecker
    private final PriceChecker comfyPriceChecker;
    private final RabbitMqSender<JobResult> outboundSender;

    public void executeRozetkaJob(Job job) {
        log.debug("Executing job Rozetka for product id: {}", job.getProductId());
        Double price = rozetkaPriceChecker.getPrice(job.getCode());
        JobResult jobResult = buildJobResult(job, price, ShopName.ROZETKA);
        outboundSender.sendJobToQueue(jobResult);
        log.debug("Job Rozetka for product id {} successfully executed", job.getProductId());
    }

    public void executeComfyJob(Job job) {
        log.debug("Executing job Comfy with product id: {}", job.getProductId());
        Double price = comfyPriceChecker.getPrice(job.getCode());
        JobResult jobResult = buildJobResult(job, price, ShopName.COMFY);
        outboundSender.sendJobToQueue(jobResult);
        log.debug("Job Rozetka for product id {} successfully executed", job.getProductId());
    }

    private JobResult buildJobResult(Job job, Double price, ShopName shop) {
        return JobResult.builder()
                .product_id(job.getProductId())
                .shop(shop)
                .price(price)
                .date(new Date(System.currentTimeMillis()))
                .build();
    }
}
