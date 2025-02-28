package com.lactancia.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lactancia.api.entity.Toma;
import com.lactancia.api.entity.Usuario;
import com.lactancia.api.repository.TomaRepository;
import com.lactancia.api.repository.UsuarioRepository;

@Service
public class TomaService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TomaRepository tomaRepository;

	public List<Toma> getTomasByUsuario(Long usuarioId) {
		return tomaRepository.findByUsuarioId(usuarioId);
	}

	public Toma saveToma(Long usuarioId, Toma toma) {
		Usuario usuario = usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
		toma.setUsuario(usuario);
		return tomaRepository.save(toma);
	}

	public Toma updateToma(Long id, Toma toma) {
		if (tomaRepository.existsById(id)) {
			toma.setId(id);
			return tomaRepository.save(toma);
		}
		throw new RuntimeException("Toma no encontrada");
	}

	public void deleteToma(Long id) {
		tomaRepository.deleteById(id);
	}
}
