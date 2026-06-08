package com.Esports.Msvcs_partidas.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartidaResponseDTO {
    private Long partidaId;
    private Long torneoId;
    private Long participanteAId;
    private Long participanteBId;
    private Integer ronda;
    private LocalDateTime fechaHora;
    private String estadopartida;
}
