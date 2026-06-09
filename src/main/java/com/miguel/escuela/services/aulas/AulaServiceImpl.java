package com.miguel.escuela.services.aulas;

import com.miguel.escuela.dto.aulas.AulaRequest;
import com.miguel.escuela.dto.aulas.AulaResponse;
import com.miguel.escuela.entities.Aula;
import com.miguel.escuela.exceptions.EntidadRelacionadaException;
import com.miguel.escuela.mappers.AulaMapper;
import com.miguel.escuela.repositories.AulaRepository;
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
public class AulaServiceImpl implements AulaService {

    private final AulaRepository aulaRepository;
    private final GrupoRepository grupoRepository;
    private final AulaMapper aulaMapper;

    @Override
    @Transactional(readOnly = true)
    public List<AulaResponse> listar() {
        log.info("Listado de todas las aulas solicitadas");
        return aulaRepository.findAll().stream()
                .map(aulaMapper::entidadAResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AulaResponse obtenerPorId(Long id) {
        return aulaMapper.entidadAResponse(obtenerAula(id));
    }

    @Override
    public AulaResponse registrar(AulaRequest request) {
        log.info("Registrando nueva aula...");

        validarNombreUnico(request);

        Aula aula = aulaMapper.requestAEntidad(request);
        aulaRepository.save(aula);

        log.info("Nueva aula {} registrada", aula.getNombre());
        return aulaMapper.entidadAResponse(aula);
    }

    @Override
    public AulaResponse actualizar(AulaRequest request, Long id) {
        Aula aula = obtenerAula(id);

        log.info("Actualizando aula con id: {}", id);

        validarCambiosEnNombreUnico(request, id);

        aula.actualizar(request.nombre(), request.capacidad());

        aulaRepository.save(aula);
        return aulaMapper.entidadAResponse(aula);
    }

    @Override
    public void eliminar(Long id) {
        Aula aula = obtenerAula(id);

        log.info("Eliminando aula con id: {}", id);

        if (grupoRepository.existsByAulaId(id))
            throw new EntidadRelacionadaException("No se puede eliminar el aula que ya tiene grupos asignados");

        aulaRepository.delete(aula);
        log.info("Aula con id {} eliminada", id);
    }

    private Aula obtenerAula(Long id) {
        return ServiceUtils.obtenerEntidadOException(aulaRepository, id, Aula.class);
    }

    private void validarNombreUnico(AulaRequest request) {
        log.info("Validando nombre unico...");
        if (aulaRepository.existsByNombre(request.nombre().trim()))
            throw new IllegalArgumentException("Ya existe un aula registrada con el nombre: " + request.nombre());
    }

    private void validarCambiosEnNombreUnico(AulaRequest request, Long id) {
        log.info("Validando nombre unico...");
        if (aulaRepository.existsByNombreAndIdNot(request.nombre().trim(), id))
            throw new IllegalArgumentException("Ya existe un aula registrada con el nombre: " + request.nombre());
    }
}