package com.Esports.Msvcs_rankings.Services;


import com.Esports.Msvcs_rankings.models.dtos.RankingResponseDTO;

import java.util.List;

public interface RankingService {
    String crearRankingParticipante(Long torneoId, Long participanteId);
    List<RankingResponseDTO> obtenerTabla(Long torneoId);
    RankingResponseDTO buscarRanking(Long id);
    String actualizarRanking(Long torneoId);
    String reiniciarRanking(Long torneoId);
}
