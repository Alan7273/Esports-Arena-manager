package com.Esports.Msvcs_juegos.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JuegoResponseDTO {
    private Long juegosId;
    private String nombrejuegos;
    private String Generojuego;
    private String Modalidadjuegos;
    private Integer Jugadores_por_equipo;
    private String estadojuego;
}
