package com.Esports.Msvcs_juegos.models.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
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
public class ActualizarJuegoDTO {

    @Schema(description = "Nuevo nombre del juego", example = "League of Legends")
    private String nombrejuegos;

    @Schema(description = "Nuevo género", example = "MOBA")
    private String Generojuego;

    @Schema(description = "Nueva modalidad", example = "5v5")
    private String Modalidadjuegos;

    @Schema(description = "Nuevo número de jugadores por equipo", example = "5")
    @Min(value = 1, message = "Debe haber al menos 1 jugador por equipo")
    private Integer Jugadores_por_equipo;

    @Schema(description = "Nuevo estado", example = "INACTIVO")
    private String estadojuego;

}
