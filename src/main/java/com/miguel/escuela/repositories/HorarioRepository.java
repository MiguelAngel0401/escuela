package com.miguel.escuela.repositories;

import com.miguel.escuela.entities.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HorarioRepository extends JpaRepository<Horario, Long> {

    boolean existsByGrupoId(Long grupoId);

    @Query(value = """
        SELECT COUNT(*) FROM HORARIOS
        WHERE ID_GRUPO = :idGrupo AND DIA = :dia
        AND ID_HORARIO != NVL(:idExcluir, -1)
        AND (HORA_INICIO < :horaFin AND HORA_FIN > :horaInicio)
        """, nativeQuery = true)
    int existeConflictoEnGrupo(@Param("idGrupo") Long idGrupo,
                               @Param("dia") String dia,
                               @Param("horaInicio") String horaInicio,
                               @Param("horaFin") String horaFin,
                               @Param("idExcluir") Long idExcluir);

    @Query(value = """
        SELECT COUNT(*) FROM HORARIOS H
        INNER JOIN GRUPOS G ON H.ID_GRUPO = G.ID_GRUPO
        WHERE G.ID_AULA = :idAula AND H.DIA = :dia
        AND H.ID_HORARIO != NVL(:idExcluir, -1)
        AND (H.HORA_INICIO < :horaFin AND H.HORA_FIN > :horaInicio)
        """, nativeQuery = true)
    int existeConflictoEnAula(@Param("idAula") Long idAula,
                              @Param("dia") String dia,
                              @Param("horaInicio") String horaInicio,
                              @Param("horaFin") String horaFin,
                              @Param("idExcluir") Long idExcluir);
}