package com.Esports.Msvcs_sanciones.clients;

import org.springframework.stereotype.Component;

/**
 * Fallback que se ejecuta cuando Msvcs-usuarios no responde (timeout, caida del
 * servicio, error de red, etc). En vez de propagar la excepcion y devolver un
 * error 500 al cliente final, se retorna un valor por defecto.
 */
@Component
public class UsuarioClientFallback implements UsuarioClient {

    @Override
    public Boolean existeUsuario(Long id) {
        return false;
    }
}
