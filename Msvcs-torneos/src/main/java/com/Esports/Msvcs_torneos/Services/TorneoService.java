package com.Esports.Msvcs_torneos.Services;


import com.Esports.Msvcs_torneos.models.dtos.ActualizarTorneoDTO;
import com.Esports.Msvcs_torneos.models.dtos.CrearTorneoDTO;
import com.Esports.Msvcs_torneos.models.dtos.TorneoResponseDTO;

import java.util.List;

public interface TorneoService {
    TorneoResponseDTO crearTorneo(CrearTorneoDTO dto);
    List<TorneoResponseDTO> listarTorneos();
    TorneoResponseDTO buscarTorneo(Long id);
    TorneoResponseDTO actualizarTorneo(Long id, ActualizarTorneoDTO dto);
    String cerrarTorneo(Long id);
    String cancelarTorneo(Long id);
}
