package com.Esports.Msvcs_partidas.clients;

import org.springframework.stereotype.Component;

@Component
public class TorneoClientFallback implements TorneoClient{

    @Override
    public Boolean existeTorneo(Long id) {
        return false;
    }
}
