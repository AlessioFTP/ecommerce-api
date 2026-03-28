package com.pedidos.backend.catalog.service.impl;

import com.pedidos.backend.catalog.dto.*;
import com.pedidos.backend.exception.ResourceNotFoundException;
import com.pedidos.backend.catalog.model.Category;
import com.pedidos.backend.catalog.model.Product;
import com.pedidos.backend.catalog.repository.CategoryRepository;
import com.pedidos.backend.catalog.repository.ProductRepository;
import com.pedidos.backend.catalog.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        //Validar Categoría
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));

        //Generar SKU único
        String generatedSku = generateSku(request.getName(), category.getName());

        //Mapear DTO a un objeto
        Product product = Product.builder()
                .sku(generatedSku)
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getInitialStock())
                .category(category)
                .state(true)
                .build();

        return mapToResponse(productRepository.save(product));
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .filter(Product::getState)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));
        return mapToResponse(product);
    }
    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        return productRepository.findByFilters(null, null, null, null, pageable)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));
        product.setState(false);
        productRepository.save(product);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductAdminResponse getProductAdminById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));
        return mapToResponseAdmin(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductAdminResponse> getAllProductsAdmin(Pageable pageable) {
        return productRepository.findAll(pageable).map(this::mapToResponseAdmin);
    }

    private String generateSku(String productName, String categoryName) {
        String prefix = (productName.substring(0, Math.min(productName.length(), 3))).toUpperCase();
        String cat = (categoryName.substring(0, Math.min(categoryName.length(), 3))).toUpperCase();
        String unique = UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        return prefix + "-" + cat + "-" + unique;
    }

    private ProductResponse mapToResponse(Product product) {
        ProductResponse res = new ProductResponse();
        res.setId(product.getId());
        res.setSku(product.getSku());
        res.setName(product.getName());
        res.setDescription(product.getDescription());
        res.setPrice(product.getPrice());
        res.setStock(product.getStock());

        CategorySummary catSummary = new CategorySummary();
        catSummary.setId(product.getCategory().getId());
        catSummary.setName(product.getCategory().getName());
        res.setCategory(catSummary);

        return res;
    }

    private ProductAdminResponse mapToResponseAdmin(Product product) {
        ProductAdminResponse res = new ProductAdminResponse();
        res.setId(product.getId());
        res.setSku(product.getSku());
        res.setName(product.getName());
        res.setDescription(product.getDescription());
        res.setPrice(product.getPrice());
        res.setStock(product.getStock());

        CategorySummary catSummary = new CategorySummary();
        catSummary.setId(product.getCategory().getId());
        catSummary.setName(product.getCategory().getName());
        res.setCategory(catSummary);

        res.setState(product.getState());
        res.setVersion(product.getVersion());
        return res;
    }

    @Transactional
    @Override
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        //Buscar el producto
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));

        //Actualizar campos
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getInitialStock());
        product.setDescription(request.getDescription());
        product.setCategory(category);

        Product updatedProduct = productRepository.save(product);
        return mapToResponse(updatedProduct);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> getProductsByFilters(String name, BigDecimal minPrice, BigDecimal maxPrice, Long categoryId, Pageable pageable) {
        return productRepository.findByFilters(name, minPrice, maxPrice, categoryId, pageable)
                .map(this::mapToResponse);
    }
}