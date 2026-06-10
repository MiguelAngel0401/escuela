package com.miguel.escuela.dto.cursos;

import jakarta.validation.constraints.*;

public record CursoRequest(
        @NotBlank(message = "El nombre es requerido")
        @Size(min = 5, max = 100, message = "El nombre del curso debe tener entre 5 y 100 caracteres")
        String nombre,

        @Size( max = 200, message = "La descripcion debe tener maximo 200 caracteres")
        String descripcion,

        @NotNull(message = "Los creditos son requeridos")
        @Min(value = 1, message = "Los creditos minimos son 1")
        @Max(value = 10, message = "Los creditos maximos son 10")
        Integer creditos
) {}
