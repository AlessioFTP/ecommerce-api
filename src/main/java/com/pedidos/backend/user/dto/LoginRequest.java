package com.pedidos.backend.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "El correo es obligatorio para el inicio de sesión.")
    @Email(message = "Ingrese un correo valido.")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria para el inicio de sesión")
    private String password;
}
