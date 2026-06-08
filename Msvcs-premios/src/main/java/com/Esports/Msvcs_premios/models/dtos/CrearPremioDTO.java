package com.Esports.Msvcs_premios.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CrearPremioDTO {
    private Long premioId;
    private Long torneoId;
    private Integer posicion;
    private String descripcion;
    private Double valor;
    private String estado;
}
