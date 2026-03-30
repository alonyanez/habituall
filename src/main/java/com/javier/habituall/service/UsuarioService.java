package com.javier.habituall.service;

import com.javier.habituall.dto.AuthResponse;
import com.javier.habituall.dto.LoginRequest;
import com.javier.habituall.dto.RegisterRequest;
import com.javier.habituall.entity.Usuario;

public interface UsuarioService {
    Usuario register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    Usuario findByEmail(String email);
    Usuario findById(Long id);
}
