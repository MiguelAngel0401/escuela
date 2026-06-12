package com.miguel.escuela.mappers;

import com.miguel.escuela.dto.alumnos.AlumnoRequest;
import com.miguel.escuela.dto.alumnos.AlumnoResponse;
import com.miguel.escuela.dto.datos.DatosCalificacion;
import com.miguel.escuela.entities.Alumno;
import com.miguel.escuela.entities.Inscripcion;
import com.miguel.escuela.utils.StringCustomUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class AlumnoMapper implements CommonMapper<AlumnoRequest, AlumnoResponse, Alumno>{

    @Override
    public Alumno requestAEntidad(AlumnoRequest request) {
        if(request == null) return null;

        return Alumno.builder()
                .nombre(request.nombre().trim())
                .apellidoPaterno(request.apellidoPaterno().trim())
                .apellidoMaterno(request.apellidoMaterno().trim())
                .build();
    }

    @Override
    public AlumnoResponse entidadAResponse(Alumno entidad) {
        if(entidad == null) return null;

        List<DatosCalificacion> calificaciones = entidadDatosCalificacion(entidad);

        return new AlumnoResponse(
                entidad.getId(),
                String.join(" ",
                        entidad.getNombre(),
                        entidad.getApellidoPaterno(),
                        entidad.getApellidoMaterno()
                ),
                entidad.getEmail(),
                entidad.getMatricula(),
                StringCustomUtils.localDateAString(entidad.getFechaIngreso()),
                calificaciones,
                entidad.calcularPromedio()
        );
    }

    private List<DatosCalificacion> entidadDatosCalificacion(Alumno entidad){
        if (entidad.getInscripciones() == null) return List.of();

        return entidad.getInscripciones().stream()
                .map(this::inscripcionesADatosCalificacion)
                .toList();
    }

    private DatosCalificacion inscripcionesADatosCalificacion(Inscripcion inscripcion) {
        BigDecimal calificacion = inscripcion.getCalificacion() != null
                ? inscripcion.getCalificacion().getCalificacion()
                : null;

        return new DatosCalificacion(
                inscripcion.getGrupo().getCurso().getNombre(),
                inscripcion.getGrupo().getPeriodo(),
                calificacion
        );
    }

}