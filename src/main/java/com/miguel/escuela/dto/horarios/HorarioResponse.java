package com.miguel.escuela.dto.horarios;

import com.miguel.escuela.dto.datos.DatosGrupo;

public record HorarioResponse(
    Long id,
    DatosGrupo grupo,
    String horario
) {}
