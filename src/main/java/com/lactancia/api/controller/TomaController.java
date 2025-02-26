package com.lactancia.api.controller;

import com.lactancia.api.entity.Toma;
import com.lactancia.api.entity.Usuario;
import com.lactancia.api.service.TomaService;
import com.lactancia.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tomas")
public class TomaController {

    @Autowired
    private TomaService tomaService;

    @Autowired
    private UsuarioService usuarioService;

    // Obtener todas las tomas del usuario autenticado
    @GetMapping
    public List<Toma> getTomas() {
        // Obtener el nombre de usuario desde el contexto de seguridad
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Buscar el usuario autenticado
        Usuario usuario = usuarioService.getUsuarioByUsername(username);

        // Si el usuario no existe, devolver un error
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }

        // Filtrar las tomas del usuario
        return tomaService.getTomasByUsuario(usuario);
    }

    // Obtener una toma por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Toma> getTomaById(@PathVariable Long id) {
        Optional<Toma> toma = tomaService.getTomaById(id);
        return toma.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Crear una nueva toma
    @PostMapping
    @Transactional
    public ResponseEntity<Toma> createToma(@RequestBody Toma toma) {
        // Obtener el usuario autenticado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioService.getUsuarioByUsername(username);

        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }

        // Asociar el usuario autenticado con la nueva toma
        toma.setUsuario(usuario);

        // Verificar si ya existe una toma con la misma fecha, tipo y usuario
        if (tomaService.existsByUsuarioAndFechaAndTipo(usuario, toma.getFecha(), toma.getTipo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya existe una toma para este usuario en esta fecha");
        }

        // Crear la nueva toma
        Toma nuevaToma = tomaService.createToma(toma);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaToma);
    }

    // Actualizar una toma existente
    @PutMapping("/{id}")
    public ResponseEntity<Toma> updateToma(@PathVariable Long id, @RequestBody Toma toma) {
        Optional<Toma> updatedToma = tomaService.updateToma(id, toma);
        return updatedToma.map(ResponseEntity::ok)
                          .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Eliminar una toma
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteToma(@PathVariable Long id) {
        boolean deleted = tomaService.deleteToma(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
