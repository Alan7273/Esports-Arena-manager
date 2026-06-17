package com.Esports.Msvcs_torneos.models.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
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
public class CrearTorneoDTO {

    @Schema(description = "Nombre del torneo", example = "Copa Verano 2025")
    @NotBlank(message = "El nombre del torneo no puede estar vacio")
    private String nombretorneo;

    @Schema(description = "ID del juego asociado al torneo", example = "1")
    @NotNull(message = "El juego es obligatorio")
    private Long juegoId;

    @Schema(description = "Fecha de inicio del torneo", example = "2025-07-01")
    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    @Schema(description = "Fecha de fin del torneo", example = "2025-07-15")
    @NotNull(message = "La fecha de fin es obligatorio")
    private LocalDate fechaFin;

    @Schema(description = "Cupo máximo de participantes", example = "16")
    @NotNull(message = "El cupo maximo es obligatorio")
    @Min(value = 2, message = "El cupo minimo es 2 participantes")
    private Integer cupoMaximo;

    @Schema(description = "Modalidad del torneo", example = "ELIMINACION_DIRECTA", allowableValues = {"ELIMINACION_DIRECTA", "GRUPOS", "ROUND_ROBIN"})
    @NotBlank(message = "La modalidad no puede estar vacia")
    private String modalidadTorneo;

    @Schema(description = "Estado del torneo", example = "ABIERTO", allowableValues = {"ABIERTO", "EN_CURSO", "CERRADO", "CANCELADO"})
    @NotBlank(message = "El estado no puede ")
    private String estadoTorneo;
}
