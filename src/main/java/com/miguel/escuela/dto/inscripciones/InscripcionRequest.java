package com.miguel.escuela.dto.inscripciones;

import jakarta.validation.constraints.NotNull;

public record InscripcionRequest(
        @NotNull(message = "El id del alumno es requerido")
        Long idAlumno,

        @NotNull(message = "El id del grupo es requerido")
        Long idGrupo
) {}