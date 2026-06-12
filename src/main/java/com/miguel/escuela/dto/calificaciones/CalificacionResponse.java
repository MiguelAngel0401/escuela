package com.miguel.escuela.dto.calificaciones;

import com.miguel.escuela.dto.datos.DatosInscripcion;

import java.math.BigDecimal;

public record CalificacionResponse(
        Long id,
        DatosInscripcion inscripcion,
        BigDecimal calificacion,
        String fechaRegistro
) {}
