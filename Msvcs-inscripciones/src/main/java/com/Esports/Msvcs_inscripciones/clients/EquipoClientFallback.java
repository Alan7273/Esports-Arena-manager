package com.Esports.Msvcs_inscripciones.clients;

import org.springframework.stereotype.Component;

@Component
public class EquipoClientFallback implements EquipoClient {

    @Override
    public Boolean existeEquipo(Long id) {
        return false;
    }
}
