package com.lactancia.api.service;

import com.lactancia.api.entity.Toma;
import com.lactancia.api.repository.TomaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TomaService {

    @Autowired
    private TomaRepository tomaRepository;

    // Obtener todas las tomas
    public List<Toma> getAllTomas() {
        return tomaRepository.findAll();
    }

    // Obtener una toma por su ID
    public Optional<Toma> getTomaById(Long id) {
        return tomaRepository.findById(id);
    }

    // Crear una nueva toma
    public Toma createToma(Toma toma) {
        return tomaRepository.save(toma);
    }

    // Actualizar una toma existente
    public Optional<Toma> updateToma(Long id, Toma toma) {
        if (tomaRepository.existsById(id)) {
            toma.setId(id);
            return Optional.of(tomaRepository.save(toma));
        }
        return Optional.empty();
    }

    // Eliminar una toma
    public boolean deleteToma(Long id) {
        if (tomaRepository.existsById(id)) {
            tomaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
