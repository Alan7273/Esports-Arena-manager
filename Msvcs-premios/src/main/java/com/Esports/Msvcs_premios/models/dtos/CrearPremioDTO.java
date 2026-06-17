package com.Esports.Msvcs_premios.models.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CrearPremioDTO {

    @Schema(description = "ID del torneo al que pertenece el premio", example = "2")
    @NotNull(message = "El torneo es obligatorio")
    private Long torneoId;

    @Schema(description = "Posición que recibe el premio", example = "1")
    @NotNull(message = "La posición es obligatoria")
    @Min(value = 1, message = "La posición mínima es 1")
    private Integer posicion;

    @Schema(description = "Descripción del premio", example = "Trofeo + $500.000 CLP")
    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcion;

    @Schema(description = "Valor monetario del premio en CLP", example = "500000.0")
    @NotNull(message = "El valor es obligatorio")
    @Positive(message = "El valor debe ser mayor a 0")
    private Double valor;

    @Schema(description = "Estado del premio", example = "PENDIENTE", allowableValues = {"PENDIENTE", "ASIGNADO"})
    @NotBlank(message = "El estado no puede estar vacío")
    private String estado;
}
