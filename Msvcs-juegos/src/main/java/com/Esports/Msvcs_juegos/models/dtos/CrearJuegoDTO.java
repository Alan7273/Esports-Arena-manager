package com.Esports.Msvcs_juegos.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CrearJuegoDTO {
    private Long juegosId;
    private String nombrejuegos;
    private String Generojuego;
    private String Modalidadjuegos;
    private Integer Jugadores_por_equipo;
    private String estadojuego;

}
