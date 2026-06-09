package com.miguel.escuela.dto.datos;

import java.math.BigDecimal;

public record DatosCalificacion(
        String curso,
        String periodo,
        BigDecimal calificacion
) {}
