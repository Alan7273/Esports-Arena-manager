package com.Esports.Msvcs_inscripciones.models.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InscripcionResponseDTO {

    @Schema(description = "ID único de la inscripción", example = "15")
    private Long inscripcionId;

    @Schema(description = "ID del torneo", example = "2")
    private Long torneoId;

    @Schema(description = "ID del equipo (null si es individual)", example = "10")
    private Long equipoId;

    @Schema(description = "ID del usuario (null si es equipo)", example = "7")
    private Long usuarioId;

    @Schema(description = "Nombre del jugador o equipo", example = "Team Phantom")
    private String NombreJugador;

    @Schema(description = "Tipo de participante", example = "EQUIPO")
    private String tipoParticipante;

    @Schema(description = "Estado de la inscripción", example = "PENDIENTE")
    private String estado;

    @Schema(description = "Fecha de inscripción", example = "2025-06-10")
    private LocalDate fechaInscripcion;
}
