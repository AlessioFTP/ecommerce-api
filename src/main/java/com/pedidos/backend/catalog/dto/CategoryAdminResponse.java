package com.pedidos.backend.catalog.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CategoryAdminResponse extends CategoryResponse{
    private Boolean state;

}