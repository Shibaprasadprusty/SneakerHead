package com.sneakerhead.backend.vendor.entity;

/**
 * Lifecycle status of a Vendor account.
 *
 * PENDING → registered but not yet approved by admin
 * ACTIVE → approved, can list shoes
 * SUSPENDED → temporarily blocked
 * BANNED → permanently removed
 */
public enum VendorStatus {
    PENDING,
    ACTIVE,
    SUSPENDED,
    BANNED
}
