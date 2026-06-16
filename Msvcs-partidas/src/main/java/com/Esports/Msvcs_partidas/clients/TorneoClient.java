package com.Esports.Msvcs_partidas.clients;

import com.Esports.Msvcs_partidas.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "msvcs-torneos",
        url = "${msvcs.torneos.url}",
        configuration = FeignConfig.class,
        fallback = TorneoClientFallback.class
)
public interface TorneoClient {
    @GetMapping("/api/v1/torneos/{id}/existe")
    Boolean existeTorneo(@PathVariable("id") Long id);
}
