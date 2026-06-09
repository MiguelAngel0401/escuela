package com.miguel.escuela.repositories;

import com.miguel.escuela.entities.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    boolean existsByNombre(String nombre);
    boolean existsByNombreAndIdNot(String nombre,Long id);

}
