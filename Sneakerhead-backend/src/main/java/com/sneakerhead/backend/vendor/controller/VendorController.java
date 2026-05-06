package com.sneakerhead.backend.vendor.controller;

import com.sneakerhead.backend.vendor.dto.AuthResponse;
import com.sneakerhead.backend.vendor.dto.VendorLoginRequest;
import com.sneakerhead.backend.vendor.dto.VendorRegisterRequest;
import com.sneakerhead.backend.vendor.dto.VendorResponse;
import com.sneakerhead.backend.vendor.entity.Vendor;
import com.sneakerhead.backend.vendor.service.VendorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Vendor Auth & Profile Controller.
 *
 * Public:
 * POST /api/vendor/register → register a new vendor
 * POST /api/vendor/login → login, receive JWT
 *
 * Protected (requires JWT):
 * POST /api/vendor/logout → invalidate token in Redis
 * GET /api/vendor/me → get own profile
 * PUT /api/vendor/me → update own profile
 */
@RestController
@RequestMapping("/api/vendor")
public class VendorController {

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    // ── Public ────────────────────────────────────────────────────────────────

    @PostMapping("/register")
    public ResponseEntity<VendorResponse> register(@Valid @RequestBody VendorRegisterRequest req) {
        VendorResponse response = vendorService.register(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody VendorLoginRequest req) {
        AuthResponse response = vendorService.login(req);
        return ResponseEntity.ok(response);
    }

    // ── Protected ─────────────────────────────────────────────────────────────

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
        vendorService.logout(token);
        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }

    @GetMapping("/me")
    public ResponseEntity<VendorResponse> getProfile(
            @AuthenticationPrincipal Vendor vendor) {
        return ResponseEntity.ok(vendorService.getProfile(vendor.getId()));
    }

    @PutMapping("/me")
    public ResponseEntity<VendorResponse> updateProfile(
            @AuthenticationPrincipal Vendor vendor,
            @Valid @RequestBody VendorRegisterRequest req) {
        return ResponseEntity.ok(vendorService.updateProfile(vendor.getId(), req));
    }
}
