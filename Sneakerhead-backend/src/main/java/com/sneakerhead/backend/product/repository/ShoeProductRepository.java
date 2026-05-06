package com.sneakerhead.backend.product.repository;

import com.sneakerhead.backend.models.enums.Brand;
import com.sneakerhead.backend.models.enums.ShoeType;
import com.sneakerhead.backend.product.document.ProductStatus;
import com.sneakerhead.backend.product.document.ShoeProduct;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MongoDB Repository for {@link ShoeProduct}.
 *
 * Spring Data MongoDB auto-implements all query methods from method names.
 */
@Repository
public interface ShoeProductRepository extends MongoRepository<ShoeProduct, String> {

    // ── Vendor-scoped queries ─────────────────────────────────────────────────

    List<ShoeProduct> findByVendorId(Long vendorId);

    List<ShoeProduct> findByVendorIdAndStatus(Long vendorId, ProductStatus status);

    long countByVendorId(Long vendorId);

    long countByVendorIdAndStatus(Long vendorId, ProductStatus status);

    // ── Public storefront queries ─────────────────────────────────────────────

    List<ShoeProduct> findByStatus(ProductStatus status);

    List<ShoeProduct> findByBrandAndStatus(Brand brand, ProductStatus status);

    List<ShoeProduct> findByShoeTypeAndStatus(ShoeType shoeType, ProductStatus status);

    List<ShoeProduct> findByNameContainingIgnoreCaseAndStatus(String keyword, ProductStatus status);

    // ── Admin queries ─────────────────────────────────────────────────────────

    List<ShoeProduct> findByStatusOrderByCreatedAtDesc(ProductStatus status);
}
