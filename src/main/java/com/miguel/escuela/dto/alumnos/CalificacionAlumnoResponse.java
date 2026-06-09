package com.miguel.escuela.dto.alumnos;

import java.math.BigDecimal;

public record CalificacionAlumnoResponse(
        String curso,
        String periodo,
        BigDecimal calificacion
) {}
