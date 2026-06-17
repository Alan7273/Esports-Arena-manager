package com.Esports.Msvcs_inscripciones.models.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "ID del torneo al que se inscribe", example = "2")
    @NotNull(message = "El torneo es obligatorio")
    private Long torneoId;

    @Schema(description = "ID del equipo (si tipoParticipante es EQUIPO)", example = "10")
    private Long equipoId;

    @Schema(description = "ID del usuario (si tipoParticipante es INDIVIDUAL)", example = "7")
    private Long usuarioId;

    @Schema(description = "Tipo de participante", example = "EQUIPO", allowableValues = {"EQUIPO", "INDIVIDUAL"})
    @NotBlank(message = "El tipo de participante no puede estar vacío")
    private String tipoParticipante;

    @Schema(description = "Nombre del jugador o equipo participante", example = "Team Phantom")
    @NotBlank(message = "El nombre del jugador no puede estar vacío")
    private String NombreJugador;

    @Schema(description = "Estado de la inscripción", example = "PENDIENTE", allowableValues = {"PENDIENTE", "ACEPTADO", "RECHAZADO", "CANCELADO"})
    @NotBlank(message = "El estado no puede estar vacío")
    private String estado;

    @Schema(description = "Fecha de inscripción", example = "2025-06-10")
    @NotNull(message = "La fecha de inscripción es obligatoria")
    private LocalDate fechaInscripcion;
}
