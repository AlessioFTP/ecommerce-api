package com.pedidos.backend.user.service;

import com.pedidos.backend.user.dto.AuthResponse;
import com.pedidos.backend.user.dto.LoginRequest;
import com.pedidos.backend.user.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}
