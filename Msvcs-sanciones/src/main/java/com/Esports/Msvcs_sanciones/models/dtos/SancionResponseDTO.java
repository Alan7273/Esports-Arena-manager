package com.Esports.Msvcs_sanciones.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SancionResponseDTO {
    private Long sancionId;
    private Long usuarioId;
    private Long equipoId;
    private String motivo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estadoSancion;
    private String severidad;
}
