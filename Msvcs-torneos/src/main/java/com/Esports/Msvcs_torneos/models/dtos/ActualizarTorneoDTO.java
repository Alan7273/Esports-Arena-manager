package com.Esports.Msvcs_torneos.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarTorneoDTO {
    private String nombretorneo;
    private Long juegoId;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Integer cupoMaximo;
    private String modalidadTorneo;
    private String estadoTorneo;
}
