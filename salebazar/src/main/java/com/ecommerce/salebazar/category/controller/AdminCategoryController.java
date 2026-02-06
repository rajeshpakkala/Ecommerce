package com.ecommerce.salebazar.category.controller;

import com.ecommerce.salebazar.category.dto.*;
import com.ecommerce.salebazar.category.service.CategoryServiceImpl;
import com.ecommerce.salebazar.common.dto.AuthResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/salebazar/admin/categories")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminCategoryController {

    private final CategoryServiceImpl categoryService;

    @PostMapping("/add/categories")
    public ResponseEntity<AuthResponseDTO<CategoryResponseDTO>> createCategory(
            @RequestBody CategoryRequestDTO dto) {

        return ResponseEntity.ok(categoryService.createCategory(dto));
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<AuthResponseDTO<CategoryResponseDTO>> updateCategory(
            @PathVariable Long id,
            @RequestBody CategoryRequestDTO dto) {

        return ResponseEntity.ok(categoryService.updateCategory(id, dto));
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<AuthResponseDTO<CategoryResponseDTO>> deleteCategory(
            @PathVariable Long id) {

        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }
}
