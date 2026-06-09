package com.miguel.escuela.repositories;

import com.miguel.escuela.entities.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    boolean existsByEmail(String email);
    boolean existsByMatricula(String matricula);
    boolean existsByEmailAndIdNot(String email, Long id);
    boolean existsByMatriculaAndIdNot(String matricula, Long id);
}
