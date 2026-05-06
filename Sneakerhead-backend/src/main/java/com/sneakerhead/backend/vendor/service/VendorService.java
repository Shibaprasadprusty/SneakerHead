package com.sneakerhead.backend.vendor.service;

import com.sneakerhead.backend.security.JwtUtil;
import com.sneakerhead.backend.vendor.dto.AuthResponse;
import com.sneakerhead.backend.vendor.dto.VendorLoginRequest;
import com.sneakerhead.backend.vendor.dto.VendorRegisterRequest;
import com.sneakerhead.backend.vendor.dto.VendorResponse;
import com.sneakerhead.backend.vendor.entity.Vendor;
import com.sneakerhead.backend.vendor.entity.VendorStatus;
import com.sneakerhead.backend.vendor.repository.VendorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Date;
import java.util.List;

/**
 * Vendor service — handles registration, login, logout, and profile management.
 *
 * Database usage:
 * - MySQL → persists vendor accounts (via VendorRepository / JPA)
 * - Redis → caches vendor profiles, stores JWT blacklist on logout
 * (all Redis calls are fault-tolerant — app works without Redis)
 */
@Service
@Transactional
public class VendorService {

    private static final Logger log = LoggerFactory.getLogger(VendorService.class);

    private static final String BLACKLIST_PREFIX = "jwt:blacklist:";
    private static final String PROFILE_CACHE_KEY = "vendor:profile:";

    private final VendorRepository vendorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, Object> redisTemplate;

    public VendorService(VendorRepository vendorRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil,
            RedisTemplate<String, Object> redisTemplate) {
        this.vendorRepository = vendorRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.redisTemplate = redisTemplate;
    }

    // ── Registration ──────────────────────────────────────────────────────────

    /**
     * Registers a new vendor account.
     * Saves to MySQL. Status starts as PENDING (admin must approve).
     */
    public VendorResponse register(VendorRegisterRequest req) {
        if (vendorRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email already registered: " + req.getEmail());
        }

        Vendor vendor = new Vendor();
        vendor.setOwnerName(req.getOwnerName());
        vendor.setEmail(req.getEmail().toLowerCase().trim());
        vendor.setPassword(passwordEncoder.encode(req.getPassword()));
        vendor.setBusinessName(req.getBusinessName());
        vendor.setBusinessDescription(req.getBusinessDescription());
        vendor.setPhone(req.getPhone());
        vendor.setAddress(req.getAddress());
        vendor.setCity(req.getCity());
        vendor.setState(req.getState());
        vendor.setPincode(req.getPincode());
        vendor.setStatus(VendorStatus.PENDING);

        Vendor saved = vendorRepository.save(vendor);
        return toResponse(saved);
    }

    // ── Login ─────────────────────────────────────────────────────────────────

    /**
     * Authenticates a vendor and returns a JWT token.
     * The token is NOT stored in Redis on login — only on logout (blacklist).
     */
    public AuthResponse login(VendorLoginRequest req) {
        Vendor vendor = vendorRepository.findByEmail(req.getEmail().toLowerCase().trim())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(req.getPassword(), vendor.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        if (vendor.getStatus() == VendorStatus.BANNED) {
            throw new IllegalStateException("Your account has been banned");
        }
        if (vendor.getStatus() == VendorStatus.SUSPENDED) {
            throw new IllegalStateException("Your account is suspended");
        }

        String token = jwtUtil.generateToken(vendor.getEmail(), vendor.getId());
        return new AuthResponse(token, vendor.getId(), vendor.getEmail(), vendor.getBusinessName());
    }

    // ── Logout ────────────────────────────────────────────────────────────────

    /**
     * Invalidates a JWT by adding it to the Redis blacklist.
     * TTL is set to the token's remaining lifetime so Redis auto-cleans it.
     * If Redis is unavailable, the logout still succeeds (token just won't be
     * blacklisted).
     */
    public void logout(String token) {
        if (token != null && jwtUtil.isTokenValid(token)) {
            Date expiry = jwtUtil.extractExpiration(token);
            long ttlMs = expiry.getTime() - System.currentTimeMillis();
            if (ttlMs > 0) {
                redisSet(BLACKLIST_PREFIX + token, "blacklisted", Duration.ofMillis(ttlMs));
            }
        }
    }

    // ── Profile ───────────────────────────────────────────────────────────────

    /**
     * Returns vendor profile. Checks Redis cache first, falls back to MySQL.
     */
    @Transactional(readOnly = true)
    public VendorResponse getProfile(Long vendorId) {
        String cacheKey = PROFILE_CACHE_KEY + vendorId;

        // Try Redis cache (fault-tolerant)
        try {
            Object cached = redisTemplate.opsForValue().get(cacheKey);
            if (cached instanceof VendorResponse vr) {
                return vr;
            }
        } catch (Exception e) {
            log.debug("Redis unavailable for profile cache read: {}", e.getMessage());
        }

        // Fallback to MySQL
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new IllegalArgumentException("Vendor not found: " + vendorId));

        VendorResponse response = toResponse(vendor);

        // Cache in Redis for 15 minutes (fault-tolerant)
        redisSet(cacheKey, response, Duration.ofMinutes(15));

        return response;
    }

