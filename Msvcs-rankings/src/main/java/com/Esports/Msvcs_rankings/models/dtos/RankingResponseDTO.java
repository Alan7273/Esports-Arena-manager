package com.Esports.Msvcs_rankings.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RankingResponseDTO {
    private Long rankingId;
    private Long torneoId;
    private Long participanteId;
    private Integer puntos;
    private Integer victorias;
    private Integer derrotas;
    private Integer diferencia;
    private Integer posicion;
}
