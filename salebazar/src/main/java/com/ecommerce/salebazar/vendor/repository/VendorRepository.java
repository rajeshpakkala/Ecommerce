package com.ecommerce.salebazar.vendor.repository;

import com.ecommerce.salebazar.vendor.entity.Vendor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;

import java.util.List;

public interface VendorRepository extends JpaRepository<Vendor, Long> {

    @Query("""
        SELECT v FROM Vendor v
        JOIN FETCH v.user
        WHERE v.approved = false
    """)
    Page<Vendor> findPendingVendors(Pageable pageable);
}

