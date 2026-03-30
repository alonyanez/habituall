package com.javier.habituall.service.impl;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.javier.habituall.dto.AuthResponse;
import com.javier.habituall.dto.LoginRequest;
import com.javier.habituall.dto.RegisterRequest;
import com.javier.habituall.entity.Usuario;
import com.javier.habituall.exception.ResourceNotFoundException;
import com.javier.habituall.exception.UserAlreadyExistException;
import com.javier.habituall.repository.UsuarioRepository;
import com.javier.habituall.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl  implements UsuarioService{

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEnoder;
    //private final AuthenticationManager authenticationManager;
    

    @Override
    public Usuario register(RegisterRequest request){

        //Comprobar si existe un correo asociado al usuario, en caso contrario crear el usuario
        if(usuarioRepository.findByEmail(request.getEmail()).isPresent()){
            throw new UserAlreadyExistException("El email ya está en uso");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEnoder.encode(request.getPassword()));

        return usuarioRepository.save(usuario);

        //Token temporal
        //String token = "dummy-token";
        
        //return new AuthResponse(token, usuario.getEmail(), usuario.getNombre());
    }


    @Override
    public AuthResponse login(LoginRequest request) {
        //authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("El usuario no se ha podido encontrar"));

            if(!passwordEnoder.matches(request.getPassword(), usuario.getPassword())){
                throw new UsernameNotFoundException("Contraseña incorrecta");
            }
        //Token
        String token = "dummy-token";
        return new AuthResponse(token, usuario.getEmail(), usuario.getNombre());
    }


    @Override
    public Usuario findByEmail(String email) {
       return usuarioRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("El usuario no se ha podido encontrar"));
    }


    @Override
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se puedo encontrar el usuario con el id:" + id));
    }
}
