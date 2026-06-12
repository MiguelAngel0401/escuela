package com.miguel.escuela.repositories;

import com.miguel.escuela.entities.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
    boolean existsByInscripcionId(Long inscripcionId);
}