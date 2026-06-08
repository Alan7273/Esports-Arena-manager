package com.Esports.Msvcs_partidas.repositories;

import com.Esports.Msvcs_partidas.models.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Long> {
    // Metodo que me permite buscar el torneo por el ID
    List<Partida> findByTorneoId(Long torneoId);
    // Metodo que me permite buscar la ronda
    List<Partida> findByRonda(Integer ronda);
    // Metodo que me permite encontrar el estado de la partida
    List<Partida> findByEstadopartida(String estadopartida);
    // Metodo que me permite buscar el participante a y el participante b
    List<Partida> findByParticipanteAIdOrParticipanteBId(Long participanteAId, Long participanteBId);
}
