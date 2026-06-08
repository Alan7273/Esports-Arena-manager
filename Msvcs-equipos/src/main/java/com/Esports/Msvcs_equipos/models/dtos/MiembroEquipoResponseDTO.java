package com.Esports.Msvcs_equipos.models.dtos;

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
    private Long MiembroId;
    private Long equipoId;
    private Long usuarioId;
    private String rolDentroEquipo;
    private LocalDateTime fechaIngreso;
}
