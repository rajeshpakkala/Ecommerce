package com.ecommerce.salebazar.category.interfaces;

import com.ecommerce.salebazar.category.dto.*;
import com.ecommerce.salebazar.common.dto.*;

public interface CategoryService {

    AuthResponseDTO<CategoryResponseDTO> createCategory(CategoryRequestDTO dto);

    AuthResponseDTO<CategoryResponseDTO> updateCategory(Long id, CategoryRequestDTO dto);

    AuthResponseDTO<CategoryResponseDTO> deleteCategory(Long id);

    AuthResponseDTO<PagedResponseDTO<CategoryResponseDTO>>
    getActiveCategories(int page, int size);}

