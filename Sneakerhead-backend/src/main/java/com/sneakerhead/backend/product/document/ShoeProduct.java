package com.sneakerhead.backend.product.document;

import com.sneakerhead.backend.models.enums.Brand;
import com.sneakerhead.backend.models.enums.Color;
import com.sneakerhead.backend.models.enums.IdealFor;
import com.sneakerhead.backend.models.enums.Material;
import com.sneakerhead.backend.models.enums.ShoeType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * MongoDB Document — Shoe product listing.
 *
 * Stored in MongoDB because shoe products have a flexible, rich schema:
 * - Colors vary per shoe (list)
 * - Sizes vary per shoe (list)
 * - Product details are unstructured text
 * - Future fields (tags, specs, reviews) can be added without migrations
 *
 * Collection: shoe_products
 */
@Document(collection = "shoe_products")
@CompoundIndex(name = "idx_brand_type", def = "{'brand': 1, 'shoeType': 1}")
@CompoundIndex(name = "idx_vendor_status", def = "{'vendorId': 1, 'status': 1}")
public class ShoeProduct {

    @Id
    private String id; // MongoDB ObjectId (string)

    // ── Vendor reference (FK to MySQL vendors table) ──────────────────────────
    @Indexed
    private Long vendorId; // references Vendor.id in MySQL

    private String vendorBusinessName; // denormalized for fast display

    // ── Core product fields ───────────────────────────────────────────────────

    private String name; // e.g. "Air Max 270"
    private String model; // e.g. "NK-AM270-WB"

    @Indexed
    private Brand brand;

    private BigDecimal price;
    private double discount; // percentage 0–100

    private List<Color> colors; // multiple colorways
    private List<Double> sizes; // e.g. [6.0, 7.0, 8.0, 9.0]

    @Indexed
    private ShoeType shoeType;

    private Material material;
    private IdealFor idealFor;

    private String productDetails; // long description / highlights

    // ── Inventory ─────────────────────────────────────────────────────────────

    private int stockQuantity;
    private String sku; // vendor's internal SKU

    // ── Media ─────────────────────────────────────────────────────────────────

    private List<String> imageUrls; // list of image URLs / paths

    // ── Status ────────────────────────────────────────────────────────────────

    @Indexed
    private ProductStatus status = ProductStatus.DRAFT;

    // ── Timestamps ────────────────────────────────────────────────────────────

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // ── Constructors ──────────────────────────────────────────────────────────

    public ShoeProduct() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // ── Derived ───────────────────────────────────────────────────────────────

    /**
     * Effective price after applying discount.
     */
    public BigDecimal getEffectivePrice() {
        if (price == null || discount <= 0)
            return price;
        BigDecimal factor = BigDecimal.valueOf(1 - (discount / 100.0));
        return price.multiply(factor).setScale(2, java.math.RoundingMode.HALF_UP);
    }

    public void touch() {
        this.updatedAt = LocalDateTime.now();
    }

    // ── Getters & Setters ─────────────────────────────────────────────────────

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long v) {
        this.vendorId = v;
    }

    public String getVendorBusinessName() {
        return vendorBusinessName;
    }

    public void setVendorBusinessName(String v) {
        this.vendorBusinessName = v;
    }

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

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus v) {
        this.status = v;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
