package com.prem.priceparser.listeners.events;

import com.prem.priceparser.domain.entity.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public abstract class ProductChangesEvent extends ApplicationEvent {

    protected Product product;

    public ProductChangesEvent(Object source) {
        super(source);
        this.product = (Product) source;
    }
}
