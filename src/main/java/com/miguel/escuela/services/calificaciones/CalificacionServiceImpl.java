package com.miguel.escuela.services.calificaciones;

import com.miguel.escuela.dto.calificaciones.CalificacionRequest;
import com.miguel.escuela.dto.calificaciones.CalificacionResponse;
import com.miguel.escuela.entities.Calificacion;
import com.miguel.escuela.entities.Inscripcion;
import com.miguel.escuela.mappers.CalificacionMapper;
import com.miguel.escuela.repositories.CalificacionRepository;
import com.miguel.escuela.repositories.InscripcionRepository;
import com.miguel.escuela.utils.ServiceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class CalificacionServiceImpl implements CalificacionService {

    private final CalificacionRepository calificacionRepository;
    private final InscripcionRepository inscripcionRepository;
    private final CalificacionMapper calificacionMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CalificacionResponse> listar() {
        log.info("Consultando calificaciones");
        return calificacionRepository.findAll().stream()
                .map(calificacionMapper::entidadAResponse).toList();
    }

    @Override
    public CalificacionResponse obtenerPorId(Long id) {
        return calificacionMapper.entidadAResponse(obtenerCalificacion(id));
    }

    @Override
    public CalificacionResponse registrar(CalificacionRequest request) {
        log.info("Registrando calificacion nueva");

        Inscripcion inscripcion = ServiceUtils.obtenerEntidadOException(
                inscripcionRepository, request.idInscripcion(), Inscripcion.class);

        if (calificacionRepository.existsByInscripcionId(request.idInscripcion()))
            throw new IllegalArgumentException("Esa inscripcion ya tiene una calificacion");

        Calificacion calificacion = calificacionMapper.requestAEntidad(request);
        calificacion.asignarInscripcion(inscripcion);

        calificacionRepository.save(calificacion);
        log.info("Calificacion creada con id {}", calificacion.getId());
        return calificacionMapper.entidadAResponse(calificacion);
    }

    @Override
    public CalificacionResponse actualizar(CalificacionRequest request, Long id) {
        Calificacion calificacion = obtenerCalificacion(id);
        log.info("Actualizando calificacion {}", id);

        Inscripcion inscripcion = ServiceUtils.obtenerEntidadOException(
                inscripcionRepository, request.idInscripcion(), Inscripcion.class);

        boolean cambioDeInscripcion = !calificacion.getInscripcion().getId().equals(request.idInscripcion());

        if (cambioDeInscripcion && calificacionRepository.existsByInscripcionId(request.idInscripcion()))
            throw new IllegalArgumentException("Esa inscripcion ya tiene una calificacion");

        calificacion.actualizar(request.calificacion(), inscripcion);

        calificacionRepository.save(calificacion);
        return calificacionMapper.entidadAResponse(calificacion);
    }

    @Override
    public void eliminar(Long id) {
        Calificacion calificacion = obtenerCalificacion(id);
        log.info("Eliminando calificacion {}", id);
        calificacionRepository.delete(calificacion);
        log.info("Calificacion {} eliminada", id);
    }

    private Calificacion obtenerCalificacion(Long id) {
        return ServiceUtils.obtenerEntidadOException(calificacionRepository, id, Calificacion.class);
    }
}