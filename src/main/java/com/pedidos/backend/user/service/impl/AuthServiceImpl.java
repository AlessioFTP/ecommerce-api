package com.pedidos.backend.user.service.impl;


import com.pedidos.backend.exception.BadRequestException;
import com.pedidos.backend.user.dto.AuthResponse;
import com.pedidos.backend.user.dto.LoginRequest;
import com.pedidos.backend.user.dto.RegisterRequest;
import com.pedidos.backend.user.model.Role;
import com.pedidos.backend.user.model.User;
import com.pedidos.backend.user.repository.RoleRepository;
import com.pedidos.backend.user.repository.UserRepository;
import com.pedidos.backend.user.service.AuthService;
import com.pedidos.backend.user.service.JwtService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new BadRequestException("Credenciales inválidas");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        Role userRole = roleRepository.findByRoleName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Error: El rol ROLE_USER no existe en la base de datos."));

        Set<Role> roles = new HashSet<>();
        roles.add(userRole);

        User newUser = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(encodedPassword)
                .state(true)
                .roles(roles)
                .build();

        User savedUser = userRepository.save(newUser);

        AuthResponse response = new AuthResponse();
        response.setName(savedUser.getFirstName() + " " + savedUser.getLastName());
        String token = jwtService.generateToken(savedUser);
        response.setToken(token);

        return response;
    }


    @Override
    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new BadRequestException("Credenciales inválidas."));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new BadRequestException("Credenciales inválidas.");
        }

        AuthResponse response = new AuthResponse();
        response.setName(user.getFirstName() + " " + user.getLastName());
        String token = jwtService.generateToken(user);
        response.setToken(token);

        return response;
    }
}
