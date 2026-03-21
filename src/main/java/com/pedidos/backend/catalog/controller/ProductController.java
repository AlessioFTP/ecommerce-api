package com.pedidos.backend.catalog.controller;


import com.pedidos.backend.catalog.dto.ProductAdminResponse;
import com.pedidos.backend.catalog.dto.ProductRequest;
import com.pedidos.backend.catalog.dto.ProductResponse;
import com.pedidos.backend.catalog.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getProducts(@RequestParam(required = false) String name,
                                                             @RequestParam(required = false) BigDecimal minPrice,
                                                             @RequestParam(required = false) BigDecimal maxPrice,
                                                             @RequestParam(required = false) Long categoryId,
                                                             @PageableDefault(size = 10) Pageable pageable) {
        Page<ProductResponse> products = productService.getProductsByFilters(name, minPrice, maxPrice, categoryId, pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable long id){
        ProductResponse productResponse = productService.getProductById(id);
        return ResponseEntity.ok(productResponse);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request){
        ProductResponse productCreated = productService.createProduct(request);
        return new ResponseEntity<>(productCreated, HttpStatus.CREATED);
    }

    @GetMapping("/admin")
    public ResponseEntity<Page<ProductAdminResponse>> getProductsAdmin(@PageableDefault(size = 20) Pageable pageable){
        Page<ProductAdminResponse> products = productService.getAllProductsAdmin(pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("admin/{id}")
    public ResponseEntity<ProductAdminResponse> getProductAdminById(@PathVariable long id){
        ProductAdminResponse productResponse = productService.getProductAdminById(id);
        return ResponseEntity.ok(productResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable long id,
                                                         @Valid @RequestBody ProductRequest request){
        ProductResponse productUpdated = productService.updateProduct(id, request);
        return ResponseEntity.ok(productUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
