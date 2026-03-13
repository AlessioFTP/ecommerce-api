package com.pedidos.backend.catalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {
    @NotBlank(message = "El nombre del producto es obligatorio")
    private String name;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
    private String description;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser estrictamente mayor a cero")
    private BigDecimal price;

    @NotNull(message = "El stock inicial es obligatorio")
    @PositiveOrZero(message = "El stock no puede ser un número negativo")
    private Integer initialStock;

    @NotNull(message = "El ID de la categoría es obligatorio")
    @Positive(message = "El ID de la categoría debe ser un número válido")
    private Long categoryId;

}