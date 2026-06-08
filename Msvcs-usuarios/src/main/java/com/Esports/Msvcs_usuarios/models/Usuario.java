package com.Esports.Msvcs_usuarios.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Long usuarioId;

    @NotBlank(message = "El campo de nombreUsuario no puede ser vacio")
    @Column(name = "nombreusuario", nullable = false)
    private String nombreusuario;

    @NotBlank(message = "El campo de nickname no puede ser vacio")
    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @NotBlank(message = "El campo de correo no puede ser vacio")
    @Email(message = "El campo de correo tiene que tener el formato de correo")
    @Column(name = "correo", nullable = false, unique = true)
    private String correo;

    @NotBlank(message = "El campo de rol no puede ser vacio")
    @Column(name = "rol", nullable = false)
    private String rol;

    @NotBlank(message = "El campo de estadoUsuario no puede ser vacio")
    @Column(name = "estadousuario", nullable = false)
    private String estadousuario;

    @NotNull(message = "El campo de fecha de registro no puede ser vacio")
    @Column(name = "fechaRegistro", nullable = false)
    private LocalDate fechaRegistro;

    @Embedded
    Audit audit = new Audit();

}
