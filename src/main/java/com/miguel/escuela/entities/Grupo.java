package com.miguel.escuela.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity // anotacion que conecta la clase con la tabla a la bd
@Table(name = "GRUPOS", // nombre exacto de la tabla en Oracle
uniqueConstraints = @UniqueConstraint( // aqui hay un unique compuesto
        name = "GRUPO_CU_MA_AU_PE_UK", //nombre exacto del constraint de la bd
        columnNames = {"ID_CURSO", "ID_MAESTRO", "ID_AULA", "PERIODO"} // columnas que conforman el unique
))
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_GRUPO")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CURSO", nullable = false)
    private Curso curso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_MAESTRO", nullable = false)
    private Maestro maestro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_AULA", nullable = false)
    private Aula aula;

    @Column(name = "PERIODO",length = 20, nullable = false)
    private String periodo;

    @Builder.Default
    @OneToMany(mappedBy = "grupo")
    private List<Horario> horarios = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "grupo")
    private List<Inscripcion> inscripciones = new ArrayList<>();

    public void actualizar(Curso curso, Maestro maestro, Aula aula, String periodo) {
        this.curso = curso;
        this.maestro = maestro;
        this.aula = aula;
        this.periodo = periodo.trim();
    }
}
