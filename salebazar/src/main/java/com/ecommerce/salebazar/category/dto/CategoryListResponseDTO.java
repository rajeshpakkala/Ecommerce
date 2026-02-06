package com.ecommerce.salebazar.category.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryListResponseDTO {
    private List<CategoryResponseDTO> categories;
}

