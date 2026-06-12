package com.miguel.escuela.dto.calificaciones;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CalificacionRequest(
   @NotNull(message = "El id de la inscripcion es requerido")
   Long idInscripcion,

   @NotNull(message = "La calificacion es requerida")
   @DecimalMin(value = "0.0", message = "La calificacion no puede ser menor a 0")
   @DecimalMax(value = "10.0", message = "La calificacion no puede ser mayor a 10")
   BigDecimal calificacion
) {}
