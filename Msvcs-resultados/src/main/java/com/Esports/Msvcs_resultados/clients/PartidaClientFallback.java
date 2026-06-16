package com.Esports.Msvcs_resultados.clients;

import com.Esports.Msvcs_resultados.models.dtos.PartidaResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class PartidaClientFallback implements PartidaClient {

    @Override
    public PartidaResponseDTO buscarPartida(Long id) {

        PartidaResponseDTO dto = new PartidaResponseDTO();

        dto.setPartidaId(0L);
        dto.setParticipanteAId(0L);
        dto.setParticipanteBId(0L);
        dto.setEstadopartida("SERVICIO_NO_DISPONIBLE");

        return dto;
    }
}
