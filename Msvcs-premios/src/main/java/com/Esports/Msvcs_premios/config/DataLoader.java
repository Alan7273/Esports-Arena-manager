package com.Esports.Msvcs_premios.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;
import com.Esports.Msvcs_premios.models.Premio;
import com.Esports.Msvcs_premios.repositories.PremioRepository;

/**
 * Siembra los premios de datos.js. Cuando el premio es un monto ($200.000) ese es el valor; si es un objeto (Beca gaming, Mouse gamer) queda en 1.0 porque la columna exige un valor positivo.
 * Solo siembra si la tabla está vacía, así que es seguro reiniciar el servicio.
 * Los IDs se asignan en orden (1, 2, 3...) y los demás microservicios
 * referencian a estos mismos números (j1 = 1, e1 = 1, t1 = 1, p1 = 1...).
 * Si ya tienes datos viejos en Postgres, borra la base para que los IDs coincidan.
 */
@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    @Autowired
    private PremioRepository premioRepository;

    @Override
    public void run(String... args) {
        if (premioRepository.count() > 0) {
            return;
        }
        premioRepository.saveAll(List.of(
            premio(1L, 1, "$200.000 + trofeo", 200000.0),
            premio(1L, 2, "$80.000", 80000.0),
            premio(2L, 1, "Beca gaming", 1.0),
            premio(3L, 1, "Mouse gamer", 1.0)
        ));
        log.info("DataLoader: {} premios cargados", premioRepository.count());
    }

    private Premio premio(Long torneoId, int posicion, String descripcion, double valor) {
        Premio p = new Premio();
        p.setTorneoId(torneoId);
        p.setPosicion(posicion);
        p.setDescripcion(descripcion);
        p.setValor(valor);
        p.setEstado("PENDIENTE");
        return p;
    }
}
