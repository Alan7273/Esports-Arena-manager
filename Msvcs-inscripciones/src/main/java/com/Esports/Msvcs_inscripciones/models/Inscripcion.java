package com.Esports.Msvcs_inscripciones.models;

import jakarta.annotation.Nullable;
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

import java.time.LocalDate;

@Entity
@Table(name = "inscripciones")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inscripcion_id")
    private Long inscripcionId;

    @NotNull(message = "El torneo no puede ser nulo")
    @Column(name = "torneo_id", nullable = false)
    private Long torneoId;

    @Nullable
    @Column(name = "equipo_id")
    private Long equipoId;

    @Nullable
    @Column(name = "usuario_id")
    private Long usuarioId;

    @NotBlank(message = "El campo (jugador) no puede estar vacio ")
    @Column(name = "Nombre_jugador",nullable = false)
    private String NombreJugador;

    @NotBlank(message = "El tipo de participante no puede ser vacio")
    @Column(name = "tipo_participante", nullable = false)
    private String tipoParticipante;

    @NotBlank(message = "El estado no puede ser vacio")
    @Column(name = "estado", nullable = false)
    private String estado;

    @NotNull(message = "La fecha de inscripcion no puede ser nula")
    @Column(name = "fecha_inscripcion", nullable = false)
    private LocalDate fechaInscripcion;

    @Embedded
    private Audit audit = new Audit();
}
