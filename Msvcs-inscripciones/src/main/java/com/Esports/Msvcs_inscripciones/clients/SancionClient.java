package com.Esports.Msvcs_inscripciones.clients;

import com.Esports.Msvcs_inscripciones.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "msvcs-sanciones",
        configuration = FeignConfig.class,
        fallback = SancionClientFallback.class)
public interface SancionClient {
    @GetMapping("/api/v1/sanciones/validar/{usuarioId}")
    Boolean tieneSancionActiva(@PathVariable("usuarioId") Long usuarioId);
}
