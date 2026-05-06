package com.sneakerhead.backend.security;

import com.sneakerhead.backend.vendor.entity.Vendor;
import com.sneakerhead.backend.vendor.repository.VendorRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * JWT authentication filter.
 *
 * On each request:
 * 1. Extracts the Bearer token from the Authorization header
 * 2. Checks the Redis blacklist (logout invalidation)
 * 3. Validates the JWT signature and expiry
 * 4. Loads the vendor from MySQL and sets the security context
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthFilter.class);

    private static final String BLACKLIST_PREFIX = "jwt:blacklist:";

    private final JwtUtil jwtUtil;
    private final VendorRepository vendorRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public JwtAuthFilter(JwtUtil jwtUtil,
            VendorRepository vendorRepository,
            RedisTemplate<String, Object> redisTemplate) {
        this.jwtUtil = jwtUtil;
        this.vendorRepository = vendorRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        // Check Redis blacklist (token was invalidated on logout)
        // Fault-tolerant: if Redis is down, skip blacklist check and continue
        try {
            Boolean isBlacklisted = redisTemplate.hasKey(BLACKLIST_PREFIX + token);
            if (Boolean.TRUE.equals(isBlacklisted)) {
                filterChain.doFilter(request, response);
                return;
            }
        } catch (Exception e) {
            log.debug("Redis unavailable for blacklist check: {}", e.getMessage());
        }

        if (!jwtUtil.isTokenValid(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        String email = jwtUtil.extractEmail(token);
        Vendor vendor = vendorRepository.findByEmail(email).orElse(null);

        if (vendor != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    vendor,
                    null,
                    List.of(new SimpleGrantedAuthority("ROLE_VENDOR")));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }
}
