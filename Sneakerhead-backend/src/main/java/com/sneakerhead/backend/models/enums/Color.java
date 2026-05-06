package com.sneakerhead.backend.models.enums;

/**
 * Enum representing available shoe colors.
 * A shoe can have multiple colors (e.g., WHITE + RED for a colorway).
 */
public enum Color {

    WHITE("White"),
    BLACK("Black"),
    RED("Red"),
    BLUE("Blue"),
    NAVY_BLUE("Navy Blue"),
    GREY("Grey"),
    CHARCOAL("Charcoal"),
    GREEN("Green"),
    OLIVE("Olive"),
    YELLOW("Yellow"),
    ORANGE("Orange"),
    PINK("Pink"),
    PURPLE("Purple"),
    BROWN("Brown"),
    TAN("Tan"),
    BEIGE("Beige"),
    GOLD("Gold"),
    SILVER("Silver"),
    MULTICOLOR("Multicolor");

    private final String displayName;

    Color(String displayName) {
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
