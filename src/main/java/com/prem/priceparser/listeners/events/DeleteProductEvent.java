package com.prem.priceparser.listeners.events;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 12.06.2018
 */

public class DeleteProductEvent extends ProductChangesEvent {

    public DeleteProductEvent(Object source) {
        super(source);
    }
}
