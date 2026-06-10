package com.Esports.Msvcs_resultados.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartidaResponseDTO {
    private Long partidaId;
    private Long participanteAId;
    private Long participanteBId;
    private String estadopartida;
}
