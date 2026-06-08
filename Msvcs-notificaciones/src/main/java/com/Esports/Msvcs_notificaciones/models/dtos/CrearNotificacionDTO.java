package com.Esports.Msvcs_notificaciones.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CrearNotificacionDTO {
    private Long notificacionId;
    private Long usuarioId;
    private String mensaje;
    private String tipo;
    private Long equipoId;
    private Boolean leidaNotificacion;
    private LocalDate fecha;
}
