package com.sneakerhead.backend.vendor.repository;

import com.sneakerhead.backend.vendor.entity.Vendor;
import com.sneakerhead.backend.vendor.entity.VendorStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * MySQL JPA Repository for {@link Vendor}.
 */
@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {

    Optional<Vendor> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Vendor> findByStatus(VendorStatus status);

    List<Vendor> findByBusinessNameContainingIgnoreCase(String keyword);
}
