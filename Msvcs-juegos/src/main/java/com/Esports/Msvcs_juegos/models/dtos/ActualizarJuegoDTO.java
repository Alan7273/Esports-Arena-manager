package com.Esports.Msvcs_juegos.models.dtos;

import jakarta.persistence.Column;
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

    private Long juegosId;
    private String nombrejuegos;
    private String Generojuego;
    private String Modalidadjuegos;
    private Integer Jugadores_por_equipo;
    private String estadojuego;

}
