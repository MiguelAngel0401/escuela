package com.miguel.escuela.services.horario;

import com.miguel.escuela.dto.horarios.HorarioRequest;
import com.miguel.escuela.dto.horarios.HorarioResponse;
import com.miguel.escuela.entities.Grupo;
import com.miguel.escuela.entities.Horario;
import com.miguel.escuela.enums.DiaSemana;
import com.miguel.escuela.mappers.HorarioMapper;
import com.miguel.escuela.repositories.GrupoRepository;
import com.miguel.escuela.repositories.HorarioRepository;
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
public class HorarioServiceImpl implements HorarioService {

    private final HorarioRepository horarioRepository;
    private final GrupoRepository grupoRepository;
    private final HorarioMapper horarioMapper;

    @Override
    @Transactional(readOnly = true)
    public List<HorarioResponse> listar() {
        log.info("Consultando horarios");
        return horarioRepository.findAll().stream()
                .map(horarioMapper::entidadAResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HorarioResponse obtenerPorId(Long id) {
        return horarioMapper.entidadAResponse(obtenerHorario(id));
    }

    @Override
    public HorarioResponse registrar(HorarioRequest request) {
        log.info("Creando horario nuevo");

        Grupo grupo = ServiceUtils.obtenerEntidadOException(grupoRepository, request.idGrupo(), Grupo.class);
        DiaSemana dia = DiaSemana.obtenerCategoriaPorDescripcion(request.dia());

        validarRangoHorario(request.horaInicio(), request.horaFin());
        validarTraslapes(request.idGrupo(), grupo.getAula().getId(), dia, request.horaInicio(), request.horaFin(), null);

        Horario horario = horarioMapper.requestAEntidad(request);
        horario.setGrupo(grupo);
        horario.setDia(dia);

        horarioRepository.save(horario);
        log.info("Horario creado con id {}", horario.getId());
        return horarioMapper.entidadAResponse(horario);
    }

    @Override
    public HorarioResponse actualizar(HorarioRequest request, Long id) {
        Horario horario = obtenerHorario(id);

        log.info("Actualizando horario {}", id);

        Grupo grupo = ServiceUtils.obtenerEntidadOException(grupoRepository, request.idGrupo(), Grupo.class);
        DiaSemana dia = DiaSemana.obtenerCategoriaPorDescripcion(request.dia());

        validarRangoHorario(request.horaInicio(), request.horaFin());
        validarTraslapes(request.idGrupo(), grupo.getAula().getId(), dia, request.horaInicio(), request.horaFin(), id);

        horario.actualizar(grupo, dia, request.horaInicio().trim(), request.horaFin().trim());

        horarioRepository.save(horario);
        return horarioMapper.entidadAResponse(horario);
    }

    @Override
    public void eliminar(Long id) {
        Horario horario = obtenerHorario(id);

        log.info("Eliminando horario {}", id);

        horarioRepository.delete(horario);
        log.info("Horario {} eliminado", id);
    }

    private Horario obtenerHorario(Long id) {
        return ServiceUtils.obtenerEntidadOException(horarioRepository, id, Horario.class);
    }

    private void validarRangoHorario(String horaInicio, String horaFin) {
        if (horaFin.compareTo(horaInicio) <= 0)
            throw new IllegalArgumentException("La hora final debe ser mayor a la hora de inicio");
    }

    private void validarTraslapes(Long idGrupo, Long idAula, DiaSemana dia, String horaInicio, String horaFin, Long idExcluir) {
        if (horarioRepository.existeConflictoEnGrupo(idGrupo, dia.name(), horaInicio, horaFin, idExcluir) > 0)
            throw new IllegalArgumentException("Ese grupo ya tiene un horario que se cruza con este");

        if (horarioRepository.existeConflictoEnAula(idAula, dia.name(), horaInicio, horaFin, idExcluir) > 0)
            throw new IllegalArgumentException("Esa aula ya tiene un horario que se cruza con este");
    }
}