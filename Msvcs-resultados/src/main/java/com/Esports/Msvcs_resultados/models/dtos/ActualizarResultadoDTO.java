package com.Esports.Msvcs_resultados.models.dtos;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarResultadoDTO {


    private Long partidaId;

    @PositiveOrZero(message = "El puntaje no puede ser negativo")
    private Integer puntajeA;

    @PositiveOrZero(message = "El puntaje no puede ser negativo")
    private Integer puntajeB;

    private Long ganadorId;

    private String estadoValidacion;

    private LocalDate fechaRegistro;
}
