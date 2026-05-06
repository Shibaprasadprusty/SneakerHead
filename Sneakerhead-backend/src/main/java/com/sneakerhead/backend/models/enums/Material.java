package com.sneakerhead.backend.models.enums;

/**
 * Enum representing the primary material used in the shoe's construction.
 */
public enum Material {

    LEATHER("Leather"),
    SYNTHETIC_LEATHER("Synthetic Leather"),
    MESH("Mesh"),
    CANVAS("Canvas"),
    KNIT("Knit / Flyknit"),
    SUEDE("Suede"),
    RUBBER("Rubber"),
    FOAM("Foam"),
    TEXTILE("Textile"),
    NUBUCK("Nubuck"),
    PATENT_LEATHER("Patent Leather"),
    VEGAN_LEATHER("Vegan Leather"),
    RECYCLED("Recycled Material");

    private final String displayName;

    Material(String displayName) {
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
