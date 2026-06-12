package com.miguel.escuela.mappers;

import com.miguel.escuela.dto.datos.DatosAlumno;
import com.miguel.escuela.dto.datos.DatosGrupo;
import com.miguel.escuela.dto.inscripciones.InscripcionRequest;
import com.miguel.escuela.dto.inscripciones.InscripcionResponse;
import com.miguel.escuela.entities.Inscripcion;
import com.miguel.escuela.utils.StringCustomUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@AllArgsConstructor
public class InscripcionMapper implements CommonMapper<InscripcionRequest, InscripcionResponse, Inscripcion> {

    @Override
    public Inscripcion requestAEntidad(InscripcionRequest request) {
        if (request == null) return null;
        return Inscripcion.builder().build();
    }

    @Override
    public InscripcionResponse entidadAResponse(Inscripcion entidad) {
        if (entidad == null) return null;

        BigDecimal calificacion = entidad.getCalificacion() != null
                ? entidad.getCalificacion().getCalificacion()
                : null;

        return new InscripcionResponse(
                entidad.getId(),
                alumnoDatos(entidad),
                grupoDatos(entidad),
                calificacion,
                StringCustomUtils.localDateAString(entidad.getFechaInscripcion())
        );
    }

    private DatosAlumno alumnoDatos(Inscripcion entidad) {
        return new DatosAlumno(
                String.join(" ",
                        entidad.getAlumno().getNombre(),
                        entidad.getAlumno().getApellidoPaterno(),
                        entidad.getAlumno().getApellidoMaterno()),
                entidad.getAlumno().getMatricula(),
                entidad.getAlumno().getEmail(),
                StringCustomUtils.localDateAString(entidad.getAlumno().getFechaIngreso())
        );
    }

    private DatosGrupo grupoDatos(Inscripcion entidad) {
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