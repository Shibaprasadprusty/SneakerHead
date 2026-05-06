package com.sneakerhead.backend.product.service;

import com.sneakerhead.backend.models.enums.Brand;
import com.sneakerhead.backend.models.enums.ShoeType;
import com.sneakerhead.backend.product.document.ProductStatus;
import com.sneakerhead.backend.product.document.ShoeProduct;
import com.sneakerhead.backend.product.dto.ShoeProductRequest;
import com.sneakerhead.backend.product.repository.ShoeProductRepository;
import com.sneakerhead.backend.vendor.entity.Vendor;
import com.sneakerhead.backend.vendor.entity.VendorStatus;
import com.sneakerhead.backend.vendor.repository.VendorRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ShoeProductService — manages shoe product listings for vendors.
 *
 * Database usage:
 * - MongoDB → stores and queries shoe product documents (via
 * ShoeProductRepository)
 * - MySQL → validates vendor existence and status (via VendorRepository)
 * - Redis → caches public product lists via @Cacheable / @CacheEvict
 */
@Service
public class ShoeProductService {

    private final ShoeProductRepository productRepository;
    private final VendorRepository vendorRepository;

    public ShoeProductService(ShoeProductRepository productRepository,
            VendorRepository vendorRepository) {
        this.productRepository = productRepository;
        this.vendorRepository = vendorRepository;
    }

    // ── Vendor: Create ────────────────────────────────────────────────────────

