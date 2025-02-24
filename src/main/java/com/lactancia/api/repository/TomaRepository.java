package com.lactancia.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lactancia.api.entity.Toma;

@Repository
public interface TomaRepository extends JpaRepository<Toma, Long> {
    List<Toma> findByFecha(String fecha);
}
