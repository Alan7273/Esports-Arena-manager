package com.Esports.Msvcs_inscripciones.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvcs-equipos", url = "${msvcs.equipos.url}")
public interface EquipoClient {
    @GetMapping("/api/v1/equipos/{id}/existe")
    Boolean existeEquipo(@PathVariable("id") Long id);
}
