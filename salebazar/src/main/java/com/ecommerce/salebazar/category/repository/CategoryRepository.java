package com.ecommerce.salebazar.category.repository;

import com.ecommerce.salebazar.category.entity.Category;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findByNameIgnoreCase(String name);
    Page<Category> findByActiveTrue(Pageable pageable);

    List<Category> findByActiveTrue();
}
