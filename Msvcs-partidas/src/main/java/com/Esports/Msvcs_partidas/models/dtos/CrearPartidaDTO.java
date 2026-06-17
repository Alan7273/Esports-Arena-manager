package com.Esports.Msvcs_partidas.models.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CrearPartidaDTO {

    @Schema(description = "ID del torneo al que pertenece la partida", example = "2")
    @NotNull(message = "El torneo es obligatorio")
    private Long torneoId;

    @Schema(description = "ID del participante A (equipo o usuario)", example = "10")
    @NotNull(message = "El participante A es obligatorio")
    private Long participanteAId;

    @Schema(description = "ID del participante B (equipo o usuario)", example = "11")
    @NotNull(message = "El participante B es obligatorio")
    private Long participanteBId;

    @Schema(description = "Número de ronda", example = "1")
    @NotNull(message = "La ronda es obligatoria")
    @Min(value = 1, message = "La ronda mínima es 1")
    private Integer ronda;

    @Schema(description = "Fecha y hora de la partida", example = "2025-07-05T15:00:00")
    @NotNull(message = "La fecha y hora son obligatorias")
    private LocalDateTime fechaHora;

    @Schema(description = "Estado de la partida", example = "PROGRAMADA", allowableValues = {"PROGRAMADA", "EN_CURSO", "FINALIZADA", "CANCELADA"})
    @NotBlank(message = "El estado de la partida no puede estar vacío")
    private String estadopartida;
}
