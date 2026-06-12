package com.miguel.escuela.entities;

import com.miguel.escuela.dto.datos.DatosCalificacion;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ALUMNOS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter

public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ALUMNO")
    private Long id;

    @Column(name = "NOMBRE", length = 50, nullable = false)
    private String nombre;

    @Column(name = "APELLIDO_PATERNO", length = 50, nullable = false)
    private String apellidoPaterno;

    @Column(name = "APELLIDO_MATERNO", length = 50, nullable = false)
    private String apellidoMaterno;

    @Column(name = "EMAIL", length = 100, unique = true, nullable = false)
    private String email;

    @Column(name = "MATRICULA", length = 20, unique = true, nullable = false)
    private String matricula;

    @Column(name = "FECHA_INGRESO")
    private LocalDate fechaIngreso = LocalDate.now();

    @Builder.Default
    @OneToMany(mappedBy = "alumno")
    private List<Inscripcion> inscripciones = new ArrayList<>();

    public void actualizar(String nombre, String apellidoPaterno, String apellidoMaterno, String email, String matricula) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.email = email;
        this.matricula = matricula;
    }
    public BigDecimal calcularPromedio() {
        List<BigDecimal> noNulas = this.inscripciones.stream()
                .filter(i -> i.getCalificacion() != null && i.getCalificacion().getCalificacion() != null)
                .map(i -> i.getCalificacion().getCalificacion())
                .toList();

        if (noNulas.isEmpty()) return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

        BigDecimal suma = noNulas.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        return suma.divide(BigDecimal.valueOf(noNulas.size()), 2, RoundingMode.HALF_UP);
    }
}
