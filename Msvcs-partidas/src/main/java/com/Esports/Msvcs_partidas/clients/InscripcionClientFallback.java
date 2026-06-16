package com.Esports.Msvcs_partidas.clients;

import org.springframework.stereotype.Component;

@Component
public class InscripcionClientFallback implements InscripcionClient{

    @Override
    public Boolean existeParticipante(Long torneoId, Long participanteId) {
        return false;
    }
}
