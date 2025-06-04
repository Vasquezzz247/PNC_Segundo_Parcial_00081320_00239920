package com.api.parcial2.service;

import com.api.parcial2.entity.Category;
import com.api.parcial2.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAll();

    List<Product> filterByCategoryAndAvailability(Category category, Boolean available);

    Optional<Product> findById(Long id);

    Product save(Product product);

    void delete(Long id);

    Product updateStock(Long id, int newQuantity);
}
