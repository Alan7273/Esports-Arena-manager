package com.Esports.Msvcs_usuarios.models.dtos;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarUsuarioDTO {

    @Schema(description = "Nuevo nombre completo", example = "Carlos Andrés Pérez")
    private String nombreusuario;

    @Schema(description = "Nuevo nickname", example = "carlitos99")
    private String nickname;

    @Schema(description = "Nuevo correo", example = "carlitos@email.com")
    @Email(message = "El correo debe tener un formato valido")
    private String correo;

    @Schema(description = "Nuevo rol", example = "ORGANIZADOR")
    private String rol;

    @Schema(description = "Nuevo estado", example = "INACTIVO")
    private String estadousuario;

    @Schema(description = "Nueva fecha de registro", example = "2025-03-20")
    private LocalDate fechaRegistro;
}
