package com.sneakerhead.backend.models;

import com.sneakerhead.backend.models.enums.Brand;
import com.sneakerhead.backend.models.enums.Color;
import com.sneakerhead.backend.models.enums.IdealFor;
import com.sneakerhead.backend.models.enums.Material;
import com.sneakerhead.backend.models.enums.ShoeType;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Immutable Shoe model built using the Builder Pattern.
 *
 * <p>
 * Fields:
 * <ul>
 * <li>id – auto-generated UUID</li>
 * <li>name – product name (e.g. "Air Max 270")</li>
 * <li>model – model identifier (e.g. "AM270-001")</li>
 * <li>brand – {@link Brand} enum</li>
 * <li>price – base price (BigDecimal for precision)</li>
 * <li>discount – discount percentage 0–100</li>
 * <li>colors – list of {@link Color} enums (a shoe may have multiple
 * colorways)</li>
 * <li>size – available sizes (e.g. [7, 8, 9, 10, 11])</li>
 * <li>shoeType – {@link ShoeType} enum</li>
 * <li>material – {@link Material} enum</li>
 * <li>idealFor – {@link IdealFor} enum</li>
 * <li>productDetails – long-form description / feature highlights</li>
 * </ul>
 */
public final class Shoe {

    // ─────────────────────────────────────────────────────────────────────────
    // Fields (all final → immutable after construction)
    // ─────────────────────────────────────────────────────────────────────────

    private final String id;
    private final String name;
    private final String model;
    private final Brand brand;
    private final BigDecimal price;
    private final double discount; // percentage, e.g. 15.0 = 15 %
    private final List<Color> colors; // a shoe can have multiple colors
    private final List<Double> sizes; // e.g. [6.0, 7.0, 7.5, 8.0]
    private final ShoeType shoeType;
    private final Material material;
    private final IdealFor idealFor;
    private final String productDetails;

    // ─────────────────────────────────────────────────────────────────────────
    // Private constructor — only the Builder can create instances
    // ─────────────────────────────────────────────────────────────────────────

    private Shoe(Builder builder) {
        this.id = UUID.randomUUID().toString();
        this.name = builder.name;
        this.model = builder.model;
        this.brand = builder.brand;
        this.price = builder.price;
        this.discount = builder.discount;
        this.colors = Collections.unmodifiableList(builder.colors);
        this.sizes = Collections.unmodifiableList(builder.sizes);
        this.shoeType = builder.shoeType;
        this.material = builder.material;
        this.idealFor = builder.idealFor;
        this.productDetails = builder.productDetails;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Derived / computed properties
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Returns the effective selling price after applying the discount.
     */
    public BigDecimal getEffectivePrice() {
        if (discount <= 0)
            return price;
        BigDecimal discountFactor = BigDecimal.valueOf(1 - (discount / 100.0));
        return price.multiply(discountFactor).setScale(2, java.math.RoundingMode.HALF_UP);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Getters
    // ─────────────────────────────────────────────────────────────────────────

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getModel() {
        return model;
    }

    public Brand getBrand() {
        return brand;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public double getDiscount() {
        return discount;
    }

    public List<Color> getColors() {
        return colors;
    }

    public List<Double> getSizes() {
        return sizes;
    }

    public ShoeType getShoeType() {
        return shoeType;
    }

    public Material getMaterial() {
        return material;
    }

    public IdealFor getIdealFor() {
        return idealFor;
    }

    public String getProductDetails() {
        return productDetails;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // toString
    // ─────────────────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return "Shoe{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", brand=" + brand +
                ", price=" + price +
                ", discount=" + discount + "%" +
                ", effectivePrice=" + getEffectivePrice() +
                ", colors=" + colors +
                ", sizes=" + sizes +
                ", shoeType=" + shoeType +
                ", material=" + material +
                ", idealFor=" + idealFor +
                ", productDetails='" + productDetails + '\'' +
                '}';
    }

    // =========================================================================
    // BUILDER — inner static class
    // =========================================================================

    /**
     * Fluent Builder for {@link Shoe}.
     *
     * <p>
     * Usage:
     * 
     * <pre>{@code
     * Shoe shoe = new Shoe.Builder("Air Max 270", "AM270-001", Brand.NIKE)
     *         .price(new BigDecimal("12999.00"))
     *         .discount(10.0)
     *         .colors(List.of(Color.WHITE, Color.RED))
     *         .sizes(List.of(7.0, 8.0, 9.0, 10.0))
     *         .shoeType(ShoeType.RUNNING)
     *         .material(Material.MESH)
     *         .idealFor(IdealFor.MEN)
     *         .productDetails("Lightweight mesh upper with Air cushioning...")
     *         .build();
     * }</pre>
     */
    public static class Builder {

        // Required fields
        private final String name;
        private final String model;
        private final Brand brand;

        // Optional fields with sensible defaults
        private BigDecimal price = BigDecimal.ZERO;
        private double discount = 0.0;
        private List<Color> colors = List.of(Color.WHITE);
        private List<Double> sizes = List.of(7.0, 8.0, 9.0, 10.0);
        private ShoeType shoeType = ShoeType.CASUAL;
        private Material material = Material.SYNTHETIC_LEATHER;
        private IdealFor idealFor = IdealFor.UNISEX;
        private String productDetails = "";

        /**
         * Creates a Builder with the three mandatory fields.
         *
         * @param name  product name
         * @param model model identifier / SKU
         * @param brand brand enum value
         */
        public Builder(String name, String model, Brand brand) {
            if (name == null || name.isBlank())
                throw new IllegalArgumentException("Shoe name must not be blank");
            if (model == null || model.isBlank())
                throw new IllegalArgumentException("Shoe model must not be blank");
            if (brand == null)
                throw new IllegalArgumentException("Brand must not be null");
            this.name = name;
            this.model = model;
            this.brand = brand;
        }

        public Builder price(BigDecimal price) {
            if (price == null || price.compareTo(BigDecimal.ZERO) < 0)
                throw new IllegalArgumentException("Price must be non-negative");
            this.price = price;
            return this;
        }

        public Builder discount(double discount) {
            if (discount < 0 || discount > 100)
                throw new IllegalArgumentException("Discount must be between 0 and 100");
            this.discount = discount;
            return this;
        }

        public Builder colors(List<Color> colors) {
            if (colors == null || colors.isEmpty())
                throw new IllegalArgumentException("At least one color must be specified");
            this.colors = colors;
            return this;
        }

        public Builder sizes(List<Double> sizes) {
            if (sizes == null || sizes.isEmpty())
                throw new IllegalArgumentException("At least one size must be specified");
            this.sizes = sizes;
            return this;
        }

        public Builder shoeType(ShoeType shoeType) {
            if (shoeType == null)
                throw new IllegalArgumentException("ShoeType must not be null");
            this.shoeType = shoeType;
            return this;
        }

        public Builder material(Material material) {
            if (material == null)
                throw new IllegalArgumentException("Material must not be null");
            this.material = material;
            return this;
        }

        public Builder idealFor(IdealFor idealFor) {
            if (idealFor == null)
                throw new IllegalArgumentException("IdealFor must not be null");
            this.idealFor = idealFor;
            return this;
        }

        public Builder productDetails(String productDetails) {
            this.productDetails = productDetails == null ? "" : productDetails;
            return this;
        }

        /**
         * Validates all fields and constructs the immutable {@link Shoe} instance.
         */
        public Shoe build() {
            return new Shoe(this);
        }
    }
}
