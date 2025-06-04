package com.api.parcial2.repository;

import com.api.parcial2.entity.Product;
import com.api.parcial2.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByNameIgnoreCase(String name);
    Optional<Product> findByNameIgnoreCase(String name);
    List<Product> findByCategoryAndAvailable(Category category, Boolean available);
}
