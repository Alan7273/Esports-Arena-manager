package com.Esports.Msvcs_equipos.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EquipoResponseDTO {
    private Long equipoId;
    private String nombreequipo;
    private Long capitanId;
    private Long juegoprincipalId;
    private String estadoequipo;
}
