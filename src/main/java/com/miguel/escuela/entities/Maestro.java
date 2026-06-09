package com.miguel.escuela.entities;

import com.miguel.escuela.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @Builder.Default
    @OneToMany(mappedBy = "maestro")
    private List<Grupo> grupos = new ArrayList<>();

    public void actualizar(String nombre, String apellidoPaterno, String apellidoMaterno
    ,String email, String telefono){

        validarDatos(nombre, apellidoPaterno, apellidoMaterno,email,telefono);

        this.nombre = nombre.trim();
        this.apellidoPaterno = apellidoPaterno.trim();
        this.apellidoMaterno = apellidoMaterno.trim();
        this.email = email.toLowerCase().trim();
        this.telefono = telefono.trim();
    }
    private void validarDatos(
            String nombre, String apellidoPaterno,
            String apellidoMaterno, String email, String telefono) {

        StringCustomUtils.validarTamanio(nombre.trim(), 4, 50,
                "El nombre es requerido y debe tener entre 4 y 50 caracteres");

        StringCustomUtils.validarTamanio(apellidoPaterno.trim(), 4, 50,
                "El apellido paterno es requerido y debe tener entre 4 y 50 caracteres");

        StringCustomUtils.validarTamanio(apellidoMaterno.trim(), 4, 50,
                "El apellido materno es requerido y debe tener entre 4 y 50 caracteres");

        StringCustomUtils.validarTamanio(email.trim(), 8, 100,
                "El email es requerido y debe tener entre 8 y 100 caracteres");

        StringCustomUtils.validarTamanio(telefono.trim(), 10, 10,
                "El telefono es requerido y debe tener exactamente 10 caracteres");
    }
}
