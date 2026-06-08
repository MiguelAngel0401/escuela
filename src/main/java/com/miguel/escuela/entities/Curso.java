package com.miguel.escuela.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CURSOS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter

public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CURSO")
    private Long id;

    @Column (name = "NOMBRE", length = 100, unique = true, nullable = false)
    private String nombre;

    @Column (name = "DESCRIPCION", length = 200)
    private String descripcion;

    @Column(name = "CREDITOS", length = 2, nullable = false)
    private Integer creditos;

    @Builder.Default
    @OneToMany(mappedBy = "curso")
    private List<Grupo> grupos = new ArrayList<>();

}
