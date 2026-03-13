package com.pedidos.backend.catalog.repository;

import com.pedidos.backend.catalog.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    //Búsqueda por nombre
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    //Búsqueda por precio mayor o igual a
    Page<Product> findByPriceGreaterThanEqual(BigDecimal minPrice, Pageable pageable);

    //Búsqueda por precio menor o igual a
    Page<Product> findByPriceLessThanEqual(BigDecimal maxPrice, Pageable pageable);

    //Búsqueda por Categoría
    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);

    //Búsqueda combinada de rango de precios
    Page<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    //Búsqueda solo productos activos por categoría
    Page<Product> findByCategoryIdAndStateTrue(Long categoryId, Pageable pageable);
}