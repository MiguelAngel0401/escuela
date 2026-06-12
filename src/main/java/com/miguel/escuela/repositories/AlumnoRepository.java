package com.miguel.escuela.repositories;

import com.miguel.escuela.entities.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);


    @Query(value = "SELECT GENERAR_MATRICULA(:nombre, :apePat, :apeMat) FROM DUAL", nativeQuery = true)
    String generarMatricula(@Param("nombre") String nombre,
                            @Param("apePat") String apePat,
                            @Param("apeMat") String apeMat);

    @Query(value = "SELECT GENERAR_CORREO(:nombre, :apePat, :apeMat) FROM DUAL", nativeQuery = true)
    String generarCorreo(@Param("nombre") String nombre,
                         @Param("apePat") String apePat,
                         @Param("apeMat") String apeMat);
}