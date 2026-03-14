package com.pedidos.backend.catalog.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductAdminResponse extends ProductResponse{
    private Boolean state;
    private Integer version;
}