package com.prem.priceparser.listeners.events;

import com.prem.priceparser.domain.entity.Product;
import org.springframework.context.ApplicationEvent;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 12.06.2018
 */

public class ChangeProductScheduleStatusEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */

    private Product product;

    public ChangeProductScheduleStatusEvent(Object source) {
        super(source);
        this.product = (Product) source;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
