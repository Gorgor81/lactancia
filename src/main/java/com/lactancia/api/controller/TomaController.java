package com.lactancia.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.google.firebase.auth.FirebaseToken;
import com.lactancia.api.entity.Toma;
import com.lactancia.api.entity.Usuario;
import com.lactancia.api.service.FirebaseAuthService;
import com.lactancia.api.service.TomaService;
import com.lactancia.api.service.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/tomas")
public class TomaController {

	@Autowired
	private TomaService tomaService;

	@GetMapping("/{usuarioId}")
	public List<Toma> getTomasByUsuario(@PathVariable Long usuarioId) {
		return tomaService.getTomasByUsuario(usuarioId);
	}

	@PostMapping("/{usuarioId}")
	public ResponseEntity<Toma> createToma(@PathVariable Long usuarioId, @RequestBody Toma toma) {
		return ResponseEntity.ok(tomaService.saveToma(usuarioId, toma));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Toma> updateToma(@PathVariable Long id, @RequestBody Toma toma) {
		return ResponseEntity.ok(tomaService.updateToma(id, toma));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteToma(@PathVariable Long id) {
		tomaService.deleteToma(id);
		return ResponseEntity.noContent().build();
	}
}
