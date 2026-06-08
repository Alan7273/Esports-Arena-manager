package com.Esports.Msvcs_inscripciones.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CrearInscripcionDTO {
    private Long inscripcionId;
    private Long torneoId;
    private Long equipoId;
    private Long usuarioId;
    private String tipoParticipante;
    private String NombreJugador;
    private String estado;
    private LocalDate fechaInscripcion;
}
