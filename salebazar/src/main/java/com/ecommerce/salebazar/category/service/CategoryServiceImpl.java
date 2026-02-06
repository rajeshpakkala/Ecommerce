package com.ecommerce.salebazar.category.service;

import com.ecommerce.salebazar.category.interfaces.CategoryService;
import com.ecommerce.salebazar.category.dto.*;
import com.ecommerce.salebazar.category.entity.Category;
import com.ecommerce.salebazar.category.repository.CategoryRepository;
import com.ecommerce.salebazar.common.dto.*;
import com.ecommerce.salebazar.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public AuthResponseDTO<CategoryResponseDTO> createCategory(CategoryRequestDTO dto) {

        Category category = Category.builder()
                .name(dto.getName())
                .active(true)
                .build();

        Category saved = categoryRepository.save(category);

        return AuthResponseDTO.<CategoryResponseDTO>builder()
                .status(true)
                .responseCode(201)
                .message("Category created successfully")
                .data(mapToResponse(saved))
                .build();
    }

    @Override
    public AuthResponseDTO<CategoryResponseDTO> updateCategory(
            Long id, CategoryRequestDTO dto) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));

        category.setName(dto.getName());

        return AuthResponseDTO.<CategoryResponseDTO>builder()
                .status(true)
                .responseCode(200)
                .message("Category updated successfully")
                .data(mapToResponse(categoryRepository.save(category)))
                .build();
    }

    @Override
    public AuthResponseDTO<CategoryResponseDTO> deleteCategory(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));

        category.setActive(false);

        return AuthResponseDTO.<CategoryResponseDTO>builder()
                .status(true)
                .responseCode(200)
                .message("Category deleted successfully")
                .data(mapToResponse(categoryRepository.save(category)))
                .build();
    }

    @Override
    public AuthResponseDTO<PagedResponseDTO<CategoryResponseDTO>>
    getActiveCategories(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categoryPage =
                categoryRepository.findByActiveTrue(pageable);

        PagedResponseDTO<CategoryResponseDTO> pagedResponse =
                PagedResponseDTO.<CategoryResponseDTO>builder()
                        .content(
                                categoryPage.getContent()
                                        .stream()
                                        .map(this::mapToResponse)
                                        .toList()
                        )
                        .page(categoryPage.getNumber())
                        .size(categoryPage.getSize())
                        .totalElements(categoryPage.getTotalElements())
                        .totalPages(categoryPage.getTotalPages())
                        .last(categoryPage.isLast())
                        .build();

        return AuthResponseDTO.<PagedResponseDTO<CategoryResponseDTO>>builder()
                .status(true)
                .responseCode(200)
                .message("Categories fetched successfully")
                .data(pagedResponse)
                .build();
    }

    private CategoryResponseDTO mapToResponse(Category category) {
        return CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .active(category.isActive())
                .createdAt(category.getCreatedAt())
                .build();
    }
}
