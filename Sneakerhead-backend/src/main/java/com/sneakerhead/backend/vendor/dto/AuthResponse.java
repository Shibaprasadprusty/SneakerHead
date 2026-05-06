package com.sneakerhead.backend.vendor.dto;

/**
 * Response DTO returned after successful login.
 * Contains the JWT token and basic vendor info.
 */
public class AuthResponse {

    private String token;
    private String tokenType = "Bearer";
    private Long vendorId;
    private String email;
    private String businessName;

    public AuthResponse() {
    }

    public AuthResponse(String token, Long vendorId, String email, String businessName) {
        this.token = token;
        this.vendorId = vendorId;
        this.email = email;
        this.businessName = businessName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String v) {
        this.token = v;
    }

    public String getTokenType() {
        return tokenType;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long v) {
        this.vendorId = v;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String v) {
        this.email = v;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String v) {
        this.businessName = v;
    }
}
