package com.Esports.Msvcs_equipos.clients;

import org.springframework.stereotype.Component;

@Component
public class UsuarioClientFallback implements UsuarioClient{

    @Override
    public Boolean existeUsuario(Long id) {
        return false;
    }
}
