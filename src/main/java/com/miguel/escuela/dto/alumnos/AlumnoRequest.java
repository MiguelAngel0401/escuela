package com.miguel.escuela.dto.alumnos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AlumnoRequest(
        @NotBlank(message = "El nombre es requerido")
        @Size(min = 5, max = 50, message = "El nombre del alumno debe tener entre 7 y 50 caracteres")
        String nombre,

        @NotBlank(message = "El apellidio paterno es requerido")
        @Size(min = 5, max = 50, message = "El apellido paterno es requerido")
        String apellidoPaterno,

        @NotBlank(message = "El apellido materno es requerido")
        @Size(min = 5, max = 50, message = "El apellido paterno es requerido")
        String apellidoMaterno
) {}
