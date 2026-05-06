package com.sneakerhead.backend.product.dto;

import com.sneakerhead.backend.models.enums.Brand;
import com.sneakerhead.backend.models.enums.Color;
import com.sneakerhead.backend.models.enums.IdealFor;
import com.sneakerhead.backend.models.enums.Material;
import com.sneakerhead.backend.models.enums.ShoeType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Request DTO for creating or updating a shoe product listing.
 * Used by the vendor panel when adding/editing a shoe.
 */
public class ShoeProductRequest {

    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 150)
    private String name;

    @NotBlank(message = "Model identifier is required")
    @Size(min = 2, max = 80)
    private String model;

    @NotNull(message = "Brand is required")
    private Brand brand;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    private double discount = 0.0;

    @NotEmpty(message = "At least one color is required")
    private List<Color> colors;

    @NotEmpty(message = "At least one size is required")
    private List<Double> sizes;

    @NotNull(message = "Shoe type is required")
    private ShoeType shoeType;

    @NotNull(message = "Material is required")
    private Material material;

    @NotNull(message = "Ideal for is required")
    private IdealFor idealFor;

    @Size(max = 5000)
    private String productDetails;

    @Min(value = 0, message = "Stock quantity cannot be negative")
    private int stockQuantity;

    private String sku;

    private List<String> imageUrls;

    // Getters & Setters
    public String getName() {
        return name;
    }

    public void setName(String v) {
        this.name = v;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String v) {
        this.model = v;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand v) {
        this.brand = v;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal v) {
        this.price = v;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double v) {
        this.discount = v;
    }

    public List<Color> getColors() {
        return colors;
    }

    public void setColors(List<Color> v) {
        this.colors = v;
    }

    public List<Double> getSizes() {
        return sizes;
    }

    public void setSizes(List<Double> v) {
        this.sizes = v;
    }

    public ShoeType getShoeType() {
        return shoeType;
    }

    public void setShoeType(ShoeType v) {
        this.shoeType = v;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material v) {
        this.material = v;
    }

    public IdealFor getIdealFor() {
        return idealFor;
    }

    public void setIdealFor(IdealFor v) {
        this.idealFor = v;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String v) {
        this.productDetails = v;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int v) {
        this.stockQuantity = v;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String v) {
        this.sku = v;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> v) {
        this.imageUrls = v;
    }
}
