package com.Esports.Msvcs_usuarios.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UsuarioResponseDTO {

    @Schema(description = "ID único del usuario", example = "7")
    private Long usuarioId;

    @Schema(description = "Nombre completo del usuario", example = "Carlos Pérez")
    private String nombreusuario;

    @Schema(description = "Apodo del usuario", example = "cperez99")
    private String nickname;

    @Schema(description = "Correo electrónico", example = "carlos@email.com")
    private String correo;

    @Schema(description = "Rol del usuario", example = "JUGADOR")
    private String rol;

    @Schema(description = "Estado del usuario", example = "ACTIVO")
    private String estadousuario;

    @Schema(description = "Fecha de registro", example = "2025-01-15")
    private LocalDate fechaRegistro;
}
