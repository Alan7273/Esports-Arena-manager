package com.Esports.Msvcs_sanciones.models.dtos;

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
public class SancionResponseDTO {

    @Schema(description = "ID único de la sanción", example = "3")
    private Long sancionId;

    @Schema(description = "ID del usuario sancionado", example = "7")
    private Long usuarioId;

    @Schema(description = "ID del equipo sancionado", example = "10")
    private Long equipoId;

    @Schema(description = "Motivo de la sanción", example = "Conducta antideportiva")
    private String motivo;

    @Schema(description = "Fecha de inicio", example = "2025-06-01")
    private LocalDate fechaInicio;

    @Schema(description = "Fecha de fin", example = "2025-06-15")
    private LocalDate fechaFin;

    @Schema(description = "Estado de la sanción", example = "ACTIVA")
    private String estadoSancion;

    @Schema(description = "Severidad", example = "MEDIA")
    private String severidad;
}
