package com.miguel.escuela.mappers;

import com.miguel.escuela.dto.calificaciones.CalificacionRequest;
import com.miguel.escuela.dto.calificaciones.CalificacionResponse;
import com.miguel.escuela.dto.datos.DatosAlumno;
import com.miguel.escuela.dto.datos.DatosGrupo;
import com.miguel.escuela.dto.datos.DatosInscripcion;
import com.miguel.escuela.entities.Calificacion;
import com.miguel.escuela.entities.Inscripcion;
import com.miguel.escuela.utils.StringCustomUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@AllArgsConstructor
public class CalificacionMapper implements CommonMapper<CalificacionRequest, CalificacionResponse, Calificacion> {
    @Override
    public Calificacion requestAEntidad(CalificacionRequest request) {
        if(request == null) return null;

        return Calificacion.builder()
                .calificacion(request.calificacion())
                .fechaRegistro(LocalDate.now())
                .build();
    }

    @Override
    public CalificacionResponse entidadAResponse(Calificacion entidad) {
        if(entidad == null) return null;

        return new CalificacionResponse(
                entidad.getId(),
                incripcionADatos(entidad.getInscripcion()),
                entidad.getCalificacion(),
                StringCustomUtils.localDateAString(entidad.getFechaRegistro())
        );
    }

    private DatosInscripcion incripcionADatos(Inscripcion inscripcion){
        DatosAlumno alumno = new DatosAlumno(
                String.join(" ",
                        inscripcion.getAlumno().getNombre(),
                        inscripcion.getAlumno().getApellidoPaterno(),
                        inscripcion.getAlumno().getApellidoMaterno()),
                inscripcion.getAlumno().getMatricula(),
                inscripcion.getAlumno().getEmail(),
                StringCustomUtils.localDateAString(inscripcion.getAlumno().getFechaIngreso())
        );

        DatosGrupo grupo = new DatosGrupo(
                inscripcion.getGrupo().getCurso().getNombre(),
                String.join(" ",
                        inscripcion.getGrupo().getMaestro().getNombre(),
                        inscripcion.getGrupo().getMaestro().getApellidoPaterno(),
                        inscripcion.getGrupo().getMaestro().getApellidoMaterno()),
                inscripcion.getGrupo().getAula().getNombre(),
                inscripcion.getGrupo().getPeriodo()
        );

        return new DatosInscripcion(
                alumno,
                grupo,
                StringCustomUtils.localDateAString(inscripcion.getFechaInscripcion())
        );
    }
}
