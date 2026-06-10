package com.Esports.Msvcs_inscripciones.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CrearInscripcionDTO {

    @NotNull(message = "El torneo es obligatorio")
    private Long torneoId;

    private Long equipoId;

    private Long usuarioId;

    @NotBlank(message = "El tipo de participante no puede estar vacío")
    private String tipoParticipante;

    @NotBlank(message = "El nombre del jugador no puede estar vacío")
    private String NombreJugador;

    @NotBlank(message = "El estado no puede estar vacío")
    private String estado;

    @NotNull(message = "La fecha de inscripción es obligatoria")
    private LocalDate fechaInscripcion;
}