    /**
     * Creates a new shoe product listing for the given vendor.
     * Saves to MongoDB. Evicts the vendor's product cache in Redis.
     *
     * @param vendorId the authenticated vendor's MySQL id
     * @param req      the product form data
     * @param asDraft  if true, saves as DRAFT; if false, submits for review
     *                 (PENDING)
     */
    @CacheEvict(value = { "products:vendor", "products:all" }, allEntries = true)
    public ShoeProduct createProduct(Long vendorId, ShoeProductRequest req, boolean asDraft) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new IllegalArgumentException("Vendor not found: " + vendorId));

        if (vendor.getStatus() != VendorStatus.ACTIVE) {
            throw new IllegalStateException(
                    "Only ACTIVE vendors can list products. Current status: " + vendor.getStatus());
        }

        ShoeProduct product = mapRequestToDocument(req, vendorId, vendor.getBusinessName());
        product.setStatus(asDraft ? ProductStatus.DRAFT : ProductStatus.PENDING);

        return productRepository.save(product);
    }

    // ── Vendor: Update ────────────────────────────────────────────────────────

    /**
     * Updates an existing product. Only the owning vendor can update it.
     * Evicts all relevant Redis caches.
     */
    @CacheEvict(value = { "products:vendor", "products:all", "products:brand" }, allEntries = true)
    public ShoeProduct updateProduct(Long vendorId, String productId, ShoeProductRequest req) {
        ShoeProduct existing = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));

        if (!existing.getVendorId().equals(vendorId)) {
            throw new SecurityException("You do not own this product listing");
        }

        // Cannot edit a published product directly — must archive first
        if (existing.getStatus() == ProductStatus.PUBLISHED) {
            throw new IllegalStateException(
                    "Published products cannot be edited directly. Archive the listing first.");
        }

        mapRequestToDocument(req, existing);
        existing.touch();

        return productRepository.save(existing);
    }

    // ── Vendor: Submit for Review ─────────────────────────────────────────────

    /**
     * Submits a DRAFT product for admin review (changes status to PENDING).
     */
    @CacheEvict(value = "products:vendor", allEntries = true)
    public ShoeProduct submitForReview(Long vendorId, String productId) {
        ShoeProduct product = getOwnedProduct(vendorId, productId);

        if (product.getStatus() != ProductStatus.DRAFT) {
            throw new IllegalStateException("Only DRAFT products can be submitted for review");
        }

        product.setStatus(ProductStatus.PENDING);
        product.touch();
        return productRepository.save(product);
    }

    // ── Vendor: Archive ───────────────────────────────────────────────────────

    /**
     * Archives a product (removes from storefront without deleting).
     */
    @CacheEvict(value = { "products:vendor", "products:all", "products:brand" }, allEntries = true)
    public ShoeProduct archiveProduct(Long vendorId, String productId) {
        ShoeProduct product = getOwnedProduct(vendorId, productId);
        product.setStatus(ProductStatus.ARCHIVED);
        product.touch();
        return productRepository.save(product);
    }

    // ── Vendor: Delete ────────────────────────────────────────────────────────

    /**
     * Permanently deletes a DRAFT or REJECTED product.
     */
    @CacheEvict(value = { "products:vendor", "products:all" }, allEntries = true)
    public void deleteProduct(Long vendorId, String productId) {
        ShoeProduct product = getOwnedProduct(vendorId, productId);

        if (product.getStatus() == ProductStatus.PUBLISHED) {
            throw new IllegalStateException("Cannot delete a published product. Archive it first.");
        }

        productRepository.deleteById(productId);
    }

    // ── Vendor: Read own listings ─────────────────────────────────────────────

    /**
     * Returns all products for a vendor (all statuses).
     * Cached in Redis under "products:vendor".
     */
    @Cacheable(value = "products:vendor", key = "#vendorId")
    public List<ShoeProduct> getVendorProducts(Long vendorId) {
        return productRepository.findByVendorId(vendorId);
    }

    public List<ShoeProduct> getVendorProductsByStatus(Long vendorId, ProductStatus status) {
        return productRepository.findByVendorIdAndStatus(vendorId, status);
    }

    public ShoeProduct getVendorProductById(Long vendorId, String productId) {
        return getOwnedProduct(vendorId, productId);
    }

    // ── Public Storefront ─────────────────────────────────────────────────────

    /**
     * Returns all published products. Cached in Redis under "products:all".
     */
    @Cacheable(value = "products:all", key = "'published'")
    public List<ShoeProduct> getPublishedProducts() {
        return productRepository.findByStatus(ProductStatus.PUBLISHED);
    }

    /**
     * Returns published products filtered by brand. Cached in Redis under
     * "products:brand".
     */
    @Cacheable(value = "products:brand", key = "#brand.name()")
    public List<ShoeProduct> getProductsByBrand(Brand brand) {
        return productRepository.findByBrandAndStatus(brand, ProductStatus.PUBLISHED);
    }

    public List<ShoeProduct> getProductsByType(ShoeType shoeType) {
        return productRepository.findByShoeTypeAndStatus(shoeType, ProductStatus.PUBLISHED);
    }

    public List<ShoeProduct> searchProducts(String keyword) {
        return productRepository.findByNameContainingIgnoreCaseAndStatus(keyword, ProductStatus.PUBLISHED);
    }

    public ShoeProduct getProductById(String productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));
    }

    // ── Admin ─────────────────────────────────────────────────────────────────

    /**
     * Admin approves a product — changes status from PENDING to PUBLISHED.
     */
    @CacheEvict(value = { "products:all", "products:brand" }, allEntries = true)
    public ShoeProduct approveProduct(String productId) {
        ShoeProduct product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));

        if (product.getStatus() != ProductStatus.PENDING) {
            throw new IllegalStateException("Only PENDING products can be approved");
        }

        product.setStatus(ProductStatus.PUBLISHED);
        product.touch();
        return productRepository.save(product);
    }

    /**
     * Admin rejects a product — changes status from PENDING to REJECTED.
     */
    @CacheEvict(value = "products:vendor", allEntries = true)
    public ShoeProduct rejectProduct(String productId) {
        ShoeProduct product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));
        product.setStatus(ProductStatus.REJECTED);
        product.touch();
        return productRepository.save(product);
    }

    public List<ShoeProduct> getPendingProducts() {
        return productRepository.findByStatusOrderByCreatedAtDesc(ProductStatus.PENDING);
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private ShoeProduct getOwnedProduct(Long vendorId, String productId) {
        ShoeProduct product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));
        if (!product.getVendorId().equals(vendorId)) {
            throw new SecurityException("You do not own this product listing");
        }
        return product;
    }

    private ShoeProduct mapRequestToDocument(ShoeProductRequest req, Long vendorId, String businessName) {
        ShoeProduct p = new ShoeProduct();
        p.setVendorId(vendorId);
        p.setVendorBusinessName(businessName);
        mapRequestToDocument(req, p);
        return p;
    }

    private void mapRequestToDocument(ShoeProductRequest req, ShoeProduct p) {
        p.setName(req.getName());
        p.setModel(req.getModel());
        p.setBrand(req.getBrand());
        p.setPrice(req.getPrice());
        p.setDiscount(req.getDiscount());
        p.setColors(req.getColors());
        p.setSizes(req.getSizes());
        p.setShoeType(req.getShoeType());
        p.setMaterial(req.getMaterial());
        p.setIdealFor(req.getIdealFor());
        p.setProductDetails(req.getProductDetails());
        p.setStockQuantity(req.getStockQuantity());
        p.setSku(req.getSku());
        p.setImageUrls(req.getImageUrls());
    }
}
