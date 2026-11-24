package com.Esports.Msvcs_juegos.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;
import com.Esports.Msvcs_juegos.models.Juegos;
import com.Esports.Msvcs_juegos.repositories.JuegoRepository;

/**
 * Siembra los juegos de datos.js (g1..g3).
 * Solo siembra si la tabla está vacía, así que es seguro reiniciar el servicio.
 * Los IDs se asignan en orden (1, 2, 3...) y los demás microservicios
 * referencian a estos mismos números (j1 = 1, e1 = 1, t1 = 1, p1 = 1...).
 * Si ya tienes datos viejos en Postgres, borra la base para que los IDs coincidan.
 */
@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    @Autowired
    private JuegoRepository juegoRepository;

    @Override
    public void run(String... args) {
        if (juegoRepository.count() > 0) {
            return;
        }
        juegoRepository.saveAll(List.of(
            juego("Valorant", "FPS táctico", "Equipos", 5),
            juego("League of Legends", "MOBA", "Equipos", 5),
            juego("Rocket League", "Deportes", "Equipos", 3)
        ));
        log.info("DataLoader: {} juegos cargados", juegoRepository.count());
    }

    private Juegos juego(String nombre, String genero, String modalidad, int jugadoresPorEquipo) {
        Juegos j = new Juegos();
        j.setNombrejuegos(nombre);
        j.setGenerojuego(genero);
        j.setModalidadjuegos(modalidad);
        j.setJugadores_por_equipo(jugadoresPorEquipo);
        j.setEstadojuego("ACTIVO");
        return j;
    }
}
