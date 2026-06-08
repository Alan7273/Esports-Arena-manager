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

    private Long resultadoId;
    private Long partidaId;
    private Integer puntajeA;
    private Integer puntajeB;
    private Integer ganadorId;
    private String estadoValidacion;
    private LocalDate fechaRegistro;
}
