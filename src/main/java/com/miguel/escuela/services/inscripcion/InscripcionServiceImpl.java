package com.miguel.escuela.services.inscripcion;

import com.miguel.escuela.dto.inscripciones.InscripcionRequest;
import com.miguel.escuela.dto.inscripciones.InscripcionResponse;
import com.miguel.escuela.entities.Alumno;
import com.miguel.escuela.entities.Grupo;
import com.miguel.escuela.entities.Inscripcion;
import com.miguel.escuela.exceptions.EntidadRelacionadaException;
import com.miguel.escuela.mappers.InscripcionMapper;
import com.miguel.escuela.repositories.AlumnoRepository;
import com.miguel.escuela.repositories.CalificacionRepository;
import com.miguel.escuela.repositories.GrupoRepository;
import com.miguel.escuela.repositories.InscripcionRepository;
import com.miguel.escuela.utils.ServiceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class InscripcionServiceImpl implements InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final AlumnoRepository alumnoRepository;
    private final GrupoRepository grupoRepository;
    private final CalificacionRepository calificacionRepository;
    private final InscripcionMapper inscripcionMapper;

    @Override
    @Transactional(readOnly = true)
    public List<InscripcionResponse> listar() {
        log.info("Consultando inscripciones");
        return inscripcionRepository.findAll().stream()
                .map(inscripcionMapper::entidadAResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public InscripcionResponse obtenerPorId(Long id) {
        return inscripcionMapper.entidadAResponse(obtenerInscripcion(id));
    }

    @Override
    public InscripcionResponse registrar(InscripcionRequest request) {
        log.info("Creando inscripcion nueva");

        Alumno alumno = ServiceUtils.obtenerEntidadOException(alumnoRepository, request.idAlumno(), Alumno.class);
        Grupo grupo = ServiceUtils.obtenerEntidadOException(grupoRepository, request.idGrupo(), Grupo.class);

        if (inscripcionRepository.existsByAlumnoIdAndGrupoId(request.idAlumno(), request.idGrupo()))
            throw new IllegalArgumentException("Este alumno ya esta inscrito en ese grupo");

        Inscripcion inscripcion = inscripcionMapper.requestAEntidad(request);
        inscripcion.setAlumno(alumno);
        inscripcion.setGrupo(grupo);
        inscripcion.setFechaInscripcion(LocalDate.now());

        inscripcionRepository.save(inscripcion);
        log.info("Inscripcion creada con id {}", inscripcion.getId());
        return inscripcionMapper.entidadAResponse(inscripcion);
    }

    @Override
    public InscripcionResponse actualizar(InscripcionRequest request, Long id) {
        Inscripcion inscripcion = obtenerInscripcion(id);

        log.info("Actualizando inscripcion {}", id);

        Alumno alumno = ServiceUtils.obtenerEntidadOException(alumnoRepository, request.idAlumno(), Alumno.class);
        Grupo grupo = ServiceUtils.obtenerEntidadOException(grupoRepository, request.idGrupo(), Grupo.class);

        if (inscripcionRepository.existsByAlumnoIdAndGrupoIdAndIdNot(request.idAlumno(), request.idGrupo(), id))
            throw new IllegalArgumentException("Este alumno ya esta inscrito en ese grupo");

        inscripcion.actualizar(alumno, grupo);

        inscripcionRepository.save(inscripcion);
        return inscripcionMapper.entidadAResponse(inscripcion);
    }

    @Override
    public void eliminar(Long id) {
        Inscripcion inscripcion = obtenerInscripcion(id);

        log.info("Eliminando inscripcion {}", id);

        if (calificacionRepository.existsByInscripcionId(id))
            throw new EntidadRelacionadaException("Esta inscripcion ya tiene calificacion, no se puede eliminar");

        inscripcionRepository.delete(inscripcion);
        log.info("Inscripcion {} eliminada", id);
    }

    private Inscripcion obtenerInscripcion(Long id) {
        return ServiceUtils.obtenerEntidadOException(inscripcionRepository, id, Inscripcion.class);
    }
}