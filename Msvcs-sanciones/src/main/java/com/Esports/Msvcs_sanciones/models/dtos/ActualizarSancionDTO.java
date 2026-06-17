package com.Esports.Msvcs_sanciones.models.dtos;

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
public class ActualizarSancionDTO {

    @Schema(description = "Nuevo motivo", example = "Uso de software no autorizado")
    private String motivo;

    @Schema(description = "Nueva fecha de inicio", example = "2025-06-05")
    private LocalDate fechaInicio;

    @Schema(description = "Nueva fecha de fin", example = "2025-06-20")
    private LocalDate fechaFin;

    @Schema(description = "Nuevo estado", example = "CERRADA")
    private String estadoSancion;

    @Schema(description = "Nueva severidad", example = "ALTA")
    private String severidad;
}
