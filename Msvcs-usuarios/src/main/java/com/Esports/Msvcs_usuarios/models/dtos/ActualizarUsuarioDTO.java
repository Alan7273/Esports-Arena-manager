package com.Esports.Msvcs_usuarios.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarUsuarioDTO {
    private String nombreusuario;
    private String nickname;
    private String correo;
    private String rol;
    private String estadousuario;
    private LocalDate fechaRegistro;
}
