package com.sneakerhead.backend.models.enums;

/**
 * Enum representing the category/type of shoe.
 */
public enum ShoeType {

    RUNNING("Running"),
    BASKETBALL("Basketball"),
    TRAINING("Training & Gym"),
    CASUAL("Casual / Lifestyle"),
    SNEAKER("Sneaker"),
    HIKING("Hiking & Trail"),
    FOOTBALL("Football / Soccer"),
    TENNIS("Tennis"),
    SKATEBOARDING("Skateboarding"),
    WALKING("Walking"),
    SLIP_ON("Slip-On"),
    BOOT("Boot"),
    SANDAL("Sandal"),
    FORMAL("Formal");

    private final String displayName;

    ShoeType(String displayName) {
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
