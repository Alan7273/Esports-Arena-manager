package com.Esports.Msvcs_inscripciones.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvcs-sanciones", url = "${msvcs.sanciones.url}")
public interface SancionClient {
    @GetMapping("/api/v1/sanciones/validar/{usuarioId}")
    Boolean tieneSancionActiva(@PathVariable("usuarioId") Long usuarioId);
}
