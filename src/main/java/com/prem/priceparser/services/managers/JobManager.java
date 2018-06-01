package com.prem.priceparser.services.managers;

import com.prem.priceparser.domain.Job;
import com.prem.priceparser.domain.JobResult;
import com.prem.priceparser.domain.enums.ShopName;
import com.prem.priceparser.rabbitmq.senders.OutboundRabbitMqSender;
import com.prem.priceparser.services.pricecheckers.PriceChecker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 02.06.2018
 */

@Service
@Slf4j
public class JobManager {

    private final PriceChecker rozetkaPriceChecker;
    private final PriceChecker comfyPriceChecker;
    private final OutboundRabbitMqSender outboundSender;

    public JobManager(@Qualifier("rozetkaPriceChecker") PriceChecker rozetkaPriceChecker,
                      @Qualifier("comfyPriceChecker") PriceChecker comfyPriceChecker,
                      OutboundRabbitMqSender outboundSender) {
        this.rozetkaPriceChecker = rozetkaPriceChecker;
        this.comfyPriceChecker = comfyPriceChecker;
        this.outboundSender = outboundSender;
    }

    public void executeRozetkaJob(Job job) {
        log.debug("Executing job Rozetka for product id: {}", job.getProductId());
        Double price = rozetkaPriceChecker.getPrice(job.getCode());
        JobResult jobResult = buildJobResult(job, price, ShopName.ROZETKA);
        outboundSender.sendMessageToQueue(jobResult);
        log.debug("Job Rozetka for product id {} successfully executed", job.getProductId());
    }

    public void executeComfyJob(Job job) {
        log.debug("Executing job Rozetka with product id: {}", job.getProductId());
        Double price = comfyPriceChecker.getPrice(job.getCode());
        JobResult jobResult = buildJobResult(job, price, ShopName.COMFY);
        outboundSender.sendMessageToQueue(jobResult);
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
