package com.Esports.Msvcs_premios.models.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarPremioDTO {

    private Long torneoId;

    @Min(value = 1, message = "La posición mínima es 1")
    private Integer posicion;

    private String descripcion;

    @Positive(message = "El valor debe ser mayor a 0")
    private Double valor;

    private String estado;
}
