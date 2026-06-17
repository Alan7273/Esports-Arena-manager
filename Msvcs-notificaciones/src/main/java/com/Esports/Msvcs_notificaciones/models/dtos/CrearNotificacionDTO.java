package com.Esports.Msvcs_notificaciones.models.dtos;

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
public class CrearNotificacionDTO {

    @Schema(description = "ID del usuario destinatario", example = "7")
    @NotNull(message = "El usuario destinatario es obligatorio")
    private Long usuarioId;

    @Schema(description = "Contenido del mensaje de la notificación", example = "Has sido agregado al equipo Team Phantom")
    @NotBlank(message = "El mensaje no puede estar vacío")
    private String mensaje;

    @Schema(description = "Tipo de notificación", example = "EQUIPO", allowableValues = {"EQUIPO", "TORNEO", "SANCION", "SISTEMA"})
    @NotBlank(message = "El tipo de notificación no puede estar vacío")
    private String tipo;

    @Schema(description = "ID del equipo relacionado (opcional)", example = "10")
    private Long equipoId;
}
