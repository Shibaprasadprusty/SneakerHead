package com.sneakerhead.backend.vendor.dto;

import com.sneakerhead.backend.vendor.entity.VendorStatus;

import java.time.LocalDateTime;

/**
 * Response DTO for vendor profile — never exposes the password hash.
 */
public class VendorResponse {

    private Long id;
    private String ownerName;
    private String email;
    private String businessName;
    private String businessDescription;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String pincode;
    private VendorStatus status;
    private boolean emailVerified;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long v) {
        this.id = v;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String v) {
        this.ownerName = v;
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

    public String getBusinessDescription() {
        return businessDescription;
    }

    public void setBusinessDescription(String v) {
        this.businessDescription = v;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String v) {
        this.phone = v;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String v) {
        this.address = v;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String v) {
        this.city = v;
    }

    public String getState() {
        return state;
    }

    public void setState(String v) {
        this.state = v;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String v) {
        this.pincode = v;
    }

    public VendorStatus getStatus() {
        return status;
    }

    public void setStatus(VendorStatus v) {
        this.status = v;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean v) {
        this.emailVerified = v;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime v) {
        this.createdAt = v;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime v) {
        this.updatedAt = v;
    }
}
