package com.Esports.Msvcs_premios.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PremioResponseDTO {
    private Long premioId;
    private Long torneoId;
    private Integer posicion;
    private String descripcion;
    private Double valor;
    private String estado;
}
