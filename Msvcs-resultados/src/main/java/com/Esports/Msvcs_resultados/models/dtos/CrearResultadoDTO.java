package com.Esports.Msvcs_resultados.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CrearResultadoDTO {

    @NotNull(message = "La partida es obligatoria")
    private Long partidaId;

    @NotNull(message = "El ganador es obligatorio")
    private Long ganadorId;

    @NotNull(message = "El puntaje A es obligatorio")
    @PositiveOrZero(message = "El puntaje no puede ser negativo")
    private Integer puntajeA;

    @NotNull(message = "El puntaje B es obligatorio")
    @PositiveOrZero(message = "El puntaje no puede ser negativo")
    private Integer puntajeB;

    @NotBlank(message = "El estado de validación no puede estar vacío")
    private String estadoValidacion;

    @NotNull(message = "La fecha de registro es obligatoria")
    private LocalDate fechaRegistro;
}
