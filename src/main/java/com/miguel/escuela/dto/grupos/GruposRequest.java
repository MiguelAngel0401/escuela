package com.miguel.escuela.dto.grupos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record GruposRequest(
    @NotNull(message = "El id del curso es requerido")
    Long idCurso,

    @NotNull(message = "El de del maestro es requerdio")
    Long idMaestro,

    @NotNull(message = "El id del aula es requerida")
    Long idAula,

    @NotBlank(message = "El periodo es requerido")
    @Size(min = 4, max = 20, message = "El periodo debe de tenero entre 4 y 20 caracteres")
    String periodo
) {}
