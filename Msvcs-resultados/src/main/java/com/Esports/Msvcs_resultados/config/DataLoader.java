package com.Esports.Msvcs_resultados.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;
import com.Esports.Msvcs_resultados.models.Resultado;
import com.Esports.Msvcs_resultados.repositories.ResultadosRepository;

/**
 * Siembra los resultados de las partidas finalizadas. puntajeA/B salen del marcador de datos.js (ganador-perdedor) ordenado según quién es A y B en la partida.
 * Solo siembra si la tabla está vacía, así que es seguro reiniciar el servicio.
 * Los IDs se asignan en orden (1, 2, 3...) y los demás microservicios
 * referencian a estos mismos números (j1 = 1, e1 = 1, t1 = 1, p1 = 1...).
 * Si ya tienes datos viejos en Postgres, borra la base para que los IDs coincidan.
 */
@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    @Autowired
    private ResultadosRepository resultadosRepository;

    @Override
    public void run(String... args) {
        if (resultadosRepository.count() > 0) {
            return;
        }
        resultadosRepository.saveAll(List.of(
            resultado(4L, 8L, 2, 0, LocalDate.of(2026, 8, 20)),
            resultado(5L, 10L, 2, 1, LocalDate.of(2026, 8, 20)),
            resultado(6L, 13L, 0, 2, LocalDate.of(2026, 8, 20)),
            resultado(7L, 14L, 2, 1, LocalDate.of(2026, 8, 21)),
            resultado(8L, 16L, 2, 0, LocalDate.of(2026, 8, 21)),
            resultado(9L, 8L, 2, 1, LocalDate.of(2026, 9, 10)),
            resultado(10L, 14L, 0, 2, LocalDate.of(2026, 9, 10)),
            resultado(14L, 1L, 3, 1, LocalDate.of(2026, 7, 5)),
            resultado(15L, 5L, 2, 3, LocalDate.of(2026, 7, 5)),
            resultado(16L, 7L, 0, 3, LocalDate.of(2026, 7, 5)),
            resultado(17L, 8L, 3, 1, LocalDate.of(2026, 7, 5)),
            resultado(18L, 10L, 3, 2, LocalDate.of(2026, 7, 5)),
            resultado(19L, 13L, 1, 3, LocalDate.of(2026, 7, 5)),
            resultado(20L, 14L, 3, 0, LocalDate.of(2026, 7, 5)),
            resultado(21L, 17L, 2, 3, LocalDate.of(2026, 7, 5)),
            resultado(22L, 18L, 3, 1, LocalDate.of(2026, 7, 6)),
            resultado(23L, 21L, 2, 3, LocalDate.of(2026, 7, 6)),
            resultado(24L, 22L, 3, 0, LocalDate.of(2026, 7, 6)),
            resultado(25L, 25L, 1, 3, LocalDate.of(2026, 7, 6)),
            resultado(26L, 26L, 3, 2, LocalDate.of(2026, 7, 6)),
            resultado(27L, 29L, 1, 3, LocalDate.of(2026, 7, 6)),
            resultado(28L, 30L, 3, 0, LocalDate.of(2026, 7, 6)),
            resultado(29L, 33L, 2, 3, LocalDate.of(2026, 7, 6)),
            resultado(30L, 1L, 3, 1, LocalDate.of(2026, 7, 7)),
            resultado(31L, 7L, 3, 2, LocalDate.of(2026, 7, 7)),
            resultado(32L, 13L, 0, 3, LocalDate.of(2026, 7, 7)),
            resultado(33L, 14L, 3, 1, LocalDate.of(2026, 7, 7)),
            resultado(34L, 21L, 2, 3, LocalDate.of(2026, 7, 7)),
            resultado(35L, 22L, 3, 0, LocalDate.of(2026, 7, 7)),
            resultado(36L, 29L, 1, 3, LocalDate.of(2026, 7, 7)),
            resultado(37L, 30L, 3, 2, LocalDate.of(2026, 7, 7)),
            resultado(38L, 1L, 3, 1, LocalDate.of(2026, 7, 8)),
            resultado(39L, 14L, 2, 3, LocalDate.of(2026, 7, 8)),
            resultado(40L, 21L, 3, 0, LocalDate.of(2026, 7, 8)),
            resultado(41L, 30L, 1, 3, LocalDate.of(2026, 7, 8)),
            resultado(42L, 1L, 3, 2, LocalDate.of(2026, 7, 9)),
            resultado(43L, 30L, 1, 3, LocalDate.of(2026, 7, 9)),
            resultado(44L, 1L, 4, 3, LocalDate.of(2026, 7, 10))
        ));
        log.info("DataLoader: {} resultados cargados", resultadosRepository.count());
    }

    private Resultado resultado(Long partidaId, Long ganadorId, int puntajeA, int puntajeB, LocalDate fechaRegistro) {
        Resultado r = new Resultado();
        r.setPartidaId(partidaId);
        r.setGanadorId(ganadorId);
        r.setPuntajeA(puntajeA);
        r.setPuntajeB(puntajeB);
        r.setEstadoValidacion("VALIDADO");
        r.setFechaRegistro(fechaRegistro);
        return r;
    }
}
