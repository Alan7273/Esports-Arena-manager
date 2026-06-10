package com.Esports.Msvcs_premios.models.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CrearPremioDTO {

    @NotNull(message = "El torneo es obligatorio")
    private Long torneoId;

    @NotNull(message = "La posición es obligatoria")
    @Min(value = 1, message = "La posición mínima es 1")
    private Integer posicion;

    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcion;

    @NotNull(message = "El valor es obligatorio")
    @Positive(message = "El valor debe ser mayor a 0")
    private Double valor;

    @NotBlank(message = "El estado no puede estar vacío")
    private String estado;
}
