package com.Esports.Msvcs_resultados.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoResponseDTO {

    @Schema(description = "ID único del resultado", example = "8")
    private Long resultadoId;

    @Schema(description = "ID de la partida", example = "20")
    private Long partidaId;

    @Schema(description = "ID del ganador", example = "10")
    private Long ganadorId;

    @Schema(description = "Puntaje del participante A", example = "3")
    private Integer puntajeA;

    @Schema(description = "Puntaje del participante B", example = "1")
    private Integer puntajeB;

    @Schema(description = "Estado de validación", example = "PENDIENTE")
    private String estadoValidacion;

    @Schema(description = "Fecha de registro", example = "2025-07-05")
    private LocalDate fechaRegistro;
}
