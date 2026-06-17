package com.Esports.Msvcs_sanciones.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CrearSancionDTO {

    @Schema(description = "ID del usuario sancionado (null si es equipo)", example = "7")
    private Long usuarioId;

    @Schema(description = "ID del equipo sancionado (null si es usuario)", example = "10")
    private Long equipoId;

    @Schema(description = "Motivo de la sanción", example = "Conducta antideportiva")
    @NotBlank(message = "El motivo no puede estar vacío")
    private String motivo;

    @Schema(description = "Fecha de inicio de la sanción", example = "2025-06-01")
    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    @Schema(description = "Fecha de fin de la sanción (null si es indefinida)", example = "2025-06-15")
    private LocalDate fechaFin;

    @Schema(description = "Estado de la sanción", example = "ACTIVA", allowableValues = {"ACTIVA", "CERRADA"})
    @NotBlank(message = "El estado de la sanción no puede estar vacío")
    private String estadoSancion;

    @Schema(description = "Severidad de la sanción", example = "MEDIA", allowableValues = {"BAJA", "MEDIA", "ALTA"})
    @NotBlank(message = "La severidad no puede estar vacía")
    private String severidad;
}
