package com.api.parcial2.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    private String description;

    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    private BigDecimal price;

    @Min(value = 0, message = "La cantidad no puede ser negativa")
    private Integer quantity;

    private Boolean available;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Category category;

    @Future(message = "La fecha debe estar en el futuro")
    private Date expirationDate;
}
