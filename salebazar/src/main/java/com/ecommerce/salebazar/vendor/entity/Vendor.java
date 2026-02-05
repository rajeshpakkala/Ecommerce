package com.ecommerce.salebazar.vendor.entity;

import com.ecommerce.salebazar.user.entity.User;
import com.ecommerce.salebazar.vendor.enums.VendorStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String businessName;

    private boolean approved;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private VendorStatus vendorStatus;

    @Column(name = "rejection_reason")
    private String rejectionReason;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}

