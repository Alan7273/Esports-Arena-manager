package com.Esports.Msvcs_equipos.models.dtos;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Long usuarioId;
    private String nombre;
    private String correo;
}
