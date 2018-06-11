package com.prem.priceparser.scheduling;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class ScheduleConditional implements Condition {

    private final String SCHEDULER_PROPERTY_NAME = "scheduler.active";

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return Boolean.valueOf(context.getEnvironment().getProperty(SCHEDULER_PROPERTY_NAME, Boolean.FALSE.toString()));
    }
}
