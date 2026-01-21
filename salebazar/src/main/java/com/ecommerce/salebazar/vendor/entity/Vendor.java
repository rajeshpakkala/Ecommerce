package com.ecommerce.salebazar.vendor.entity;

import com.ecommerce.salebazar.user.entity.User;
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

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}

