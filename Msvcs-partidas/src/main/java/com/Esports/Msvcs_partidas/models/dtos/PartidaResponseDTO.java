package com.Esports.Msvcs_partidas.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartidaResponseDTO {

    @Schema(description = "ID único de la partida", example = "20")
    private Long partidaId;

    @Schema(description = "ID del torneo", example = "2")
    private Long torneoId;

    @Schema(description = "ID del participante A", example = "10")
    private Long participanteAId;

    @Schema(description = "ID del participante B", example = "11")
    private Long participanteBId;

    @Schema(description = "Número de ronda", example = "1")
    private Integer ronda;

    @Schema(description = "Fecha y hora de la partida", example = "2025-07-05T15:00:00")
    private LocalDateTime fechaHora;

    @Schema(description = "Estado de la partida", example = "PROGRAMADA")
    private String estadopartida;
}
