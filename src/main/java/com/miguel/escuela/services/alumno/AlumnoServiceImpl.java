package com.miguel.escuela.services.alumno;

import com.miguel.escuela.dto.alumnos.AlumnoRequest;
import com.miguel.escuela.dto.alumnos.AlumnoResponse;
import com.miguel.escuela.entities.Alumno;
import com.miguel.escuela.mappers.AlumnoMapper;
import com.miguel.escuela.repositories.AlumnoRepository;
import com.miguel.escuela.repositories.InscripcionRepository;
import com.miguel.escuela.exceptions.EntidadRelacionadaException;

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
public class AlumnoServiceImpl implements AlumnoService {

    private final AlumnoRepository alumnoRepository;
    private final InscripcionRepository inscripcionRepository;
    private final AlumnoMapper alumnoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<AlumnoResponse> listar() {
        log.info("Listado de todos los alumnos solicitados");
        return alumnoRepository.findAll().stream()
                .map(alumnoMapper::entidadAResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AlumnoResponse obtenerPorId(Long id) {
        return alumnoMapper.entidadAResponse(obtenerAlumno(id));
    }

    @Override
    public AlumnoResponse registrar(AlumnoRequest request) {
        log.info("Registrando nuevo alumno ...");

        String email = alumnoRepository.generarCorreo(
                request.nombre(),
                request.apellidoPaterno(),
                request.apellidoMaterno());

        if (alumnoRepository.existsByEmail(email))
            throw new IllegalArgumentException("Ya existe un alumno con ese email: " + email);

        String matricula = alumnoRepository.generarMatricula(
                request.nombre(),
                request.apellidoPaterno(),
                request.apellidoMaterno());

        Alumno alumno = alumnoMapper.requestAEntidad(request);
        alumno.setEmail(email);
        alumno.setMatricula(matricula);
        alumno.setFechaIngreso(LocalDate.now());

        alumnoRepository.save(alumno);
        log.info("Alumno {} registrado", alumno.getNombre());
        return alumnoMapper.entidadAResponse(alumno);
    }

    @Override
    public AlumnoResponse actualizar(AlumnoRequest request, Long id) {
        Alumno alumno = obtenerAlumno(id);

        log.info("Actualizando alumno con id: {}", id);

        String nuevoEmail = alumnoRepository.generarCorreo(
                request.nombre(),
                request.apellidoPaterno(),
                request.apellidoMaterno());

        if (alumnoRepository.existsByEmailAndIdNot(nuevoEmail, id))
            throw new IllegalArgumentException("El email generado ya está en uso: " + nuevoEmail);

        String nuevaMatricula = alumnoRepository.generarMatricula(
                request.nombre(),
                request.apellidoPaterno(),
                request.apellidoMaterno());

        alumno.actualizar(
                request.nombre(),
                request.apellidoPaterno(),
                request.apellidoMaterno(),
                nuevoEmail,
                nuevaMatricula);

        alumnoRepository.save(alumno);
        return alumnoMapper.entidadAResponse(alumno);
    }

    @Override
    public void eliminar(Long id) {
        Alumno alumno = obtenerAlumno(id);

        log.info("Eliminando alumno con id: {}", id);

        if (inscripcionRepository.existsByAlumnoId(id))
            throw new EntidadRelacionadaException("No se puede eliminar al alumno porque tiene inscripciones asociadas");

        alumnoRepository.delete(alumno);
        log.info("Alumno con id {} eliminado", id);
    }

    private Alumno obtenerAlumno(Long id) {
        return ServiceUtils.obtenerEntidadOException(alumnoRepository, id, Alumno.class);
    }
}