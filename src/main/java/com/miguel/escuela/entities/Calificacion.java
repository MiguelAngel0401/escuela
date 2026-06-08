package com.miguel.escuela.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "CALIFICACIONES")

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CALIFICACION")
    private Long id;


    @Column(name = "CALIFICACION", nullable = false)
    private BigDecimal calificacion;

    @Column(name = "FECHA_REGISTRO")
    private LocalDate fechaRegistro;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_INSCRIPCION", nullable = false, unique = true)
    private Inscripcion inscripcion;
}
