package com.Esports.Msvcs_torneos.models.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarTorneoDTO {

    @Schema(description = "Nuevo nombre del torneo", example = "Copa Invierno 2025")
    private String nombretorneo;

    @Schema(description = "Nuevo ID del juego", example = "2")
    private Long juegoId;

    @Schema(description = "Nueva fecha de inicio", example = "2025-08-01")
    private LocalDate fechaInicio;

    @Schema(description = "Nueva fecha de fin", example = "2025-08-15")
    private LocalDate fechaFin;

    @Schema(description = "Nuevo cupo máximo", example = "32")
    @Min(value = 2, message = "El cupo minimo es 2 participantes")
    private Integer cupoMaximo;

    @Schema(description = "Nueva modalidad", example = "ROUND_ROBIN")
    private String modalidadTorneo;

    @Schema(description = "Nuevo estado", example = "EN_CURSO")
    private String estadoTorneo;
}
