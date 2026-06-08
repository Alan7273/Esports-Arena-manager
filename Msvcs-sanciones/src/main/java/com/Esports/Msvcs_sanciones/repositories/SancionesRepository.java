package com.Esports.Msvcs_sanciones.repositories;

import com.Esports.Msvcs_sanciones.models.Sancion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SancionesRepository extends JpaRepository<Sancion, Long> {
    // Metodo que me permite buscar por ID al ususario
    List<Sancion> findByUsuarioId(Long usuarioId);
    // Metodo que me permite revisar el estado de la sancion
    List<Sancion> findByEstadoSancion(String estadoSancion);
    // Metodo que me permite saber si existe el estado y el usuario
    Boolean existsByUsuarioIdAndEstadoSancion(Long usuarioId, String estadoSancion);
}
