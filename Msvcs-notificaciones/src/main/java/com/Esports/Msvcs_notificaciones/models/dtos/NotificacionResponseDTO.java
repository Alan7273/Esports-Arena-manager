package com.Esports.Msvcs_notificaciones.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionResponseDTO {
    private Long notificacionId;
    private Long usuarioId;
    private Long equipoId;
    private String tipo;
    private String mensaje;
    private Boolean leidaNotificacion;
    private LocalDate fecha;
}
