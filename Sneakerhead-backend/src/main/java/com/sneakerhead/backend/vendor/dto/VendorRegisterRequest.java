package com.sneakerhead.backend.vendor.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request DTO for vendor registration.
 */
public class VendorRegisterRequest {

    @NotBlank(message = "Owner name is required")
    @Size(min = 2, max = 100)
    private String ownerName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @NotBlank(message = "Business name is required")
    @Size(min = 3, max = 150)
    private String businessName;

    private String businessDescription;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String pincode;

    // Getters & Setters
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
}
