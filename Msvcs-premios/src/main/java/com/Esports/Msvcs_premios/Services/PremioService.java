package com.Esports.Msvcs_premios.Services;

import com.Esports.Msvcs_premios.models.dtos.ActualizarPremioDTO;
import com.Esports.Msvcs_premios.models.dtos.CrearPremioDTO;
import com.Esports.Msvcs_premios.models.dtos.PremioResponseDTO;

import java.util.List;

public interface PremioService {
    PremioResponseDTO crearPremio(CrearPremioDTO dto);
    List<PremioResponseDTO> listarPremios();
    PremioResponseDTO buscarPremio(Long id);
    PremioResponseDTO actualizarPremio(Long id, ActualizarPremioDTO dto);
    String asignarPremio(Long id, Long participanteId);
}
