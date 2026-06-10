package com.Esports.Msvcs_usuarios.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "El nombre de usuario no puede estar vacio")
    private String nombreusuario;

    @NotBlank(message = "El nickname no puede estar vacio")
    private String nickname;

    @NotBlank(message = "El correo no puede estar vacio")
    @Email(message = "El correo debe tener un formato valido")
    private String correo;

    @NotBlank(message = "El rol no puede estar vacio")
    private String rol;

    @NotBlank(message = "El estado no puede estar vacio")
    private String estadousuario;

    @NotNull(message = "La fecha de registro no puede estar vacia")
    private LocalDate fechaRegistro;
}
