package com.miguel.escuela.dto.horarios;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record HorarioRequest(
        @NotNull(message = "El id del grupo es requerido")
        Long idGrupo,

        @NotBlank(message = "El dia es requerdio")
        String dia,

        @NotBlank(message = "La hora de inicio es requerida")
        @Pattern(regexp = "^([01][0-9]|2[0-3]):[0-5][0-9]$")
        String horaInicio,

        @NotBlank(message = "La hora de finalizacion es requerida")
        @Pattern(regexp = "^([01][0-9]|2[0-3]):[0-5][0-9]$")
        String horaFin
) {}
