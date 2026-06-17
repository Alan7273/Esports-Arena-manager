package com.Esports.Msvcs_equipos.models.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CrearEquipoDTO {

    @Schema(description = "Nombre del equipo", example = "Team Phantom")
    @NotBlank(message = "El nombre del equipo no puede estar vacío")
    private String nombreequipo;

    @Schema(description = "ID del usuario que actúa como capitán", example = "3")
    @NotNull(message = "El capitán es obligatorio")
    private Long capitanId;

    @Schema(description = "ID del juego principal del equipo", example = "1")
    @NotNull(message = "El juego principal es obligatorio")
    private Long juegoprincipalId;

    @Schema(description = "Estado del equipo", example = "ACTIVO", allowableValues = {"ACTIVO", "INACTIVO"})
    @NotBlank(message = "El estado del equipo no puede estar vacío")
    private String estadoequipo;
}
