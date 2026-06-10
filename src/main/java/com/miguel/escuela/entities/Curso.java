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
        StringCustomUtils.validarTamanio(nombre.trim(), 5, 100,
                "El nombre es requerido y debe tener entre 5 y 100 caracteres");

        if (descripcion != null)
            StringCustomUtils.validarTamanio(descripcion.trim(), 1, 200,
                    "La descripcion debe tener maximo 200 caracteres");

        if (creditos == null || creditos <= 0)
            throw new IllegalArgumentException("Los créditos deben ser mayores a cero");

    }
}
