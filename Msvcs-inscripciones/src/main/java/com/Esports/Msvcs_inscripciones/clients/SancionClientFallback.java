package com.Esports.Msvcs_inscripciones.clients;

import org.springframework.stereotype.Component;

@Component
public class SancionClientFallback implements SancionClient {

    @Override
    public Boolean tieneSancionActiva(Long usuarioId) {
        return false;
    }
}
