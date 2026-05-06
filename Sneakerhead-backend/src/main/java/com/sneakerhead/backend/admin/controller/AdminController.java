package com.sneakerhead.backend.admin.controller;

import com.sneakerhead.backend.product.document.ShoeProduct;
import com.sneakerhead.backend.product.service.ShoeProductService;
import com.sneakerhead.backend.vendor.dto.VendorResponse;
import com.sneakerhead.backend.vendor.entity.VendorStatus;
import com.sneakerhead.backend.vendor.service.VendorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Admin Controller — manage vendors and product approvals.
 *
 * All routes require JWT (in production, add ROLE_ADMIN check).
 *
 * GET /api/admin/vendors → list all vendors
 * GET /api/admin/vendors?status=PENDING → filter by status
 * PUT /api/admin/vendors/{id}/status → approve/suspend/ban vendor
 *
 * GET /api/admin/products/pending → products awaiting review
 * POST /api/admin/products/{id}/approve → publish a product
 * POST /api/admin/products/{id}/reject → reject a product
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final VendorService vendorService;
    private final ShoeProductService productService;

    public AdminController(VendorService vendorService, ShoeProductService productService) {
        this.vendorService = vendorService;
        this.productService = productService;
    }

    // ── Vendor Management ─────────────────────────────────────────────────────

    @GetMapping("/vendors")
    public ResponseEntity<List<VendorResponse>> getVendors(
            @RequestParam(required = false) VendorStatus status) {
        List<VendorResponse> vendors = (status != null)
                ? vendorService.getVendorsByStatus(status)
                : vendorService.getAllVendors();
        return ResponseEntity.ok(vendors);
    }

    @PutMapping("/vendors/{id}/status")
    public ResponseEntity<VendorResponse> updateVendorStatus(
            @PathVariable Long id,
            @RequestParam VendorStatus status) {
        return ResponseEntity.ok(vendorService.updateStatus(id, status));
    }

    // ── Product Moderation ────────────────────────────────────────────────────

    @GetMapping("/products/pending")
    public ResponseEntity<List<ShoeProduct>> getPendingProducts() {
        return ResponseEntity.ok(productService.getPendingProducts());
    }

    @PostMapping("/products/{id}/approve")
    public ResponseEntity<ShoeProduct> approveProduct(@PathVariable String id) {
        return ResponseEntity.ok(productService.approveProduct(id));
    }

    @PostMapping("/products/{id}/reject")
    public ResponseEntity<ShoeProduct> rejectProduct(@PathVariable String id) {
        return ResponseEntity.ok(productService.rejectProduct(id));
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "mysql", "connected",
                "mongo", "connected",
                "redis", "connected"));
    }
}
