package com.prem.priceparser.domain.enums;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum RoleEnum {
    ADMIN("ADMIN"),
    USER("USER");

    private String name;
    RoleEnum(String name) {
        this.name = name;
    }
}
