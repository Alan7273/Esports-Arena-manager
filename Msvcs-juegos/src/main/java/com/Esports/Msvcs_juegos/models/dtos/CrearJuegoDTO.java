package com.Esports.Msvcs_juegos.models.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CrearJuegoDTO {

    @Schema(description = "Nombre del juego", example = "League of Legends")
    @NotBlank(message = "El nombre del juego no puede estar vacio")
    private String nombrejuegos;

    @Schema(description = "Género del juego", example = "MOBA")
    @NotBlank(message = "El genero no puede estar vacio")
    private String Generojuego;

    @Schema(description = "Modalidad del juego", example = "5v5")
    @NotBlank(message = "La modalidad no puede estar vacia")
    private String Modalidadjuegos;

    @Schema(description = "Número de jugadores por equipo", example = "5")
    @NotNull(message = "Los jugadores por equipo no pueden estar vacios")
    @Min(value = 1, message = "Debe haber al menos 1 jugador por equipo")
    private Integer Jugadores_por_equipo;

    @Schema(description = "Estado del juego", example = "ACTIVO", allowableValues = {"ACTIVO", "INACTIVO"})
    @NotBlank(message = "El estado del juego no puede estar vacio")
    private String estadojuego;

}
