package com.javier.habituall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javier.habituall.dto.AuthResponse;
import com.javier.habituall.dto.LoginRequest;
import com.javier.habituall.dto.RegisterRequest;
import com.javier.habituall.entity.Usuario;
import com.javier.habituall.service.UsuarioService;

@RestController
@RequestMapping("/api/test")
public class TestController {
    
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        System.out.println("Register request: " + request);
        if (request == null || request.getEmail() == null) {
            return ResponseEntity.badRequest().body("Bad request body");
        }

        Usuario saved = usuarioService.register(request);
        saved.setPassword(null);
        return ResponseEntity.ok(saved);

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            AuthResponse response = usuarioService.login(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }

    @GetMapping("/find-by-email")
    public ResponseEntity<?> findByEmail(@RequestParam String email) {
        Usuario usuario = usuarioService.findByEmail(email);
        if (usuario != null) {
            usuario.setPassword(null);
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, world!";
    }
 
}
