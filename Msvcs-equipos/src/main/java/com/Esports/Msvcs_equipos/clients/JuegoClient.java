package com.Esports.Msvcs_equipos.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvcs-juegos", url = "${msvcs.juegos.url}")
public interface JuegoClient {
    @GetMapping("/api/v1/juegos/{id}/existe")
    Boolean existeJuego(@PathVariable("id") Long id);
}
