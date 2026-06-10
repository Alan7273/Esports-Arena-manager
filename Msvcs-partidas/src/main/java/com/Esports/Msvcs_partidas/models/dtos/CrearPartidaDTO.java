package com.Esports.Msvcs_partidas.models.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CrearPartidaDTO {

    @NotNull(message = "El torneo es obligatorio")
    private Long torneoId;

    @NotNull(message = "El participante A es obligatorio")
    private Long participanteAId;

    @NotNull(message = "El participante B es obligatorio")
    private Long participanteBId;

    @NotNull(message = "La ronda es obligatoria")
    @Min(value = 1, message = "La ronda mínima es 1")
    private Integer ronda;

    @NotNull(message = "La fecha y hora son obligatorias")
    private LocalDateTime fechaHora;

    @NotBlank(message = "El estado de la partida no puede estar vacío")
    private String estadopartida;
}
