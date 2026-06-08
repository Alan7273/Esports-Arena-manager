package com.Esports.Msvcs_equipos.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "equipos")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Equipos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipo_id")
    private Long equipoId;

    @NotBlank(message = "El campo de nombreequipo no puede ser vacio")
    @Column(name = "nombre_equipo", nullable = false)
    private String nombreequipo;

    @NotNull(message = "El campo de fecha de registro no puede ser vacio")
    @Column(name = "capitan_id", nullable = false)
    private Long capitanId;

    @NotNull(message = "El campo de fecha de registro no puede ser vacio")
    @Column(name = "juego_principal_id", nullable = false)
    private Long juegoprincipalId;

    @NotBlank(message = "El campo de estadoequipo no puede ser vacio")
    @Column(name = "estado_equipo",nullable = false)
    private String estadoequipo;

    @Embedded
    private Audit audit = new Audit();
}
