package com.miguel.escuela.dto.alumnos;
import java.math.BigDecimal;
import java.util.List;

public record AlumnoResponse(
    Long id,
    String nombre,
    String email,
    String matricula,
    String fechaIngreso,
    BigDecimal promedio

)  {}
