package com.api.parcial2.service.impl;

import com.api.parcial2.entity.Category;
import com.api.parcial2.entity.Product;
import com.api.parcial2.repository.ProductRepository;
import com.api.parcial2.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<Product> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Product> filterByCategoryAndAvailability(Category category, Boolean available) {
        return repository.findByCategoryAndAvailable(category, available);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Product save(Product product) {
        if (repository.existsByNameIgnoreCase(product.getName())) {
            throw new IllegalArgumentException("El nombre del producto ya existe.");
        }

        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0.");
        }

        if (product.getQuantity() == null || product.getQuantity() == 0) {
            product.setAvailable(false);
        } else {
            product.setAvailable(true);
        }

        if (product.getExpirationDate() != null && product.getExpirationDate().before(new Date())) {
            throw new IllegalArgumentException("La fecha de expiración debe ser válida (después de hoy).");
        }

        return repository.save(product);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (product.getCategory() == Category.INGREDIENT && Boolean.TRUE.equals(product.getAvailable())) {
            throw new IllegalStateException("No se puede eliminar un ingrediente disponible.");
        }

        repository.delete(product);
    }

    @Override
    public Product updateStock(Long id, int newQuantity) {
        if (newQuantity == 0) {
            throw new IllegalArgumentException("No se permite establecer el stock en 0.");
        }

        Product product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        product.setQuantity(newQuantity);
        product.setAvailable(newQuantity > 0);

        return repository.save(product);
    }
}
