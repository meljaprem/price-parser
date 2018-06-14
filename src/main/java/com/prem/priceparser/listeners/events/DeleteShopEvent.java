package com.prem.priceparser.listeners.events;

import com.prem.priceparser.domain.enums.ShopName;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 12.06.2018
 */

@Getter
@Setter
public class DeleteShopEvent extends ProductChangesEvent {

    private ShopName shop;

    public DeleteShopEvent(Object source, ShopName shop) {
        super(source);
        this.shop = shop;
    }

}
