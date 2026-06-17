package com.Esports.Msvcs_premios.models.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarPremioDTO {

    @Schema(description = "Nuevo ID del torneo", example = "3")
    private Long torneoId;

    @Schema(description = "Nueva posición", example = "2")
    @Min(value = 1, message = "La posición mínima es 1")
    private Integer posicion;

    @Schema(description = "Nueva descripción", example = "Medalla + $200.000 CLP")
    private String descripcion;

    @Schema(description = "Nuevo valor", example = "200000.0")
    @Positive(message = "El valor debe ser mayor a 0")
    private Double valor;

    @Schema(description = "Nuevo estado", example = "ASIGNADO")
    private String estado;
}
