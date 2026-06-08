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
public class InscripcionResponseDTO {
    private Long inscripcionId;
    private Long torneoId;
    private Long equipoId;
    private Long usuarioId;
    private String NombreJugador;
    private String tipoParticipante;
    private String estado;
    private LocalDate fechaInscripcion;
}
