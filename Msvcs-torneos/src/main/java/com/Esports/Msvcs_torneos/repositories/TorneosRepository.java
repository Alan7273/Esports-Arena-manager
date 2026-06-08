package com.Esports.Msvcs_torneos.repositories;

import com.Esports.Msvcs_torneos.models.Torneo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TorneosRepository extends JpaRepository<Torneo, Long> {
    // Metodo que me permite revisar el estado del torneo
    List<Torneo> findByEstadoTorneo(String estadoTorneo);

    // Metodo que me permite buscar el juego por ID
    List<Torneo> findByJuegoId(Long juegoId);

    // Metodo que me permite buscar la fecha de inicio
    List<Torneo> findByFechaInicioBetween(LocalDate fechaInicio, LocalDate fechaFin);
}
