package com.Esports.Msvcs_partidas.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "partidas")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "partidaId")
    private Long partidaId;

    @NotNull(message = "El torneo_Id no puede ser nulo")
    @Column(name = "torneoId", nullable = false)
    private Long torneoId;

    @NotNull(message = "El participante_a_id no puede ser nulo")
    @Column(name = "participanteAId", nullable = false)
    private Long participanteAId;

    @NotNull(message = "El participante_b_id no puede ser nulo")
    @Column(name = "participanteBId", nullable = false)
    private Long participanteBId;

    @NotNull(message = "la ronda no puede ser nulo")
    @Column(name = "ronda", nullable = false)
    private Integer ronda;

    @NotNull(message = "la fecha_hora no puede ser nulo")
    @Column(name = "fechaHora", nullable = false)
    private LocalDateTime fechaHora;

    @NotBlank(message = "El campo de estadopartida no puede ser vacio")
    @Column(name = "estadopartida", nullable = false)
    private String estadopartida;

    @Embedded
    private Audit audit = new Audit();
}
