package com.pedidos.backend.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "El correo es obligatorio para el registro.")
    @Email(message = "Ingrese un correo valido.")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria para el registro")
    private String password;

    @NotBlank(message = "El nombre del usuario es obligatorio para el registro")
    private String firstName;

    @NotBlank(message = "El apellido del usuario es obligatorio para el registro")
    private String lastName;

}