    /**
     * Updates vendor profile in MySQL and invalidates the Redis cache.
     */
    public VendorResponse updateProfile(Long vendorId, VendorRegisterRequest req) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new IllegalArgumentException("Vendor not found: " + vendorId));

        vendor.setOwnerName(req.getOwnerName());
        vendor.setBusinessName(req.getBusinessName());
        vendor.setBusinessDescription(req.getBusinessDescription());
        vendor.setPhone(req.getPhone());
        vendor.setAddress(req.getAddress());
        vendor.setCity(req.getCity());
        vendor.setState(req.getState());
        vendor.setPincode(req.getPincode());

        Vendor updated = vendorRepository.save(vendor);

        // Invalidate Redis cache (fault-tolerant)
        redisDelete(PROFILE_CACHE_KEY + vendorId);

        return toResponse(updated);
    }

    // ── Admin ─────────────────────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<VendorResponse> getAllVendors() {
        return vendorRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<VendorResponse> getVendorsByStatus(VendorStatus status) {
        return vendorRepository.findByStatus(status).stream().map(this::toResponse).toList();
    }

    public VendorResponse updateStatus(Long vendorId, VendorStatus newStatus) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new IllegalArgumentException("Vendor not found: " + vendorId));
        vendor.setStatus(newStatus);
        Vendor updated = vendorRepository.save(vendor);
        redisDelete(PROFILE_CACHE_KEY + vendorId);
        return toResponse(updated);
    }

    // ── Redis helpers (fault-tolerant) ────────────────────────────────────────

    /**
     * Checks if a key exists in Redis. Returns false if Redis is unavailable.
     */
    public boolean redisHasKey(String key) {
        try {
            return Boolean.TRUE.equals(redisTemplate.hasKey(key));
        } catch (Exception e) {
            log.debug("Redis unavailable for hasKey({}): {}", key, e.getMessage());
            return false;
        }
    }

    private void redisSet(String key, Object value, Duration ttl) {
        try {
            redisTemplate.opsForValue().set(key, value, ttl);
        } catch (Exception e) {
            log.debug("Redis unavailable for set({}): {}", key, e.getMessage());
        }
    }

    private void redisDelete(String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            log.debug("Redis unavailable for delete({}): {}", key, e.getMessage());
        }
    }

    // ── Mapper ────────────────────────────────────────────────────────────────

    private VendorResponse toResponse(Vendor v) {
        VendorResponse r = new VendorResponse();
        r.setId(v.getId());
        r.setOwnerName(v.getOwnerName());
        r.setEmail(v.getEmail());
        r.setBusinessName(v.getBusinessName());
        r.setBusinessDescription(v.getBusinessDescription());
        r.setPhone(v.getPhone());
        r.setAddress(v.getAddress());
        r.setCity(v.getCity());
        r.setState(v.getState());
        r.setPincode(v.getPincode());
        r.setStatus(v.getStatus());
        r.setEmailVerified(v.isEmailVerified());
        r.setCreatedAt(v.getCreatedAt());
        r.setUpdatedAt(v.getUpdatedAt());
        return r;
    }
}
