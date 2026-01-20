package com.ecommerce.salebazar.user.repository;

import com.ecommerce.salebazar.vendor.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends JpaRepository<Vendor,Long> {
}
