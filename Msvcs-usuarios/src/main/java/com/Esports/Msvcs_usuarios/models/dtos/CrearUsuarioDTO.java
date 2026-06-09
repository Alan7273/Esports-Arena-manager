package com.Esports.Msvcs_usuarios.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CrearUsuarioDTO {
    private String nombreusuario;
    private String nickname;
    private String correo;
    private String rol;
    private String estadousuario;
    private LocalDate fechaRegistro;
}
