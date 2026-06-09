package com.miguel.escuela.dto.aulas;

import jakarta.validation.constraints.*;

public record AulaRequest(
        @NotBlank(message = "El nombre es requerido")
        @Size(min = 5, max = 50, message = "El nombre del aula debe tener entre 5 y 50 caracteres")
        String nombre,

        @NotNull(message = "La capacidad es requerida")
        @Positive(message = "La capacidad debe de ser positiva")
        Integer capacidad
) {}
