package com.pedidos.backend.catalog.service;

import com.pedidos.backend.catalog.dto.ProductAdminResponse;
import com.pedidos.backend.catalog.dto.ProductRequest;
import com.pedidos.backend.catalog.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request);

    ProductResponse updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);

    ProductResponse getProductById(Long id);

    Page<ProductResponse> getAllProducts(Pageable pageable);

    Page<ProductResponse> getProductsByFilters(String name, BigDecimal minPrice, BigDecimal maxPrice, Long categoryId, Pageable pageable);

    ProductAdminResponse getProductAdminById(Long id);

    Page<ProductAdminResponse> getAllProductsAdmin(Pageable pageable);
}