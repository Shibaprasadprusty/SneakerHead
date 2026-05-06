package com.sneakerhead.backend.product.document;

/**
 * Lifecycle status of a shoe product listing.
 *
 * DRAFT → saved but not yet submitted for review
 * PENDING → submitted, awaiting admin approval
 * PUBLISHED → live on the storefront
 * REJECTED → admin rejected the listing
 * ARCHIVED → vendor archived the listing
 */
public enum ProductStatus {
    DRAFT,
    PENDING,
    PUBLISHED,
    REJECTED,
    ARCHIVED
}
