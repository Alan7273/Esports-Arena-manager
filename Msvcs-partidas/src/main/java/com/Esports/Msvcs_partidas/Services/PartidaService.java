package com.Esports.Msvcs_partidas.Services;

import com.Esports.Msvcs_partidas.models.dtos.CrearPartidaDTO;
import com.Esports.Msvcs_partidas.models.dtos.PartidaResponseDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PartidaService {
    PartidaResponseDTO crearPartida(CrearPartidaDTO dto);
    List<PartidaResponseDTO> listarPartidas();
    PartidaResponseDTO buscarPartida(Long id);
    PartidaResponseDTO actualizarHorario(Long id, LocalDateTime fechaHora);
    String cancelarPartida(Long id);
}
