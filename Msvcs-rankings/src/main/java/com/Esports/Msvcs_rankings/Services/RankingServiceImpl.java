package com.Esports.Msvcs_rankings.Services;

import com.Esports.Msvcs_rankings.exceptions.ResourceNotFoundException;
import com.Esports.Msvcs_rankings.models.Ranking;
import com.Esports.Msvcs_rankings.models.dtos.RankingResponseDTO;
import com.Esports.Msvcs_rankings.repositories.RankingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RankingServiceImpl implements RankingService {

    @Autowired
    private RankingsRepository rankingRepository;

    @Override
    public String crearRankingParticipante(Long torneoId, Long participanteId) {
        Ranking ranking = new Ranking();
        ranking.setTorneoId(torneoId);
        ranking.setParticipanteId(participanteId);
        ranking.setPuntos(0);
        ranking.setVictorias(0);
        ranking.setDerrotas(0);
        ranking.setDiferencia(0);
        ranking.setPosicion(0);
        rankingRepository.save(ranking);
        return "Ranking creado para participante " + participanteId;
    }

    @Override
    public List<RankingResponseDTO> obtenerTabla(Long torneoId) {
        return rankingRepository.findByTorneoId(torneoId).stream().map(ranking -> {
            RankingResponseDTO dto = new RankingResponseDTO();
            dto.setRankingId(ranking.getRankingId());
            dto.setTorneoId(ranking.getTorneoId());
            dto.setParticipanteId(ranking.getParticipanteId());
            dto.setPuntos(ranking.getPuntos());
            dto.setVictorias(ranking.getVictorias());
            dto.setDerrotas(ranking.getDerrotas());
            dto.setDiferencia(ranking.getDiferencia());
            dto.setPosicion(ranking.getPosicion());
            return dto;
        }).toList();
    }

    @Override
    public RankingResponseDTO buscarRanking(Long id) {
        Ranking ranking = rankingRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Ranking no encontrado"));

        RankingResponseDTO dto = new RankingResponseDTO();
        dto.setRankingId(ranking.getRankingId());
        dto.setTorneoId(ranking.getTorneoId());
        dto.setParticipanteId(ranking.getParticipanteId());
        dto.setPuntos(ranking.getPuntos());
        dto.setVictorias(ranking.getVictorias());
        dto.setDerrotas(ranking.getDerrotas());
        dto.setDiferencia(ranking.getDiferencia());
        dto.setPosicion(ranking.getPosicion());

        return dto;
    }

    @Override
    public String actualizarRanking(Long torneoId) {
        List<Ranking> rankings = rankingRepository.findByTorneoId(torneoId);

        if (rankings.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron rankings para el torneo");
        }

        rankings.sort((a, b) -> b.getPuntos() - a.getPuntos());

        for (int i = 0; i < rankings.size(); i++) {
            rankings.get(i).setPosicion(i + 1);
        }

        rankingRepository.saveAll(rankings);

        return "Ranking actualizado correctamente";
    }

    @Override
    public String reiniciarRanking(Long torneoId) {
        List<Ranking> rankings = rankingRepository.findByTorneoId(torneoId);

        if (rankings.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron rankings para el torneo");
        }

        rankings.forEach(ranking -> {
            ranking.setPuntos(0);
            ranking.setVictorias(0);
            ranking.setDerrotas(0);
            ranking.setDiferencia(0);
            ranking.setPosicion(0);
        });

        rankingRepository.saveAll(rankings);

        return "Ranking reiniciado correctamente";
    }
}