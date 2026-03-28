package com.pedidos.backend.user.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String roleName;

}
