package com.Esports.Msvcs_equipos.models.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EquipoResponseDTO {

    @Schema(description = "ID único del equipo", example = "10")
    private Long equipoId;

    @Schema(description = "Nombre del equipo", example = "Team Phantom")
    private String nombreequipo;

    @Schema(description = "ID del capitán del equipo", example = "3")
    private Long capitanId;

    @Schema(description = "ID del juego principal", example = "1")
    private Long juegoprincipalId;

    @Schema(description = "Estado actual del equipo", example = "ACTIVO")
    private String estadoequipo;
}
