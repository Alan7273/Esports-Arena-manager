package com.Esports.Msvcs_inscripciones.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;
import com.Esports.Msvcs_inscripciones.models.Inscripcion;
import com.Esports.Msvcs_inscripciones.repositories.InscripcionRepository;

/**
 * Siembra las inscripciones a partir de "participantes" de cada torneo en datos.js (equipos o jugadores).
 * Solo siembra si la tabla está vacía, así que es seguro reiniciar el servicio.
 * Los IDs se asignan en orden (1, 2, 3...) y los demás microservicios
 * referencian a estos mismos números (j1 = 1, e1 = 1, t1 = 1, p1 = 1...).
 * Si ya tienes datos viejos en Postgres, borra la base para que los IDs coincidan.
 */
@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Override
    public void run(String... args) {
        if (inscripcionRepository.count() > 0) {
            return;
        }
        inscripcionRepository.saveAll(List.of(
            inscripcion(1L, 1L, 1L, "Los Halcones", "EQUIPO", LocalDate.of(2026, 11, 20)),
            inscripcion(1L, 3L, 7L, "Neon Wolves", "EQUIPO", LocalDate.of(2026, 11, 20)),
            inscripcion(1L, 4L, 12L, "Crimson Vipers", "EQUIPO", LocalDate.of(2026, 11, 20)),
            inscripcion(1L, 5L, 17L, "Silent Ravens", "EQUIPO", LocalDate.of(2026, 11, 20)),
            inscripcion(1L, 6L, 22L, "Iron Titans", "EQUIPO", LocalDate.of(2026, 11, 20)),
            inscripcion(1L, 7L, 27L, "Pixel Pirates", "EQUIPO", LocalDate.of(2026, 11, 20)),
            inscripcion(2L, 8L, 32L, "Dragon Guard", "EQUIPO", LocalDate.of(2026, 8, 15)),
            inscripcion(2L, 9L, 37L, "Nexus Raiders", "EQUIPO", LocalDate.of(2026, 8, 15)),
            inscripcion(2L, 10L, 42L, "Baron Hunters", "EQUIPO", LocalDate.of(2026, 8, 15)),
            inscripcion(2L, 11L, 47L, "Void Walkers", "EQUIPO", LocalDate.of(2026, 8, 15)),
            inscripcion(2L, 12L, 52L, "Storm Breakers", "EQUIPO", LocalDate.of(2026, 8, 15)),
            inscripcion(2L, 13L, 57L, "Arcane Order", "EQUIPO", LocalDate.of(2026, 8, 15)),
            inscripcion(2L, 14L, 62L, "Night Owls", "EQUIPO", LocalDate.of(2026, 8, 15)),
            inscripcion(2L, 15L, 67L, "Blue Phoenix", "EQUIPO", LocalDate.of(2026, 8, 15)),
            inscripcion(2L, 16L, 72L, "Frost Legion", "EQUIPO", LocalDate.of(2026, 8, 15)),
            inscripcion(2L, 17L, 77L, "Rift Kings", "EQUIPO", LocalDate.of(2026, 8, 15)),
            inscripcion(3L, null, 1L, "shadow99", "JUGADOR", LocalDate.of(2026, 7, 1)),
            inscripcion(3L, null, 2L, "nova_x", "JUGADOR", LocalDate.of(2026, 7, 1)),
            inscripcion(3L, null, 4L, "vexa", "JUGADOR", LocalDate.of(2026, 7, 1)),
            inscripcion(3L, null, 5L, "ryuzen", "JUGADOR", LocalDate.of(2026, 7, 1)),
            inscripcion(3L, null, 6L, "lunaris", "JUGADOR", LocalDate.of(2026, 7, 1)),
            inscripcion(3L, null, 7L, "wolfgang", "JUGADOR", LocalDate.of(2026, 7, 1)),
            inscripcion(3L, null, 8L, "zeta", "JUGADOR", LocalDate.of(2026, 7, 1)),
            inscripcion(3L, null, 9L, "kairo", "JUGADOR", LocalDate.of(2026, 7, 1)),
            inscripcion(3L, null, 10L, "pixel", "JUGADOR", LocalDate.of(2026, 7, 1)),
            inscripcion(3L, null, 11L, "drako", "JUGADOR", LocalDate.of(2026, 7, 1)),
            inscripcion(3L, null, 12L, "viper01", "JUGADOR", LocalDate.of(2026, 7, 1)),
            inscripcion(3L, null, 13L, "sofi_x", "JUGADOR", LocalDate.of(2026, 7, 1)),
            inscripcion(3L, null, 14L, "benja", "JUGADOR", LocalDate.of(2026, 7, 1)),
            inscripcion(3L, null, 15L, "anto", "JUGADOR", LocalDate.of(2026, 7, 1)),
            inscripcion(3L, null, 16L, "joaco", "JUGADOR", LocalDate.of(2026, 7, 1)),
            inscripcion(3L, null, 17L, "raven", "JUGADOR", LocalDate.of(2026, 7, 1)),
            inscripcion(3L, null, 18L, "seba", "JUGADOR", LocalDate.of(2026, 7, 1)),
            inscripcion(3L, null, 19L, "isi", "JUGADOR", LocalDate.of(2026, 7, 1)),
            inscripcion(3L, null, 20L, "cris", "JUGADOR", LocalDate.of(2026, 7, 1)),
            inscripcion(3L, null, 21L, "fer", "JUGADOR", LocalDate.of(2026, 7, 1)),
            inscripcion(3L, null, 22L, "titan", "JUGADOR", LocalDate.of(2026, 7, 1)),
            inscripcion(3L, null, 23L, "cata", "JUGADOR", LocalDate.of(2026, 7, 1)),
            inscripcion(3L, null, 24L, "vicho", "JUGADOR", LocalDate.of(2026, 7, 1)),
            inscripcion(3L, null, 25L, "emi", "JUGADOR", LocalDate.of(2026, 7, 1)),
            inscripcion(3L, null, 26L, "rod", "JUGADOR", LocalDate.of(2026, 7, 1)),
            inscripcion(3L, null, 27L, "pirata", "JUGADOR", LocalDate.of(2026, 7, 1)),
            inscripcion(3L, null, 28L, "lucas", "JUGADOR", LocalDate.of(2026, 7, 1)),
            inscripcion(3L, null, 29L, "paula", "JUGADOR", LocalDate.of(2026, 7, 1)),
            inscripcion(3L, null, 30L, "gabo", "JUGADOR", LocalDate.of(2026, 7, 1)),
            inscripcion(3L, null, 31L, "dani", "JUGADOR", LocalDate.of(2026, 7, 1)),
            inscripcion(3L, null, 32L, "dragon", "JUGADOR", LocalDate.of(2026, 7, 1)),
            inscripcion(3L, null, 33L, "caro", "JUGADOR", LocalDate.of(2026, 7, 1))
        ));
        log.info("DataLoader: {} inscripciones cargadas", inscripcionRepository.count());
    }

    private Inscripcion inscripcion(Long torneoId, Long equipoId, Long usuarioId, String nombre, String tipo, LocalDate fecha) {
        Inscripcion i = new Inscripcion();
        i.setTorneoId(torneoId);
        i.setEquipoId(equipoId);
        i.setUsuarioId(usuarioId);
        i.setNombreJugador(nombre);
        i.setTipoParticipante(tipo);
        i.setEstado("CONFIRMADA");
        i.setFechaInscripcion(fecha);
        return i;
    }
}
