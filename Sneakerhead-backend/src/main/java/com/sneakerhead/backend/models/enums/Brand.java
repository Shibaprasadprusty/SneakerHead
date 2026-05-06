package com.sneakerhead.backend.models.enums;

/**
 * Enum representing supported shoe brands.
 * Each brand has a human-readable display name.
 */
public enum Brand {

    NIKE("Nike"),
    ADIDAS("Adidas"),
    PUMA("Puma"),
    REEBOK("Reebok"),
    NEW_BALANCE("New Balance"),
    CONVERSE("Converse"),
    VANS("Vans"),
    UNDER_ARMOUR("Under Armour"),
    ASICS("ASICS"),
    SKECHERS("Skechers");

    private final String displayName;

    Brand(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
