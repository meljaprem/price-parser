package com.prem.priceparser.helpers;

import com.prem.priceparser.domain.Job;
import com.prem.priceparser.domain.enums.ShopName;
import com.prem.priceparser.rabbitmq.senders.InboundRabbitMqSender;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

@Aspect
@Slf4j
@Configuration
public class InboundRabbitSenderAspect {


    @Pointcut("execution(* com.prem.priceparser.rabbitmq.senders.InboundRabbitMqSender.sendJobToQueue(..))")
    public void threadLocalUsing() {
    }

    @Around(value = "threadLocalUsing()")
    public void saveShopToThreadLocal(ProceedingJoinPoint point) throws Throwable {
        log.trace("Starting advice to save ShopName to ThreadLocal var");
        Object target = point.getTarget();
        if (target instanceof InboundRabbitMqSender) {
            InboundRabbitMqSender sender = (InboundRabbitMqSender) target;
            ThreadLocal<ShopName> shopNameHolder = sender.getShopNameHolder();
            Object[] args = point.getArgs();
            Object arg = args[0];
            if (arg instanceof Job) {
                Job job = (Job) arg;
                shopNameHolder.set(job.getShop());
                log.trace("ShopName was setted");
            }
            point.proceed();
            shopNameHolder.remove();
        }
        log.trace("Ending advice");
    }
}
