package com.Esports.Msvcs_equipos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "Miembro_equipo")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Miembro_equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Miembro_Id")
    private Long miembroId;

    @NotNull(message = "El campo de fecha de registro no puede ser vacio")
    @Column(name = "equipo_id", nullable = false)
    private Long equipoId;

    @NotNull(message = "El campo de fecha de registro no puede ser vacio")
    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @NotNull(message = "El campo de fecha de registro no puede ser vacio")
    @Column(name = "rol_dentro_equipo", nullable = false)
    private String rolDentroEquipo;

    @NotNull(message = "el campo de fecha de ingreso no puede ser vacio")
    @Column(nullable = false)
    private LocalDateTime fechaIngreso;

    @Embedded
    private Audit audit = new Audit();
}
