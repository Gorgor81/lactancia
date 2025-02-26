package com.lactancia.api.repository;

import com.lactancia.api.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	// Método para encontrar un usuario por su nombre de usuario (suponiendo que tienes un campo "username")
    Usuario findByUsername(String username);
}
