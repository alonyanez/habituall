package com.javier.habituall.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.javier.habituall.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String Email);
}