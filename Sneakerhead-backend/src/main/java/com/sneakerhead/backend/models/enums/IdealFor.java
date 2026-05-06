package com.sneakerhead.backend.models.enums;

/**
 * Enum representing the target audience / ideal use-case for the shoe.
 */
public enum IdealFor {

    MEN("Men"),
    WOMEN("Women"),
    UNISEX("Unisex"),
    BOYS("Boys"),
    GIRLS("Girls"),
    KIDS("Kids"),
    TODDLERS("Toddlers");

    private final String displayName;

    IdealFor(String displayName) {
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
