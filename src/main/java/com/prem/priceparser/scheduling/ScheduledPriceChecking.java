package com.prem.priceparser.scheduling;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledPriceChecking {

    @Scheduled(fixedDelayString = "${scheduler.daily.delay}")
    public void dailyChecker(){
        log.info("Delay is 5000 ms" );
    }

    @Scheduled(fixedDelayString = "${scheduler.hourly.delay}")
    public void hourlyChecker(){
        log.info("Delay is 5000 ms" );
    }

    @Scheduled(fixedDelayString = "${scheduler.minutely.delay}")
    public void minutelyChecker(){
        log.info("Delay is 5000 ms" );
    }

}
