package com.Esports.Msvcs_torneos.models.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TorneoResponseDTO {

    @Schema(description = "ID único del torneo", example = "2")
    private Long torneoId;

    @Schema(description = "Nombre del torneo", example = "Copa Verano 2025")
    private String nombretorneo;

    @Schema(description = "ID del juego", example = "1")
    private Long juegoId;

    @Schema(description = "Fecha de inicio", example = "2025-07-01")
    private LocalDate fechaInicio;

    @Schema(description = "Fecha de fin", example = "2025-07-15")
    private LocalDate fechaFin;

    @Schema(description = "Cupo máximo de participantes", example = "16")
    private Integer cupoMaximo;

    @Schema(description = "Estado del torneo", example = "ABIERTO")
    private String estadoTorneo;

    @Schema(description = "Modalidad del torneo", example = "ELIMINACION_DIRECTA")
    private String modalidadTorneo;
}
