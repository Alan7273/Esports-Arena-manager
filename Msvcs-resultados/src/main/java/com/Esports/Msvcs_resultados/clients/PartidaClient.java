package com.Esports.Msvcs_resultados.clients;

import com.Esports.Msvcs_resultados.models.dtos.PartidaResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvcs-partidas", url = "${msvcs.partidas.url}")
public interface PartidaClient {
    @GetMapping("/api/v1/partidas/{id}")
    PartidaResponseDTO buscarPartida(@PathVariable("id") Long id);
}
