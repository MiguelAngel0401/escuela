package com.miguel.escuela.entities;

import com.miguel.escuela.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "AULAS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AULA")
    private Long id;

    @Column(name = "NOMBRE", length = 100, unique = true, nullable = false)
    private String nombre;

    @Column(name = "CAPACIDAD", nullable = false)
    private Integer capacidad;

    @Builder.Default
    @OneToMany(mappedBy = "aula")
    private List<Grupo> grupos = new ArrayList<>();

    public void actualizar(String nombre, Integer capacidad) {
        validarDatos(nombre, capacidad);
        this.nombre = nombre.trim();
        this.capacidad = capacidad;
    }

    private void validarDatos(String nombre, Integer capacidad) {
        StringCustomUtils.validarTamanio(nombre.trim(), 2, 50,
                "El nombre del aula es requerido y debe tener entre 2 y 50 caracteres");

        if (capacidad == null || capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser un número positivo");
        }
    }
}
