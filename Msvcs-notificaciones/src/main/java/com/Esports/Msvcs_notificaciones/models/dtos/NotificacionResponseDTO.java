package com.Esports.Msvcs_notificaciones.models.dtos;

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
public class NotificacionResponseDTO {

    @Schema(description = "ID único de la notificación", example = "30")
    private Long notificacionId;

    @Schema(description = "ID del usuario destinatario", example = "7")
    private Long usuarioId;

    @Schema(description = "ID del equipo relacionado", example = "10")
    private Long equipoId;

    @Schema(description = "Tipo de notificación", example = "EQUIPO")
    private String tipo;

    @Schema(description = "Contenido del mensaje", example = "Has sido agregado al equipo Team Phantom")
    private String mensaje;

    @Schema(description = "Indica si la notificación fue leída", example = "false")
    private Boolean leidaNotificacion;

    @Schema(description = "Fecha de la notificación", example = "2025-06-10")
    private LocalDate fecha;
}
