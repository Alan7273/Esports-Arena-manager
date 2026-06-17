package com.Esports.Msvcs_usuarios.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CrearUsuarioDTO {

    @Schema(description = "Nombre completo del usuario", example = "Carlos Pérez")
    @NotBlank(message = "El nombre de usuario no puede estar vacio")
    private String nombreusuario;

    @Schema(description = "Apodo o alias del usuario", example = "cperez99")
    @NotBlank(message = "El nickname no puede estar vacio")
    private String nickname;

    @Schema(description = "Correo electrónico del usuario", example = "carlos@email.com")
    @NotBlank(message = "El correo no puede estar vacio")
    @Email(message = "El correo debe tener un formato valido")
    private String correo;

    @Schema(description = "Rol del usuario en el sistema", example = "JUGADOR", allowableValues = {"JUGADOR", "ORGANIZADOR", "ADMIN"})
    @NotBlank(message = "El rol no puede estar vacio")
    private String rol;

    @Schema(description = "Estado del usuario", example = "ACTIVO", allowableValues = {"ACTIVO", "INACTIVO"})
    @NotBlank(message = "El estado no puede estar vacio")
    private String estadousuario;

    @Schema(description = "Fecha de registro del usuario", example = "2025-01-15")
    @NotNull(message = "La fecha de registro no puede estar vacia")
    private LocalDate fechaRegistro;
}
