package com.Esports.Msvcs_equipos.repositories;

import com.Esports.Msvcs_equipos.models.Miembro_equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MiembroEquipoRepository extends JpaRepository<Miembro_equipo, Long> {
    // Metodo que me permite buscar a un equipo por ID
    List<Miembro_equipo> findByEquipoId(Long equipoId);

    // Metodo que me permite buscar a un usuario por ID
    List<Miembro_equipo> findByUsuarioId(Long usuarioId);

    // Metodo que me permite buscar el equipo y el usuario
    Optional<Miembro_equipo> findByEquipoIdAndUsuarioId (Long equipoId, Long usuarioId);

    // Metodo que me permite saber si existe el equipo y el usuario
    boolean existsByEquipoIdAndUsuarioId (Long equipoId, Long usuarioId);
}
