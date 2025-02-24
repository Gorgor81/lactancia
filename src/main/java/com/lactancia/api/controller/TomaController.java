package com.lactancia.api.controller;

import com.lactancia.api.entity.Toma;
import com.lactancia.api.service.TomaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tomas")
public class TomaController {

    @Autowired
    private TomaService tomaService;

    // Obtener todas las tomas
    @GetMapping
    public List<Toma> getTomas() {
        return tomaService.getAllTomas();
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
    public ResponseEntity<Toma> createToma(@RequestBody Toma toma) {
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
