package com.miguel.escuela.dto.inscripciones;

import com.miguel.escuela.dto.datos.DatosAlumno;
import com.miguel.escuela.dto.datos.DatosGrupo;

import java.math.BigDecimal;

public record InscripcionResponse(
        Long id,
        DatosAlumno alumno,
        DatosGrupo grupo,
        BigDecimal calificacion,
        String fechaInscripcion
) {}