package com.Esports.Msvcs_equipos.models.dtos;

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

    @NotBlank(message = "El nombre del equipo no puede estar vacío")
    private String nombreequipo;

    @NotNull(message = "El capitán es obligatorio")
    private Long capitanId;

    @NotNull(message = "El juego principal es obligatorio")
    private Long juegoprincipalId;

    @NotBlank(message = "El estado del equipo no puede estar vacío")
    private String estadoequipo;
}
