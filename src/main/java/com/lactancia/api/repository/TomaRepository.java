package com.lactancia.api.repository;

import com.lactancia.api.entity.Toma;
import com.lactancia.api.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TomaRepository extends JpaRepository<Toma, Long> {

    // Buscar tomas por usuario
    List<Toma> findByUsuario(Usuario usuario);

    // Verificar si existe una toma para un usuario, fecha y tipo específicos
    boolean existsByUsuarioAndFechaAndTipo(Usuario usuario, String fecha, String tipo);
}
