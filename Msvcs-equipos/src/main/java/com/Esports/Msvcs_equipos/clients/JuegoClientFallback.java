package com.Esports.Msvcs_equipos.clients;

import org.springframework.stereotype.Component;

@Component
public class JuegoClientFallback implements JuegoClient{

    @Override
    public Boolean existeJuego(Long id) {
        return false;
    }
}
