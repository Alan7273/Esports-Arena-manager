package com.Esports.Msvcs_equipos.repositories;

import com.Esports.Msvcs_equipos.models.Equipos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EquiposRepository extends JpaRepository<Equipos, Long> {
    // Metodo que me permite buscar por nombre del equipo
    Optional<Equipos> findByNombreequipo(String nombreequipo);

    // Metodo que me permite buscar el estado del equipo
    Optional<Equipos> findByEstadoequipo(String estadoequipo);

    // Metodo que me permite saber si existe el nombre del equipo
    Boolean existsByNombreequipo(String nombreequipo);

}
