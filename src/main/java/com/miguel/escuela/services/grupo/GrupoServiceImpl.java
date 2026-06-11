package com.miguel.escuela.services.grupo;

import com.miguel.escuela.dto.grupos.GruposRequest;
import com.miguel.escuela.dto.grupos.GruposResponse;
import com.miguel.escuela.entities.Aula;
import com.miguel.escuela.entities.Curso;
import com.miguel.escuela.entities.Grupo;
import com.miguel.escuela.entities.Maestro;
import com.miguel.escuela.exceptions.EntidadRelacionadaException;
import com.miguel.escuela.mappers.GrupoMapper;
import com.miguel.escuela.repositories.*;
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
public class GrupoServiceImpl implements GrupoService {

    private final GrupoRepository grupoRepository;
    private final CursoRepository cursoRepository;
    private final MaestroRepository maestroRepository;
    private final AulaRepository aulaRepository;
    private final InscripcionRepository inscripcionRepository;
    private final HorarioRepository horarioRepository;
    private final GrupoMapper grupoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<GruposResponse> listar() {
        log.info("Listado de todos los grupos solicitados");
        return grupoRepository.findAll().stream()
                .map(grupoMapper::entidadAResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public GruposResponse obtenerPorId(Long id) {
        return grupoMapper.entidadAResponse(obtenerGrupo(id));
    }

    @Override
    public GruposResponse registrar(GruposRequest request) {
        log.info("Registrando nuevo grupo ...");

        Curso curso = ServiceUtils.obtenerEntidadOException(cursoRepository, request.idCurso(), Curso.class);
        Maestro maestro = ServiceUtils.obtenerEntidadOException(maestroRepository, request.idMaestro(), Maestro.class);
        Aula aula = ServiceUtils.obtenerEntidadOException(aulaRepository, request.idAula(), Aula.class);

        validarGrupoDuplicado(request.idCurso(), request.idMaestro(), request.idAula(), request.periodo());

        Grupo grupo = grupoMapper.requestAEntidad(request);
        grupo.setCurso(curso);
        grupo.setMaestro(maestro);
        grupo.setAula(aula);

        grupoRepository.save(grupo);
        log.info("Nuevo grupo {} registrado", grupo.getId());
        return grupoMapper.entidadAResponse(grupo);
    }

    @Override
    public GruposResponse actualizar(GruposRequest request, Long id) {
        Grupo grupo = obtenerGrupo(id);

        log.info("Actualizando grupo con id: {}", id);

        Curso curso = ServiceUtils.obtenerEntidadOException(cursoRepository, request.idCurso(), Curso.class);
        Maestro maestro = ServiceUtils.obtenerEntidadOException(maestroRepository, request.idMaestro(), Maestro.class);
        Aula aula = ServiceUtils.obtenerEntidadOException(aulaRepository, request.idAula(), Aula.class);

        validarGrupoDuplicadoAlActualizar(request.idCurso(), request.idMaestro(), request.idAula(), request.periodo(), id);

        grupo.actualizar(curso, maestro, aula, request.periodo());

        grupoRepository.save(grupo);
        return grupoMapper.entidadAResponse(grupo);
    }

    @Override
    public void eliminar(Long id) {
        Grupo grupo = obtenerGrupo(id);

        log.info("Eliminando grupo con id: {}", id);

        if (inscripcionRepository.existsByGrupoId(id))
            throw new EntidadRelacionadaException("No se puede eliminar el grupo porque tiene inscripciones asociadas");

        if (horarioRepository.existsByGrupoId(id))
            throw new EntidadRelacionadaException("No se puede eliminar el grupo porque tiene horarios asociados");

        grupoRepository.delete(grupo);
        log.info("Grupo con id {} eliminado", id);
    }

    private Grupo obtenerGrupo(Long id) {
        return ServiceUtils.obtenerEntidadOException(grupoRepository, id, Grupo.class);
    }

    private void validarGrupoDuplicado(Long idCurso, Long idMaestro, Long idAula, String periodo) {
        if (grupoRepository.existsByCursoIdAndMaestroIdAndAulaIdAndPeriodo(idCurso, idMaestro, idAula, periodo.trim()))
            throw new IllegalArgumentException("Ya existe un grupo con esa combinación de curso, maestro, aula y periodo");
    }

    private void validarGrupoDuplicadoAlActualizar(Long idCurso, Long idMaestro, Long idAula, String periodo, Long id) {
        if (grupoRepository.existsByCursoIdAndMaestroIdAndAulaIdAndPeriodoAndIdNot(idCurso, idMaestro, idAula, periodo.trim(), id))
            throw new IllegalArgumentException("Ya existe un grupo con esa combinación de curso, maestro, aula y periodo");
    }
}