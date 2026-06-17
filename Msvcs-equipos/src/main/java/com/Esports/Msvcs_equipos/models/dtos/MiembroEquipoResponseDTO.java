package com.Esports.Msvcs_equipos.models.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MiembroEquipoResponseDTO {

    @Schema(description = "ID único del miembro en el equipo", example = "5")
    private Long MiembroId;

    @Schema(description = "ID del equipo al que pertenece", example = "10")
    private Long equipoId;

    @Schema(description = "ID del usuario miembro", example = "7")
    private Long usuarioId;

    @Schema(description = "Rol del miembro dentro del equipo", example = "Support")
    private String rolDentroEquipo;

    @Schema(description = "Fecha y hora de ingreso al equipo", example = "2025-06-01T14:30:00")
    private LocalDateTime fechaIngreso;
}
