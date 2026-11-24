package com.Esports.Msvcs_equipos.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;
import com.Esports.Msvcs_equipos.models.Equipos;
import com.Esports.Msvcs_equipos.models.Miembro_equipo;
import com.Esports.Msvcs_equipos.repositories.EquiposRepository;
import com.Esports.Msvcs_equipos.repositories.MiembroEquipoRepository;

/**
 * Siembra los equipos de datos.js (e1..e17) y sus integrantes con el rol de cada uno.
 * Solo siembra si la tabla está vacía, así que es seguro reiniciar el servicio.
 * Los IDs se asignan en orden (1, 2, 3...) y los demás microservicios
 * referencian a estos mismos números (j1 = 1, e1 = 1, t1 = 1, p1 = 1...).
 * Si ya tienes datos viejos en Postgres, borra la base para que los IDs coincidan.
 */
@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    @Autowired
    private EquiposRepository equiposRepository;

    @Autowired
    private MiembroEquipoRepository miembroEquipoRepository;

    @Override
    public void run(String... args) {
        if (equiposRepository.count() > 0) {
            return;
        }
        equiposRepository.saveAll(List.of(
            equipo("Los Halcones", 1L, 1L, "ACTIVO"),
            equipo("Team Fenix", 3L, 3L, "INACTIVO"),
            equipo("Neon Wolves", 7L, 1L, "ACTIVO"),
            equipo("Crimson Vipers", 12L, 1L, "ACTIVO"),
            equipo("Silent Ravens", 17L, 1L, "ACTIVO"),
            equipo("Iron Titans", 22L, 1L, "ACTIVO"),
            equipo("Pixel Pirates", 27L, 1L, "ACTIVO"),
            equipo("Dragon Guard", 32L, 2L, "ACTIVO"),
            equipo("Nexus Raiders", 37L, 2L, "ACTIVO"),
            equipo("Baron Hunters", 42L, 2L, "ACTIVO"),
            equipo("Void Walkers", 47L, 2L, "ACTIVO"),
            equipo("Storm Breakers", 52L, 2L, "ACTIVO"),
            equipo("Arcane Order", 57L, 2L, "ACTIVO"),
            equipo("Night Owls", 62L, 2L, "ACTIVO"),
            equipo("Blue Phoenix", 67L, 2L, "ACTIVO"),
            equipo("Frost Legion", 72L, 2L, "ACTIVO"),
            equipo("Rift Kings", 77L, 2L, "ACTIVO")
        ));
        miembroEquipoRepository.saveAll(List.of(
            miembro(1L, 1L, "Capitán"),
            miembro(1L, 2L, "Duelista"),
            miembro(1L, 4L, "Controlador"),
            miembro(1L, 5L, "Centinela"),
            miembro(1L, 6L, "Iniciador"),
            miembro(2L, 3L, "Capitán"),
            miembro(3L, 7L, "Capitán"),
            miembro(3L, 8L, "Duelista"),
            miembro(3L, 9L, "Iniciador"),
            miembro(3L, 10L, "Centinela"),
            miembro(3L, 11L, "Controlador"),
            miembro(4L, 12L, "Capitán"),
            miembro(4L, 13L, "Duelista"),
            miembro(4L, 14L, "Controlador"),
            miembro(4L, 15L, "Centinela"),
            miembro(4L, 16L, "Iniciador"),
            miembro(5L, 17L, "Capitán"),
            miembro(5L, 18L, "Duelista"),
            miembro(5L, 19L, "Controlador"),
            miembro(5L, 20L, "Centinela"),
            miembro(5L, 21L, "Iniciador"),
            miembro(6L, 22L, "Capitán"),
            miembro(6L, 23L, "Duelista"),
            miembro(6L, 24L, "Controlador"),
            miembro(6L, 25L, "Centinela"),
            miembro(6L, 26L, "Iniciador"),
            miembro(7L, 27L, "Capitán"),
            miembro(7L, 28L, "Duelista"),
            miembro(7L, 29L, "Controlador"),
            miembro(7L, 30L, "Centinela"),
            miembro(7L, 31L, "Iniciador"),
            miembro(8L, 32L, "Capitán"),
            miembro(8L, 33L, "Jungla"),
            miembro(8L, 34L, "Mid"),
            miembro(8L, 35L, "ADC"),
            miembro(8L, 36L, "Soporte"),
            miembro(9L, 37L, "Capitán"),
            miembro(9L, 38L, "Jungla"),
            miembro(9L, 39L, "Mid"),
            miembro(9L, 40L, "ADC"),
            miembro(9L, 41L, "Soporte"),
            miembro(10L, 42L, "Capitán"),
            miembro(10L, 43L, "Jungla"),
            miembro(10L, 44L, "Mid"),
            miembro(10L, 45L, "ADC"),
            miembro(10L, 46L, "Soporte"),
            miembro(11L, 47L, "Capitán"),
            miembro(11L, 48L, "Jungla"),
            miembro(11L, 49L, "Mid"),
            miembro(11L, 50L, "ADC"),
            miembro(11L, 51L, "Soporte"),
            miembro(12L, 52L, "Capitán"),
            miembro(12L, 53L, "Jungla"),
            miembro(12L, 54L, "Mid"),
            miembro(12L, 55L, "ADC"),
            miembro(12L, 56L, "Soporte"),
            miembro(13L, 57L, "Capitán"),
            miembro(13L, 58L, "Jungla"),
            miembro(13L, 59L, "Mid"),
            miembro(13L, 60L, "ADC"),
            miembro(13L, 61L, "Soporte"),
            miembro(14L, 62L, "Capitán"),
            miembro(14L, 63L, "Jungla"),
            miembro(14L, 64L, "Mid"),
            miembro(14L, 65L, "ADC"),
            miembro(14L, 66L, "Soporte"),
            miembro(15L, 67L, "Capitán"),
            miembro(15L, 68L, "Jungla"),
            miembro(15L, 69L, "Mid"),
            miembro(15L, 70L, "ADC"),
            miembro(15L, 71L, "Soporte"),
            miembro(16L, 72L, "Capitán"),
            miembro(16L, 73L, "Jungla"),
            miembro(16L, 74L, "Mid"),
            miembro(16L, 75L, "ADC"),
            miembro(16L, 76L, "Soporte"),
            miembro(17L, 77L, "Capitán"),
            miembro(17L, 78L, "Jungla"),
            miembro(17L, 79L, "Mid"),
            miembro(17L, 80L, "ADC"),
            miembro(17L, 81L, "Soporte")
        ));
        log.info("DataLoader: {} equipos y {} miembros cargados", equiposRepository.count(), miembroEquipoRepository.count());
    }

    private Equipos equipo(String nombre, Long capitanId, Long juegoId, String estado) {
        Equipos e = new Equipos();
        e.setNombreequipo(nombre);
        e.setCapitanId(capitanId);
        e.setJuegoprincipalId(juegoId);
        e.setEstadoequipo(estado);
        return e;
    }

    private Miembro_equipo miembro(Long equipoId, Long usuarioId, String rol) {
        Miembro_equipo m = new Miembro_equipo();
        m.setEquipoId(equipoId);
        m.setUsuarioId(usuarioId);
        m.setRolDentroEquipo(rol);
        m.setFechaIngreso(LocalDateTime.now());
        return m;
    }
}
