package com.miguel.escuela.mappers;

import com.miguel.escuela.dto.datos.DatosGrupo;
import com.miguel.escuela.dto.horarios.HorarioRequest;
import com.miguel.escuela.dto.horarios.HorarioResponse;
import com.miguel.escuela.entities.Horario;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class HorarioMapper implements CommonMapper<HorarioRequest, HorarioResponse, Horario> {

    @Override
    public Horario requestAEntidad(HorarioRequest request) {
        if (request == null) return null;

        return Horario.builder()
                .horaInicio(request.horaInicio().trim())
                .horaFin(request.horaFin().trim())
                .build();
    }

    @Override
    public HorarioResponse entidadAResponse(Horario entidad) {
        if (entidad == null) return null;

        return new HorarioResponse(
                entidad.getId(),
                grupoDatos(entidad),
                entidad.getDia().getDescripcion() + " " + entidad.getHoraInicio() + " " + entidad.getHoraFin()
        );
    }

    private DatosGrupo grupoDatos(Horario entidad) {
        return new DatosGrupo(
                entidad.getGrupo().getCurso().getNombre(),
                String.join(" ",
                        entidad.getGrupo().getMaestro().getNombre(),
                        entidad.getGrupo().getMaestro().getApellidoPaterno(),
                        entidad.getGrupo().getMaestro().getApellidoMaterno()),
                entidad.getGrupo().getAula().getNombre(),
                entidad.getGrupo().getPeriodo()
        );
    }
}