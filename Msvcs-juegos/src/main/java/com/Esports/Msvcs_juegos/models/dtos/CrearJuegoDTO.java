package com.Esports.Msvcs_juegos.models.dtos;

import jakarta.validation.constraints.Min;
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
public class CrearJuegoDTO {

    @NotBlank(message = "El nombre del juego no puede estar vacio")
    private String nombrejuegos;

    @NotBlank(message = "El genero no puede estar vacio")
    private String Generojuego;

    @NotBlank(message = "La modalidad no puede estar vacia")
    private String Modalidadjuegos;

    @NotNull(message = "Los jugadores por equipo no pueden estar vacios")
    @Min(value = 1, message = "Debe haber al menos 1 jugador por equipo")
    private Integer Jugadores_por_equipo;

    @NotBlank(message = "El estado del juego no puede estar vacio")
    private String estadojuego;

}
