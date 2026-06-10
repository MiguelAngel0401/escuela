package com.miguel.escuela.dto.maestros;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MaestroRequest(
    @NotBlank(message = "El nombre es requerido")
    @Size(min = 4, max = 50, message = "El nombre del maestro debe tener entre 4 y 50 caracteres")
    String nombre,

    @NotBlank(message = "El apellido paterno es requerido")
    @Size(min = 4, max = 50, message = "El apellido paterno debe tener entre 4 y 50 caracteres")
    String apellidoPaterno,

    @NotBlank(message = "El apellido materno es requerido")
    @Size(min = 4, max = 50, message = "El apellido materno debe tener entre 4 y 50 caracteres")
    String apellidoMaterno,

    @NotBlank(message = "El email es requerido")
    @Size(min = 8, max = 100, message = "El email debe tener entre 8 y 100 caracteres")
    @Email(message = "El email no tiene un formato valido(ejemplo@dominio.com")
    String email,

    @NotBlank(message = "El telefono es requerido")
    @Pattern(regexp = "^[0-9]{10}$", message = "El teléfono debe tener exactamente 10 dígitos")
    String telefono
) {}
