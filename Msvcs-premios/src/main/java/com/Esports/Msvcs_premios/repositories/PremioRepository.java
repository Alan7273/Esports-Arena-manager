package com.Esports.Msvcs_premios.repositories;

import com.Esports.Msvcs_premios.models.Premio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PremioRepository extends JpaRepository<Premio, Long> {
    // Metodo que me permite buscar el torneo por el ID
    List<Premio> findByTorneoId(Long torneoId);
    // Metodo que me permite encontrar la posicion
    Optional<Premio> findByPosicion(Integer posicion);
    // Metodo que me permite saber si existe el torneo y la posicion
    Boolean existsByTorneoIdAndPosicion(Long torneoId, Integer posicion);
}
