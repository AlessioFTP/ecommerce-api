package com.pedidos.backend.catalog.service.impl;

import com.pedidos.backend.catalog.dto.CategoryAdminResponse;
import com.pedidos.backend.catalog.dto.CategoryRequest;
import com.pedidos.backend.catalog.dto.CategoryResponse;
import com.pedidos.backend.catalog.exception.BadRequestException;
import com.pedidos.backend.catalog.exception.ResourceNotFoundException;
import com.pedidos.backend.catalog.model.Category;
import com.pedidos.backend.catalog.repository.CategoryRepository;
import com.pedidos.backend.catalog.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {
        //Validar si el nombre ya existe
        if (categoryRepository.findByNameIgnoreCase(request.getName()).isPresent()) {
            throw new BadRequestException("Ya existe una categoría con el nombre: " + request.getName());
        }

        //Mapear DTO a un objeto
        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .state(true)
                .build();

        //Guardar y mapear a Response
        Category savedCategory = categoryRepository.save(category);
        return mapToResponse(savedCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + id));
        return mapToResponse(category);
    }

    @Override
    @Transactional
    public CategoryAdminResponse getCategoryAdminById(Long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + id));
        return mapToResponseAdmin(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .filter(Category::getState)
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryAdminResponse> getAllCategoriesAdmin() {
        return categoryRepository.findAll().stream()
                .map(this::mapToResponseAdmin)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        //Buscar la categoría existente
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se puede actualizar. Categoría no encontrada."));

        //Validar que el nuevo nómbre sea único.
        categoryRepository.findByNameIgnoreCase(request.getName())
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id)) {
                        throw new BadRequestException("El nombre '" + request.getName() + "' ya está en uso por otra categoría.");
                    }
                });

        //Actualizar campos
        category.setName(request.getName());
        category.setDescription(request.getDescription());

        Category updatedCategory = categoryRepository.save(category);
        return mapToResponse(updatedCategory);
    }

    private CategoryResponse mapToResponse(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setDescription(category.getDescription());
        return response;
    }

    private CategoryAdminResponse mapToResponseAdmin(Category category) {
        CategoryAdminResponse response = new CategoryAdminResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setDescription(category.getDescription());
        response.setState(category.getState());
        return response;
    }
}
