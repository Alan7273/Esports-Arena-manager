package com.Esports.Msvcs_sanciones.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ActualizarSancionDTO {
    private String motivo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estadoSancion;
    private String severidad;
}
