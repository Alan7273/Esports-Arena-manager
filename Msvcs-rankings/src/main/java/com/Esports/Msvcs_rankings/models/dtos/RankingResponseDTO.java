package com.Esports.Msvcs_rankings.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RankingResponseDTO {

    @Schema(description = "ID único del ranking", example = "4")
    private Long rankingId;

    @Schema(description = "ID del torneo", example = "2")
    private Long torneoId;

    @Schema(description = "ID del participante", example = "10")
    private Long participanteId;

    @Schema(description = "Puntos acumulados", example = "9")
    private Integer puntos;

    @Schema(description = "Número de victorias", example = "3")
    private Integer victorias;

    @Schema(description = "Número de derrotas", example = "1")
    private Integer derrotas;

    @Schema(description = "Diferencia de puntaje (puntajeA - puntajeB)", example = "5")
    private Integer diferencia;

    @Schema(description = "Posición en el ranking del torneo", example = "1")
    private Integer posicion;
}
