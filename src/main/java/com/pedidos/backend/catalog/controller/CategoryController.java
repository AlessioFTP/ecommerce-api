package com.pedidos.backend.catalog.controller;


import com.pedidos.backend.catalog.dto.CategoryAdminResponse;
import com.pedidos.backend.catalog.dto.CategoryRequest;
import com.pedidos.backend.catalog.dto.CategoryResponse;
import com.pedidos.backend.catalog.service.impl.CategoryServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories(){
        List<CategoryResponse> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable long id){
        CategoryResponse category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest request){
        CategoryResponse categoryCreated = categoryService.createCategory(request);
        return new ResponseEntity<>(categoryCreated, HttpStatus.CREATED);
    }

    @GetMapping("/admin")
    public ResponseEntity<List<CategoryAdminResponse>> getAllCategoriesAdmin(){
        List<CategoryAdminResponse> categories = categoryService.getAllCategoriesAdmin();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<CategoryAdminResponse> getCategoryAdminById(@PathVariable long id){
        CategoryAdminResponse category = categoryService.getCategoryAdminById(id);
        return ResponseEntity.ok(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request) {
        CategoryResponse updated = categoryService.updateCategory(id, request);
        return ResponseEntity.ok(updated);
    }

}
