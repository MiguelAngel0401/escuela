package com.miguel.escuela.mappers;

import com.miguel.escuela.dto.datos.DatosAula;
import com.miguel.escuela.dto.datos.DatosCurso;
import com.miguel.escuela.dto.datos.DatosMaestro;
import com.miguel.escuela.dto.grupos.GruposRequest;
import com.miguel.escuela.dto.grupos.GruposResponse;
import com.miguel.escuela.entities.Aula;
import com.miguel.escuela.entities.Curso;
import com.miguel.escuela.entities.Grupo;
import com.miguel.escuela.entities.Maestro;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class GrupoMapper implements CommonMapper<GruposRequest, GruposResponse, Grupo> {

    @Override
    public Grupo requestAEntidad(GruposRequest request) {
        if(request == null) return null;

        return Grupo.builder()
                .periodo(request.periodo().trim())
                .build();
    }

    @Override
    public GruposResponse entidadAResponse(Grupo entidad) {
        if(entidad ==  null)return null;

        List<String>horarios = entidad.getHorarios().stream()
                .map(horario -> horario.getDia().getDescripcion()
                + " " + horario.getHoraInicio() + " - " +
                        horario.getHoraFin())
                .toList();

        return new GruposResponse(
                entidad.getId(),
                cursoDatos(entidad.getCurso()),
                maestroDatos(entidad.getMaestro()),
                aulaDatos(entidad.getAula()),
                horarios,
                entidad.getPeriodo()
        );
    }

    private DatosCurso cursoDatos(Curso curso) {
        if (curso == null) return null;
        return new DatosCurso(curso.getNombre(), curso.getDescripcion(), curso.getCreditos());
    }

    private DatosMaestro maestroDatos(Maestro maestro) {
        if (maestro == null) return null;
        return new DatosMaestro(
                String.join(" ", maestro.getNombre(), maestro.getApellidoPaterno(), maestro.getApellidoMaterno()),
                maestro.getEmail(),
                maestro.getTelefono()
        );
    }

    private DatosAula aulaDatos(Aula aula) {
        if (aula == null) return null;
        return new DatosAula(aula.getNombre(), aula.getCapacidad());
    }
}
