package com.Esports.Msvcs_juegos.models.dtos;

import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "")
    private String Modalidadjuegos;
    private Integer Jugadores_por_equipo;
    private String estadojuego;

}
