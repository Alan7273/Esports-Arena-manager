package com.Esports.Msvcs_resultados.models.dtos;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarResultadoDTO {

    @Schema(description = "ID de la partida", example = "20")
    private Long partidaId;

    @Schema(description = "Nuevo puntaje A", example = "3")
    @PositiveOrZero(message = "El puntaje no puede ser negativo")
    private Integer puntajeA;

    @Schema(description = "Nuevo puntaje B", example = "2")
    @PositiveOrZero(message = "El puntaje no puede ser negativo")
    private Integer puntajeB;

    @Schema(description = "Nuevo ganador", example = "10")
    private Long ganadorId;

    @Schema(description = "Nuevo estado de validación", example = "VALIDADO")
    private String estadoValidacion;

    @Schema(description = "Nueva fecha de registro", example = "2025-07-06")
    private LocalDate fechaRegistro;
}
