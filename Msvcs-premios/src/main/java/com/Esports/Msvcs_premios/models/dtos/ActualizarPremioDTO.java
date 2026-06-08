package com.Esports.Msvcs_premios.models.dtos;

import jakarta.validation.constraints.NotBlank;
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

    private Long premiId;
    private Long torneoId;
    private Integer posicion;
    private String descripcion;
    private Double valor;
    private String estado;
}
