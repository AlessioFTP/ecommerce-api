package com.pedidos.backend.catalog.service;

import com.pedidos.backend.catalog.dto.CategoryAdminResponse;
import com.pedidos.backend.catalog.dto.CategoryRequest;
import com.pedidos.backend.catalog.dto.CategoryResponse;
import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);

    CategoryResponse getCategoryById(Long id);

    CategoryAdminResponse getCategoryAdminById(Long id);

    List<CategoryResponse> getAllCategories();

    List<CategoryAdminResponse> getAllCategoriesAdmin();

    CategoryResponse updateCategory(Long id, CategoryRequest request);

}