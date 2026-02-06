package com.ecommerce.salebazar.category.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private boolean active = true;

    private LocalDateTime createdAt;
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
