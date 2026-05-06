package com.sneakerhead.backend.vendor.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * MySQL Entity — Vendor / Seller account.
 *
 * Stored in MySQL because vendor accounts are structured, relational data
 * that benefit from ACID transactions and foreign-key integrity.
 *
 * Table: vendors
 */
@Entity
@Table(name = "vendors", indexes = {
        @Index(name = "idx_vendor_email", columnList = "email", unique = true),
        @Index(name = "idx_vendor_business_name", columnList = "businessName")
})
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ── Identity ──────────────────────────────────────────────────────────────

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(nullable = false)
    private String ownerName;

    @NotBlank
    @Email
    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String password; // BCrypt hashed

    @NotBlank
    @Size(min = 3, max = 150)
    @Column(nullable = false)
    private String businessName;

    @Size(max = 500)
    private String businessDescription;

    // ── Contact ───────────────────────────────────────────────────────────────

    @Size(max = 15)
    private String phone;

    @Size(max = 300)
    private String address;

    @Size(max = 100)
    private String city;

    @Size(max = 100)
    private String state;

    @Size(max = 10)
    private String pincode;

    // ── Status ────────────────────────────────────────────────────────────────

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VendorStatus status = VendorStatus.PENDING;

    private boolean emailVerified = false;

    // ── Timestamps ────────────────────────────────────────────────────────────

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // ── Constructors ──────────────────────────────────────────────────────────

    public Vendor() {
    }

    // ── Getters & Setters ─────────────────────────────────────────────────────

    public Long getId() {
        return id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String v) {
        this.password = v;
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
