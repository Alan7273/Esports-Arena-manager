package com.Esports.Msvcs_notificaciones.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvcs-usuarios", url = "${msvcs.usuarios.url}")
public interface UsuarioClient {
    @GetMapping("/api/v1/usuarios/{id}/existe")
    Boolean existeUsuario(@PathVariable("id") Long id);
}
