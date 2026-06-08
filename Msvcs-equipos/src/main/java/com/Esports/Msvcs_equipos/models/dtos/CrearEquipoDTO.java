package com.Esports.Msvcs_equipos.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CrearEquipoDTO {
    private Long equipoId;
    private String nombreequipo;
    private Long capitanId;
    private Long juegoprincipalId;
    private String estadoequipo;

}
