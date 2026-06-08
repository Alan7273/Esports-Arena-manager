package com.Esports.Msvcs_partidas.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvcs-inscripciones", url = "${msvcs.inscripciones.url}")
public interface InscripcionClient {
    @GetMapping("/api/v1/inscripciones/torneo/{torneoId}/participante/{participanteId}/existe")
    Boolean existeParticipante(@PathVariable("torneoId") Long torneoId, @PathVariable("participanteId") Long participanteId);
}
