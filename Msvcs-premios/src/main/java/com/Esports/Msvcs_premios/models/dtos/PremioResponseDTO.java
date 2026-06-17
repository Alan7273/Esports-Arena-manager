package com.Esports.Msvcs_premios.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PremioResponseDTO {

    @Schema(description = "ID único del premio", example = "6")
    private Long premioId;

    @Schema(description = "ID del torneo", example = "2")
    private Long torneoId;

    @Schema(description = "Posición premiada", example = "1")
    private Integer posicion;

    @Schema(description = "Descripción del premio", example = "Trofeo + $500.000 CLP")
    private String descripcion;

    @Schema(description = "Valor monetario", example = "500000.0")
    private Double valor;

    @Schema(description = "Estado del premio", example = "PENDIENTE")
    private String estado;
}
