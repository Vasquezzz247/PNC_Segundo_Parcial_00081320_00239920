package com.api.parcial2.controller;

import com.api.parcial2.entity.Category;
import com.api.parcial2.entity.Product;
import com.api.parcial2.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<Product> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return service.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado :("));
    }

    @GetMapping("/filter")
    public List<Product> filter(
            @RequestParam Category category,
            @RequestParam Boolean available
    ) {
        return service.filterByCategoryAndAvailability(category, available);
    }

    @PostMapping
    public Product create(@Valid @RequestBody Product product) {
        return service.save(product);
    }

    @PutMapping("/{id}/stock")
    public Product updateStock(
            @PathVariable Long id,
            @RequestParam int quantity
    ) {
        return service.updateStock(id, quantity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
