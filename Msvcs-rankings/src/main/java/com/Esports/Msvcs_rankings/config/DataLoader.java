package com.Esports.Msvcs_rankings.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;
import com.Esports.Msvcs_rankings.models.Ranking;
import com.Esports.Msvcs_rankings.repositories.RankingsRepository;

/**
 * Siembra las tablas de posiciones de datos.js. Victorias, derrotas y diferencia se calculan con las partidas del torneo (la diferencia no baja de 0 porque la columna no admite negativos).
 * Solo siembra si la tabla está vacía, así que es seguro reiniciar el servicio.
 * Los IDs se asignan en orden (1, 2, 3...) y los demás microservicios
 * referencian a estos mismos números (j1 = 1, e1 = 1, t1 = 1, p1 = 1...).
 * Si ya tienes datos viejos en Postgres, borra la base para que los IDs coincidan.
 */
@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    @Autowired
    private RankingsRepository rankingsRepository;

    @Override
    public void run(String... args) {
        if (rankingsRepository.count() > 0) {
            return;
        }
        rankingsRepository.saveAll(List.of(
            ranking(2L, 8L, 6, 2, 0, 3, 1),
            ranking(2L, 14L, 6, 2, 0, 3, 2),
            ranking(2L, 10L, 3, 1, 1, 0, 3),
            ranking(2L, 13L, 3, 1, 1, 0, 4),
            ranking(2L, 16L, 3, 1, 0, 2, 5),
            ranking(2L, 9L, 0, 0, 1, 0, 6),
            ranking(2L, 11L, 0, 0, 1, 0, 7),
            ranking(2L, 12L, 0, 0, 1, 0, 8),
            ranking(2L, 15L, 0, 0, 1, 0, 9),
            ranking(2L, 17L, 0, 0, 1, 0, 10),
            ranking(3L, 1L, 15, 5, 0, 8, 1),
            ranking(3L, 30L, 12, 4, 1, 7, 2),
            ranking(3L, 14L, 9, 3, 1, 5, 3),
            ranking(3L, 21L, 9, 3, 1, 3, 4),
            ranking(3L, 7L, 6, 2, 1, 2, 5),
            ranking(3L, 13L, 6, 2, 1, 4, 6),
            ranking(3L, 22L, 6, 2, 1, 3, 7),
            ranking(3L, 29L, 6, 2, 1, 2, 8),
            ranking(3L, 5L, 3, 1, 1, 0, 9),
            ranking(3L, 8L, 3, 1, 1, 1, 10),
            ranking(3L, 10L, 3, 1, 1, 0, 11),
            ranking(3L, 17L, 3, 1, 1, 0, 12),
            ranking(3L, 18L, 3, 1, 1, 1, 13),
            ranking(3L, 25L, 3, 1, 1, 0, 14),
            ranking(3L, 26L, 3, 1, 1, 0, 15),
            ranking(3L, 33L, 3, 1, 1, 0, 16),
            ranking(3L, 2L, 0, 0, 1, 0, 17),
            ranking(3L, 4L, 0, 0, 1, 0, 18),
            ranking(3L, 6L, 0, 0, 1, 0, 19),
            ranking(3L, 9L, 0, 0, 1, 0, 20),
            ranking(3L, 11L, 0, 0, 1, 0, 21),
            ranking(3L, 12L, 0, 0, 1, 0, 22),
            ranking(3L, 15L, 0, 0, 1, 0, 23),
            ranking(3L, 16L, 0, 0, 1, 0, 24),
            ranking(3L, 19L, 0, 0, 1, 0, 25),
            ranking(3L, 20L, 0, 0, 1, 0, 26),
            ranking(3L, 23L, 0, 0, 1, 0, 27),
            ranking(3L, 24L, 0, 0, 1, 0, 28),
            ranking(3L, 27L, 0, 0, 1, 0, 29),
            ranking(3L, 28L, 0, 0, 1, 0, 30),
            ranking(3L, 31L, 0, 0, 1, 0, 31),
            ranking(3L, 32L, 0, 0, 1, 0, 32)
        ));
        log.info("DataLoader: {} filas de ranking cargadas", rankingsRepository.count());
    }

    private Ranking ranking(Long torneoId, Long participanteId, int puntos, int victorias, int derrotas, int diferencia, int posicion) {
        Ranking r = new Ranking();
        r.setTorneoId(torneoId);
        r.setParticipanteId(participanteId);
        r.setPuntos(puntos);
        r.setVictorias(victorias);
        r.setDerrotas(derrotas);
        r.setDiferencia(diferencia);
        r.setPosicion(posicion);
        return r;
    }
}
