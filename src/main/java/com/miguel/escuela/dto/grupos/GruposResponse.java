package com.miguel.escuela.dto.grupos;

import com.miguel.escuela.dto.datos.DatosAula;
import com.miguel.escuela.dto.datos.DatosCurso;
import com.miguel.escuela.dto.datos.DatosMaestro;

import java.util.List;

public record GruposResponse(
    Long id,
    DatosCurso curso,
    DatosMaestro maestro,
    DatosAula aula,
    List<String> horarios,
    String periodo
) {}
