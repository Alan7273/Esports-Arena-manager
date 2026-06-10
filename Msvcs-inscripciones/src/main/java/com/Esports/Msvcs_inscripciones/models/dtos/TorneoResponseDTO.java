package com.Esports.Msvcs_inscripciones.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TorneoResponseDTO {
    private Long torneoId;
    private Integer cupoMaximo;
    private String estadoTorneo;
}
