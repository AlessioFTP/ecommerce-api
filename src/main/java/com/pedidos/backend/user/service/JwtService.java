package com.pedidos.backend.user.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(UserDetails userDetails);

    String extractUsername(String token);

    Boolean isTokenValid(String token, UserDetails userDetails);

}
