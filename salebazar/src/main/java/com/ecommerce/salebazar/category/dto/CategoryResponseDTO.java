package com.ecommerce.salebazar.category.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDTO {
    private Long id;
    private String name;
    private boolean active;
    private LocalDateTime createdAt;
}
