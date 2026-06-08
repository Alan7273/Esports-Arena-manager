package com.Esports.Msvcs_rankings.repositories;

import com.Esports.Msvcs_rankings.models.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RankingsRepository extends JpaRepository<Ranking, Long> {
    // Metodo que me permite buscar el torneo por ID
    List<Ranking> findByTorneoId(Long torneoId);
    // Metodo que me permite encontrar cada participante por ID
    Optional<Ranking> findByParticipanteId(Long participanteId);
    // Metodo que me permite buscar el Torneo y participante
    Optional<Ranking> findByTorneoIdAndParticipanteId(Long torneoId, Long participanteId);
}
