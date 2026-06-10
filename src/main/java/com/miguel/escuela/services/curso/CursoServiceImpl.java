package com.miguel.escuela.services.curso;

import com.miguel.escuela.dto.cursos.CursoRequest;
import com.miguel.escuela.dto.cursos.CursoResponse;
import com.miguel.escuela.entities.Curso;
import com.miguel.escuela.exceptions.EntidadRelacionadaException;
import com.miguel.escuela.mappers.CursoMapper;
import com.miguel.escuela.repositories.CursoRepository;
import com.miguel.escuela.repositories.GrupoRepository;
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
public class CursoServiceImpl implements CursoService {

    private final CursoRepository cursoRepository;

    private final GrupoRepository grupoRepository;

    private final CursoMapper cursoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CursoResponse> listar() {
        log.info("Listado de todos los cursos solicitados");
        return cursoRepository.findAll().stream()
                .map(cursoMapper::entidadAResponse).toList();
    }

    @Override
    @Transactional
    public CursoResponse obtenerPorId(Long id) {
        return cursoMapper.entidadAResponse(obtenerCurso(id));
    }

    @Override
    public CursoResponse registrar(CursoRequest request) {
        log.info("Registrando nuevo curso ... ");

        validarNombreUnico(request);

        Curso curso = cursoMapper.requestAEntidad(request);
        cursoRepository.save(curso);

        log.info("Curso {} registrado exitosamente", curso.getNombre());
        return cursoMapper.entidadAResponse(curso);
    }

    @Override
    public CursoResponse actualizar(CursoRequest request, Long id) {
        Curso curso = obtenerCurso(id);
        log.info("Actualizando curso con id: {}", id);

        validarCambiosEnNombreUnico(request, id);

        curso.actualizar(request.nombre(), request.descripcion(),request.creditos());

        cursoRepository.save(curso);
        return cursoMapper.entidadAResponse(curso);
    }

    @Override
    public void eliminar(Long id) {
        Curso curso = obtenerCurso(id);

        log.info("Eliminando curso con id: {}" , id);

        if (grupoRepository.existsByCursoId(id))
            throw new EntidadRelacionadaException("No se puede eliminar el curso que ya tiene grupos asignados");

        cursoRepository.delete(curso);
        log.info("Curso con id {} eliminada", id);
    }

    private Curso obtenerCurso(Long id) {
        return ServiceUtils.obtenerEntidadOException(cursoRepository, id, Curso.class);
    }

    private void validarNombreUnico(CursoRequest request) {
        if (cursoRepository.existsByNombre(request.nombre().trim()))
            throw new IllegalArgumentException("Ya existe un curso registrado con el nombre: " + request.nombre());
    }

    private void validarCambiosEnNombreUnico(CursoRequest request, Long id) {
        if (cursoRepository.existsByNombreAndIdNot(request.nombre().trim(), id))
            throw new IllegalArgumentException("Ya existe un curso registrado con el nombre: " + request.nombre());
    }
}
