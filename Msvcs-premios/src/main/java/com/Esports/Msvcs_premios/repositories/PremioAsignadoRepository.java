package com.Esports.Msvcs_premios.repositories;

import com.Esports.Msvcs_premios.models.Premio;
import com.Esports.Msvcs_premios.models.Premio_asignado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PremioAsignadoRepository extends JpaRepository<Premio_asignado, Long> {
    // Metodo que me permite buscar el participante por ID
    List<Premio_asignado> findByParticipanteId(Long participanteId);
    // Metodo que me permite buscar el premio por el ID
    List<Premio_asignado> findByPremioId(Long premioId);
}
