package com.prem.priceparser.listeners.events;

import com.prem.priceparser.domain.enums.ShopName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddShopEvent extends ProductChangesEvent {

    private ShopName shop;

    public AddShopEvent(Object source, ShopName shopToAdd) {
        super(source);
        this.shop  = shopToAdd;
    }
}
