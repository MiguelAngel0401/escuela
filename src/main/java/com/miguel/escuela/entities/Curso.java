package com.miguel.escuela.entities;

import jakarta.persistence.*;
import lombok.*;

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

}
