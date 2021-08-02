package com.clane.wallet.model;

import java.util.stream.Stream;

/**
 * @author timolor
 * @created 29/07/2021
 */
public enum KycName {
    LEVEL_ONE("ONE"),
    LEVEL_TWO("TWO"),
    LEVEL_THREE("THREE")
    ;

    private final String text;

    KycName(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public static KycName getByName(String name){
        return Stream.of(values()).filter(item -> item.name().equalsIgnoreCase(name)).findAny().orElse(null);
    }
}
