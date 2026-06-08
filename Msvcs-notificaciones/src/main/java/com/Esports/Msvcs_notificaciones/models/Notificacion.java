package com.Esports.Msvcs_notificaciones.models;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "notificaciones")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notificacion_id")
    private Long notificacionId;

    @NotNull(message = "El ususario_Id no puede ser nulo")
    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Nullable
    @Column(name = "equipo_id")
    private Long equipoId;

    @NotBlank(message = "El campo de tipo no puede ser vacio")
    @Column(name = "tipo", nullable = false)
    private String tipo;

    @NotBlank(message = "El campo de mensaje no puede ser vacio")
    @Column(name = "mensaje", nullable = false)
    private String mensaje;

    @NotNull(message = "la notificacion leida no puede ser nulo")
    @Column(name = "leida", nullable = false)
    private Boolean leidaNotificacion;

    @NotNull(message = "la fecha no puede ser nulo")
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Embedded
    private Audit audit = new Audit();
}
