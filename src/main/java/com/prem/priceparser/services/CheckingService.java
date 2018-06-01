package com.prem.priceparser.services;

import com.prem.priceparser.configs.RabbitMQConfig;
import com.prem.priceparser.domain.JobResult;
import com.prem.priceparser.repository.ProductRepository;
import com.prem.priceparser.services.pricecheckers.PriceChecker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@PropertySource("classpath:rabbitmq.properties")
public class CheckingService {

    public CheckingService(@Qualifier("rozetkaPriceChecker") PriceChecker rozetkaPriceChecker,
                           @Qualifier("comfyPriceChecker") PriceChecker comfyPriceChecker,
                           ProductRepository productRepository,
                           RabbitMQConfig rabbitConfig) {
        this.rozetkaPriceChecker = rozetkaPriceChecker;
        this.comfyPriceChecker = comfyPriceChecker;
        this.productRepository = productRepository;
        this.rabbitConfig = rabbitConfig;
    }

    private final PriceChecker rozetkaPriceChecker;
    private final PriceChecker comfyPriceChecker;
    private final ProductRepository productRepository;
    private final RabbitMQConfig rabbitConfig;

    @RabbitListener(queues = "${outbound.queue.rozetka}")
    public void processOrder(JobResult result) {
        log.info("Order Received: "+ result);
    }

//    public void checkPriceForProductId(Long id, User user) {
//        Optional<Product> productOptional = productRepository.findByIdAndUser(id, user);
//        if (productOptional.isPresent()){
//            Product product = productOptional.get();
//        } else {
//            throw new IllegalAccessException("Access error");
//        }
//    }


}
