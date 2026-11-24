package com.Esports.Msvcs_torneos.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;
import com.Esports.Msvcs_torneos.models.Torneo;
import com.Esports.Msvcs_torneos.repositories.TorneosRepository;

/**
 * Siembra los torneos de datos.js (t1..t3). El backend exige fecha de fin: se toma la de la última partida.
 * Solo siembra si la tabla está vacía, así que es seguro reiniciar el servicio.
 * Los IDs se asignan en orden (1, 2, 3...) y los demás microservicios
 * referencian a estos mismos números (j1 = 1, e1 = 1, t1 = 1, p1 = 1...).
 * Si ya tienes datos viejos en Postgres, borra la base para que los IDs coincidan.
 */
@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    @Autowired
    private TorneosRepository torneosRepository;

    @Override
    public void run(String... args) {
        if (torneosRepository.count() > 0) {
            return;
        }
        torneosRepository.saveAll(List.of(
            torneo("Copa Apertura Valorant", 1L, LocalDate.of(2026, 11, 25), LocalDate.of(2026, 11, 26), 16, "Equipos", "ACTIVO"),
            torneo("Liga LoL Otoño", 2L, LocalDate.of(2026, 8, 20), LocalDate.of(2026, 11, 30), 10, "Equipos", "EN_CURSO"),
            torneo("1v1 Rocket League", 3L, LocalDate.of(2026, 7, 5), LocalDate.of(2026, 7, 10), 32, "Individual", "CERRADO")
        ));
        log.info("DataLoader: {} torneos cargados", torneosRepository.count());
    }

    private Torneo torneo(String nombre, Long juegoId, LocalDate inicio, LocalDate fin, int cupoMaximo, String modalidad, String estado) {
        Torneo t = new Torneo();
        t.setNombretorneo(nombre);
        t.setJuegoId(juegoId);
        t.setFechaInicio(inicio);
        t.setFechaFin(fin);
        t.setCupoMaximo(cupoMaximo);
        t.setModalidadTorneo(modalidad);
        t.setEstadoTorneo(estado);
        return t;
    }
}
