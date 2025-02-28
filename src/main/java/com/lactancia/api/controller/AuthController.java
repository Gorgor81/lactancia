package com.lactancia.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lactancia.api.entity.Usuario;
import com.lactancia.api.service.UsuarioService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody LoginRequest loginRequest) {
        Optional<Usuario> usuario = usuarioService.validarUsuario(loginRequest.getEmail(), loginRequest.getPassword());
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get()); // Devuelve el usuario autenticado
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }
}