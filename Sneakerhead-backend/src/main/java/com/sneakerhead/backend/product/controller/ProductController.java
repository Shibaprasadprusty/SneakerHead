package com.sneakerhead.backend.product.controller;

import com.sneakerhead.backend.models.enums.Brand;
import com.sneakerhead.backend.models.enums.ShoeType;
import com.sneakerhead.backend.product.document.ShoeProduct;
import com.sneakerhead.backend.product.service.ShoeProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Public Product Controller — no authentication required.
 *
 * GET /api/products → all published products (Redis cached)
 * GET /api/products/{id} → single product by MongoDB id
 * GET /api/products/brand/{brand} → filter by brand (Redis cached)
 * GET /api/products/type/{type} → filter by shoe type
 * GET /api/products/search?q=keyword → search by name
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ShoeProductService productService;

    public ProductController(ShoeProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ShoeProduct>> getAllPublished() {
        return ResponseEntity.ok(productService.getPublishedProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoeProduct> getById(@PathVariable String id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<ShoeProduct>> getByBrand(@PathVariable Brand brand) {
        return ResponseEntity.ok(productService.getProductsByBrand(brand));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<ShoeProduct>> getByType(@PathVariable ShoeType type) {
        return ResponseEntity.ok(productService.getProductsByType(type));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ShoeProduct>> search(@RequestParam String q) {
        return ResponseEntity.ok(productService.searchProducts(q));
    }
}
