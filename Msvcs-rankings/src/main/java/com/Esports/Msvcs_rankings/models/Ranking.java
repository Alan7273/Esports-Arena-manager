package com.Esports.Msvcs_rankings.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "rankings")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Ranking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ranking_id")
    private Long rankingId;

    @NotNull(message = "El campo de torneoid no puede ser vacio")
    @Column(name = "torneo_id", nullable = false)
    private Long torneoId;

    @NotNull(message = "El campo de participanteid no puede ser vacio")
    @Column(name = "participante_id", nullable = false)
    private Long participanteId;

    @PositiveOrZero(message = "El campo de puntos no puede ser vacio")
    @Column(name = "puntos", nullable = false)
    private Integer puntos;

    @PositiveOrZero(message = "El campo de victorias no puede ser vacio")
    @Column(name = "victorias", nullable = false)
    private Integer victorias;

    @PositiveOrZero(message = "El campo de derrotas no puede ser vacio")
    @Column(name = "derrotas", nullable = false)
    private Integer derrotas;

    @PositiveOrZero(message = "El campo de diferencia no puede ser vacio")
    @Column(name = "diferencia", nullable = false)
    private Integer diferencia;

    @NotNull(message = "El campo de posicion no puede ser vacio")
    @Column(name = "posicion", nullable = false)
    private Integer posicion;

    @Embedded
    private Audit audit = new Audit();
}
