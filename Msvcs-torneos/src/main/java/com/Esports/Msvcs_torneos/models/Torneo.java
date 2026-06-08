package com.Esports.Msvcs_torneos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "Torneo")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Torneo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "torneo_id")
    private Long torneoId;

    @NotBlank(message = "El campo de nombre torneo no puede ser vacio")
    @Column(nullable = false)
    private String nombretorneo;

    @NotNull(message = "El campo juego id no puede ser vacio")
    @Column(name = "juego_id", nullable = false)
    private Long juegoId;

    @NotNull(message = "El campo de fecha de inicio no puede ser vacio")
    @Column(name = "fecha_Inicio",nullable = false)
    private LocalDate fechaInicio;

    @NotNull(message = "El campo de fecha de fin no puede ser vacio")
    @Column(name = "fecha_Fin",nullable = false)
    private LocalDate fechaFin;

    @Positive(message = "El campo de cupoMaximo no puede ser vacio")
    @Column(name = "cupa_Maximo",nullable = false)
    private Integer cupoMaximo;

    @NotBlank(message = "El campo de modalidadTorneo no puede ser vacio")
    @Column(name = "modalidad_Torneo",nullable = false)
    private String modalidadTorneo;

    @NotBlank(message = "El campo de estadoTorneo no puede ser vacio")
    @Column(name = "estado_Torneo",nullable = false)
    private String estadoTorneo;

    @Embedded
    Audit audit =  new Audit();
}
