package com.Esports.Msvcs_torneos.models.dtos;

import jakarta.validation.constraints.Min;
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
public class CrearTorneoDTO {

    @NotBlank(message = "El nombre del torneo no puede estar vacio")
    private String nombretorneo;

    @NotNull(message = "El juego es obligatorio")
    private Long juegoId;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin es obligatorio")
    private LocalDate fechaFin;

    @NotNull(message = "El cupo maximo es obligatorio")
    @Min(value = 2, message = "El cupo minimo es 2 participantes")
    private Integer cupoMaximo;

    @NotBlank(message = "La modalidad no puede estar vacia")
    private String modalidadTorneo;

    @NotBlank(message = "El estado no puede ")
    private String estadoTorneo;
}
