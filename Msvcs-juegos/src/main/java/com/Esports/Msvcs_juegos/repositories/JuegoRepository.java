package com.Esports.Msvcs_juegos.repositories;

import com.Esports.Msvcs_juegos.models.Juegos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JuegoRepository extends JpaRepository<Juegos, Long> {
    // Metodo que me permite encontrar nombre de cada juego
    Optional<Juegos> findByNombrejuegos(String nombrejuegos);

    // Metodo que me permite encontrar el estado del juego
    List<Juegos> findByEstadojuego(String estadojuego);

    // Metodo que me permite revisar si existe el nombre del juego
    Boolean existsByNombrejuegos(String nombrejuegos);
}
