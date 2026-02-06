package com.ecommerce.salebazar.category.controller;

import com.ecommerce.salebazar.category.dto.*;
import com.ecommerce.salebazar.category.service.CategoryServiceImpl;
import com.ecommerce.salebazar.common.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/salebazar/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    @GetMapping("/fetch/list")
    public ResponseEntity<AuthResponseDTO<PagedResponseDTO<CategoryResponseDTO>>>
    getCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(
                categoryService.getActiveCategories(page, size)
        );
    }
}
