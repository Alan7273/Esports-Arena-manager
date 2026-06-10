package com.Esports.Msvcs_resultados.repositories;

import com.Esports.Msvcs_resultados.models.Resultado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResultadosRepository extends JpaRepository<Resultado, Long> {
    // Metodo que me permite buscar cada partida por ID
    Optional<Resultado> findByPartidaId(Long partidaId);
    // Metodo que me permite revisar el estado de la validacion
    List<Resultado> findByEstadoValidacion(String estadoValidacion);
    // Metodo que me permite buscar el id del ganador
    List<Resultado> findByGanadorId(Long ganadorId);
    // Metodo que permite buscar si existe partida por el id
    boolean existsByPartidaId(Long partidaId);
}
