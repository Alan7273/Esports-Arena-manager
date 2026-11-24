package com.Esports.Msvcs_sanciones.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;
import com.Esports.Msvcs_sanciones.models.Sancion;
import com.Esports.Msvcs_sanciones.repositories.SancionesRepository;

/**
 * Siembra las sanciones de los jugadores de datos.js (vigente => ACTIVA, no vigente => CERRADA). Fechas y severidad son de ejemplo: datos.js no las trae.
 * Solo siembra si la tabla está vacía, así que es seguro reiniciar el servicio.
 * Los IDs se asignan en orden (1, 2, 3...) y los demás microservicios
 * referencian a estos mismos números (j1 = 1, e1 = 1, t1 = 1, p1 = 1...).
 * Si ya tienes datos viejos en Postgres, borra la base para que los IDs coincidan.
 */
@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    @Autowired
    private SancionesRepository sancionesRepository;

    @Override
    public void run(String... args) {
        if (sancionesRepository.count() > 0) {
            return;
        }
        sancionesRepository.saveAll(List.of(
            sancion(1L, "Insultos en chat", LocalDate.now().minusDays(60), LocalDate.now().minusDays(30), "CERRADA", "LEVE"),
            sancion(3L, "Abandono reiterado", LocalDate.now().minusDays(7), LocalDate.now().plusDays(23), "ACTIVA", "GRAVE")
        ));
        log.info("DataLoader: {} sanciones cargadas", sancionesRepository.count());
    }

    private Sancion sancion(Long usuarioId, String motivo, LocalDate inicio, LocalDate fin, String estado, String severidad) {
        Sancion s = new Sancion();
        s.setUsuarioId(usuarioId);
        s.setMotivo(motivo);
        s.setFechaInicio(inicio);
        s.setFechaFin(fin);
        s.setEstadoSancion(estado);
        s.setSeveridad(severidad);
        return s;
    }
}
