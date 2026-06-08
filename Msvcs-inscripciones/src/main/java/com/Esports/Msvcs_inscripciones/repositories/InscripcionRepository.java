package com.Esports.Msvcs_inscripciones.repositories;

import com.Esports.Msvcs_inscripciones.models.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
    // Metodo que me permite buscar el torneo por el ID
    List<Inscripcion> findByTorneoId(Long torneoId);

    // Metodo que me permite buscar equipo por el ID
    List<Inscripcion> findByEquipoId(Long equipoId);

    // Metodo que me permite buscar ususario por ID
    List<Inscripcion> findByUsuarioId(Long usuarioId);

    // Metodo que me permite revisar si el ID del torneo y el usuario existen
    Boolean existsByTorneoIdAndUsuarioId(Long torneoId, Long usuarioId);

    // Metodo que me permite revisar si el torneo y el usuario existe
    Boolean existsByTorneoIdAndEquipoId(Long torneoId, Long equipoId);
}
