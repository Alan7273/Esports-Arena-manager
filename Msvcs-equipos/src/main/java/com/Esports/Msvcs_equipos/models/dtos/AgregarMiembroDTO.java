package com.Esports.Msvcs_equipos.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AgregarMiembroDTO {
    private Long MiembroId;
    private Long usuarioId;
    private String rolDentroEquipo;
    private Long equipoId;
    private LocalDateTime fechaIngreso;
}
