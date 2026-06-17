package com.Esports.Msvcs_juegos.models.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JuegoResponseDTO {

    @Schema(description = "ID único del juego", example = "1")
    private Long juegosId;

    @Schema(description = "Nombre del juego", example = "League of Legends")
    private String nombrejuegos;

    @Schema(description = "Género del juego", example = "MOBA")
    private String Generojuego;

    @Schema(description = "Modalidad del juego", example = "5v5")
    private String Modalidadjuegos;

    @Schema(description = "Jugadores por equipo", example = "5")
    private Integer Jugadores_por_equipo;

    @Schema(description = "Estado del juego", example = "ACTIVO")
    private String estadojuego;
}
