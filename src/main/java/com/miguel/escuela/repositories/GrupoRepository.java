package com.miguel.escuela.repositories;

import com.miguel.escuela.entities.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {

    boolean existsByMaestroId(Long idMaestro);

    boolean existsByAulaId(Long idAula);
}
