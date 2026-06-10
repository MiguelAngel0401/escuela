package com.miguel.escuela.entities;

import com.miguel.escuela.utils.StringCustomUtils;
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

    public void actualizar(String nombre, String descripcion, Integer creditos){

        validarDatos(nombre, descripcion, creditos);

        this.nombre = nombre.trim();
        this.descripcion = descripcion;
        this.creditos = creditos;
    }
    private void validarDatos(String nombre, String descripcion, Integer creditos) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía");
        }
        if (creditos == null || creditos <= 0) {
            throw new IllegalArgumentException("Los créditos deben ser mayores a cero");
        }
    }
}
