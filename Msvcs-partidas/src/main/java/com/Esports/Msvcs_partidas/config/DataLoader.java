package com.Esports.Msvcs_partidas.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;
import com.Esports.Msvcs_partidas.models.Partida;
import com.Esports.Msvcs_partidas.repositories.PartidaRepository;

/**
 * Siembra las 44 partidas de datos.js (p1..p44) con su ronda, fecha y estado.
 * Solo siembra si la tabla está vacía, así que es seguro reiniciar el servicio.
 * Los IDs se asignan en orden (1, 2, 3...) y los demás microservicios
 * referencian a estos mismos números (j1 = 1, e1 = 1, t1 = 1, p1 = 1...).
 * Si ya tienes datos viejos en Postgres, borra la base para que los IDs coincidan.
 */
@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    @Autowired
    private PartidaRepository partidaRepository;

    @Override
    public void run(String... args) {
        if (partidaRepository.count() > 0) {
            return;
        }
        partidaRepository.saveAll(List.of(
            partida(1L, 1L, 3L, 1, LocalDateTime.of(2026, 11, 25, 18, 0), "PROGRAMADA"),
            partida(1L, 4L, 5L, 1, LocalDateTime.of(2026, 11, 25, 19, 0), "PROGRAMADA"),
            partida(1L, 6L, 7L, 1, LocalDateTime.of(2026, 11, 25, 20, 0), "PROGRAMADA"),
            partida(2L, 8L, 9L, 1, LocalDateTime.of(2026, 8, 20, 18, 0), "FINALIZADA"),
            partida(2L, 10L, 11L, 1, LocalDateTime.of(2026, 8, 20, 19, 0), "FINALIZADA"),
            partida(2L, 12L, 13L, 1, LocalDateTime.of(2026, 8, 20, 20, 0), "FINALIZADA"),
            partida(2L, 14L, 15L, 1, LocalDateTime.of(2026, 8, 21, 18, 0), "FINALIZADA"),
            partida(2L, 16L, 17L, 1, LocalDateTime.of(2026, 8, 21, 19, 0), "FINALIZADA"),
            partida(2L, 8L, 10L, 2, LocalDateTime.of(2026, 9, 10, 18, 0), "FINALIZADA"),
            partida(2L, 13L, 14L, 2, LocalDateTime.of(2026, 9, 10, 19, 0), "FINALIZADA"),
            partida(2L, 16L, 9L, 2, LocalDateTime.of(2026, 11, 30, 18, 0), "PROGRAMADA"),
            partida(2L, 11L, 12L, 2, LocalDateTime.of(2026, 11, 30, 19, 0), "PROGRAMADA"),
            partida(2L, 15L, 17L, 2, LocalDateTime.of(2026, 11, 30, 20, 0), "PROGRAMADA"),
            partida(3L, 1L, 2L, 1, LocalDateTime.of(2026, 7, 5, 10, 0), "FINALIZADA"),
            partida(3L, 4L, 5L, 1, LocalDateTime.of(2026, 7, 5, 11, 0), "FINALIZADA"),
            partida(3L, 6L, 7L, 1, LocalDateTime.of(2026, 7, 5, 12, 0), "FINALIZADA"),
            partida(3L, 8L, 9L, 1, LocalDateTime.of(2026, 7, 5, 13, 0), "FINALIZADA"),
            partida(3L, 10L, 11L, 1, LocalDateTime.of(2026, 7, 5, 14, 0), "FINALIZADA"),
            partida(3L, 12L, 13L, 1, LocalDateTime.of(2026, 7, 5, 15, 0), "FINALIZADA"),
            partida(3L, 14L, 15L, 1, LocalDateTime.of(2026, 7, 5, 16, 0), "FINALIZADA"),
            partida(3L, 16L, 17L, 1, LocalDateTime.of(2026, 7, 5, 17, 0), "FINALIZADA"),
            partida(3L, 18L, 19L, 1, LocalDateTime.of(2026, 7, 6, 10, 0), "FINALIZADA"),
            partida(3L, 20L, 21L, 1, LocalDateTime.of(2026, 7, 6, 11, 0), "FINALIZADA"),
            partida(3L, 22L, 23L, 1, LocalDateTime.of(2026, 7, 6, 12, 0), "FINALIZADA"),
            partida(3L, 24L, 25L, 1, LocalDateTime.of(2026, 7, 6, 13, 0), "FINALIZADA"),
            partida(3L, 26L, 27L, 1, LocalDateTime.of(2026, 7, 6, 14, 0), "FINALIZADA"),
            partida(3L, 28L, 29L, 1, LocalDateTime.of(2026, 7, 6, 15, 0), "FINALIZADA"),
            partida(3L, 30L, 31L, 1, LocalDateTime.of(2026, 7, 6, 16, 0), "FINALIZADA"),
            partida(3L, 32L, 33L, 1, LocalDateTime.of(2026, 7, 6, 17, 0), "FINALIZADA"),
            partida(3L, 1L, 5L, 2, LocalDateTime.of(2026, 7, 7, 10, 0), "FINALIZADA"),
            partida(3L, 7L, 8L, 2, LocalDateTime.of(2026, 7, 7, 11, 0), "FINALIZADA"),
            partida(3L, 10L, 13L, 2, LocalDateTime.of(2026, 7, 7, 12, 0), "FINALIZADA"),
            partida(3L, 14L, 17L, 2, LocalDateTime.of(2026, 7, 7, 13, 0), "FINALIZADA"),
            partida(3L, 18L, 21L, 2, LocalDateTime.of(2026, 7, 7, 14, 0), "FINALIZADA"),
            partida(3L, 22L, 25L, 2, LocalDateTime.of(2026, 7, 7, 15, 0), "FINALIZADA"),
            partida(3L, 26L, 29L, 2, LocalDateTime.of(2026, 7, 7, 16, 0), "FINALIZADA"),
            partida(3L, 30L, 33L, 2, LocalDateTime.of(2026, 7, 7, 17, 0), "FINALIZADA"),
            partida(3L, 1L, 7L, 3, LocalDateTime.of(2026, 7, 8, 16, 0), "FINALIZADA"),
            partida(3L, 13L, 14L, 3, LocalDateTime.of(2026, 7, 8, 17, 0), "FINALIZADA"),
            partida(3L, 21L, 22L, 3, LocalDateTime.of(2026, 7, 8, 18, 0), "FINALIZADA"),
            partida(3L, 29L, 30L, 3, LocalDateTime.of(2026, 7, 8, 19, 0), "FINALIZADA"),
            partida(3L, 1L, 14L, 4, LocalDateTime.of(2026, 7, 9, 18, 0), "FINALIZADA"),
            partida(3L, 21L, 30L, 4, LocalDateTime.of(2026, 7, 9, 19, 0), "FINALIZADA"),
            partida(3L, 1L, 30L, 5, LocalDateTime.of(2026, 7, 10, 19, 0), "FINALIZADA")
        ));
        log.info("DataLoader: {} partidas cargadas", partidaRepository.count());
    }

    private Partida partida(Long torneoId, Long participanteA, Long participanteB, int ronda, LocalDateTime fechaHora, String estado) {
        Partida p = new Partida();
        p.setTorneoId(torneoId);
        p.setParticipanteAId(participanteA);
        p.setParticipanteBId(participanteB);
        p.setRonda(ronda);
        p.setFechaHora(fechaHora);
        p.setEstadopartida(estado);
        return p;
    }
}
