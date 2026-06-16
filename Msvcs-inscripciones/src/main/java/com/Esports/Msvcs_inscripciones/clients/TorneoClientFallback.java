package com.Esports.Msvcs_inscripciones.clients;

import com.Esports.Msvcs_inscripciones.models.dtos.TorneoResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class TorneoClientFallback implements TorneoClient{

    @Override
    public Boolean existeTorneo(Long id) {
        return false;
    }

    @Override
    public TorneoResponseDTO buscarTorneo(Long id) {

        TorneoResponseDTO dto = new TorneoResponseDTO();

        dto.setTorneoId(0L);
        dto.setCupoMaximo(0);
        dto.setEstadoTorneo("SERVICIO_NO_DISPONIBLE");

        return dto;
    }
}
