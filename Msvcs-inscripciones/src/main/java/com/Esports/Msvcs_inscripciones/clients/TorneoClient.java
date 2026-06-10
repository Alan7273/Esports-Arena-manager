package com.Esports.Msvcs_inscripciones.clients;

import com.Esports.Msvcs_inscripciones.models.dtos.TorneoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvcs-torneos", url = "${msvcs.torneos.url}")
public interface TorneoClient {
    @GetMapping("/api/v1/torneos/{id}/existe")
    Boolean existeTorneo(@PathVariable("id") Long id);

    @GetMapping("/api/v1/torneos/{id}")
    TorneoResponseDTO buscarTorneo(@PathVariable("id") Long id);
}
