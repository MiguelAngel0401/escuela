package com.miguel.escuela.dto;

public record CustomErrorResponse(
        int codigo,
        String mensaje
) {
}

