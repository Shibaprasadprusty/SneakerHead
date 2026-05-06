package com.sneakerhead.backend.vendor.controller;

import com.sneakerhead.backend.product.document.ProductStatus;
import com.sneakerhead.backend.product.document.ShoeProduct;
import com.sneakerhead.backend.product.dto.ShoeProductRequest;
import com.sneakerhead.backend.product.service.ShoeProductService;
import com.sneakerhead.backend.vendor.entity.Vendor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Vendor Panel — Product Management Controller.
 *
 * All routes require a valid JWT (ROLE_VENDOR).
 * Vendors can only manage their own products.
 *
 * POST /api/vendor/products → add a shoe (as draft)
 * POST /api/vendor/products?submit=true → add and immediately submit for review
 * GET /api/vendor/products → list all my products
 * GET /api/vendor/products/{id} → get one of my products
 * PUT /api/vendor/products/{id} → update a product
 * POST /api/vendor/products/{id}/submit → submit draft for review
 * POST /api/vendor/products/{id}/archive → archive a listing
 * DELETE /api/vendor/products/{id} → delete a draft/rejected product
 */
@RestController
@RequestMapping("/api/vendor/products")
public class VendorProductController {

    private final ShoeProductService productService;

    public VendorProductController(ShoeProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ShoeProduct> addProduct(
            @AuthenticationPrincipal Vendor vendor,
            @Valid @RequestBody ShoeProductRequest req,
            @RequestParam(defaultValue = "true") boolean draft) {

        ShoeProduct created = productService.createProduct(vendor.getId(), req, draft);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<ShoeProduct>> getMyProducts(
            @AuthenticationPrincipal Vendor vendor,
            @RequestParam(required = false) ProductStatus status) {

        List<ShoeProduct> products = (status != null)
                ? productService.getVendorProductsByStatus(vendor.getId(), status)
                : productService.getVendorProducts(vendor.getId());

        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoeProduct> getMyProduct(
            @AuthenticationPrincipal Vendor vendor,
            @PathVariable String id) {

        return ResponseEntity.ok(productService.getVendorProductById(vendor.getId(), id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShoeProduct> updateProduct(
            @AuthenticationPrincipal Vendor vendor,
            @PathVariable String id,
            @Valid @RequestBody ShoeProductRequest req) {

        return ResponseEntity.ok(productService.updateProduct(vendor.getId(), id, req));
    }

    @PostMapping("/{id}/submit")
    public ResponseEntity<ShoeProduct> submitForReview(
            @AuthenticationPrincipal Vendor vendor,
            @PathVariable String id) {

        return ResponseEntity.ok(productService.submitForReview(vendor.getId(), id));
    }

    @PostMapping("/{id}/archive")
    public ResponseEntity<ShoeProduct> archive(
            @AuthenticationPrincipal Vendor vendor,
            @PathVariable String id) {

        return ResponseEntity.ok(productService.archiveProduct(vendor.getId(), id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteProduct(
            @AuthenticationPrincipal Vendor vendor,
            @PathVariable String id) {

        productService.deleteProduct(vendor.getId(), id);
        return ResponseEntity.ok(Map.of("message", "Product deleted successfully"));
    }
}
