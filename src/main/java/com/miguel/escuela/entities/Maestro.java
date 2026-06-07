package com.miguel.escuela.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "MAESTROS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter

public class Maestro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MAESTRO")
    private Long id;

    @Column(name = "NOMBRE",length = 50, nullable = false)
    private  String nombre;

    @Column(name = "APELLIDO_PATERNO", length = 50, nullable = false)
    private String apellidoPaterno;

    @Column(name = "APELLIDO_MATERNO", length = 50, nullable = false)
    private String apellidoMaterno;

    @Column (name = "EMAIL", length = 100, unique = true, nullable = false)
    private String email;

    @Column(name = "TELEFONO", length = 10, unique = true, nullable = false)
    private String telefono;
}
