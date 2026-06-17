package com.Esports.Msvcs_resultados.models.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "ID de la partida cuyo resultado se registra", example = "20")
    @NotNull(message = "La partida es obligatoria")
    private Long partidaId;

    @Schema(description = "ID del participante ganador", example = "10")
    @NotNull(message = "El ganador es obligatorio")
    private Long ganadorId;

    @Schema(description = "Puntaje del participante A", example = "3")
    @NotNull(message = "El puntaje A es obligatorio")
    @PositiveOrZero(message = "El puntaje no puede ser negativo")
    private Integer puntajeA;

    @Schema(description = "Puntaje del participante B", example = "1")
    @NotNull(message = "El puntaje B es obligatorio")
    @PositiveOrZero(message = "El puntaje no puede ser negativo")
    private Integer puntajeB;

    @Schema(description = "Estado de validación del resultado", example = "PENDIENTE", allowableValues = {"PENDIENTE", "VALIDADO", "ANULADO"})
    @NotBlank(message = "El estado de validación no puede estar vacío")
    private String estadoValidacion;

    @Schema(description = "Fecha de registro del resultado", example = "2025-07-05")
    @NotNull(message = "La fecha de registro es obligatoria")
    private LocalDate fechaRegistro;
}
