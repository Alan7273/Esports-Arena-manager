package com.Esports.Msvcs_torneos.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CrearTorneoDTO {
    private Long torneoId;
    private String nombretorneo;
    private Long juegoId;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Integer cupoMaximo;
    private String modalidadTorneo;
    private String estadoTorneo;
}
