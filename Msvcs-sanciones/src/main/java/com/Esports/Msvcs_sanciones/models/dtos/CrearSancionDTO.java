package com.Esports.Msvcs_sanciones.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CrearSancionDTO {
    private Long usuarioId;
    private Long equipoId;

    @NotBlank(message = "El motivo no puede estar vacío")
    private String motivo;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    @NotBlank(message = "El estado de la sanción no puede estar vacío")
    private String estadoSancion;

    @NotBlank(message = "La severidad no puede estar vacía")
    private String severidad;
}
