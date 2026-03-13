package com.pedidos.backend.catalog.repository;

import com.pedidos.backend.catalog.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    //Búsqueda por nombre
    Optional<Category> findByNameIgnoreCase(String name);
}