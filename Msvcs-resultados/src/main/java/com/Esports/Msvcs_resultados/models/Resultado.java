package com.Esports.Msvcs_resultados.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "resultados")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Resultado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resultado_id")
    private Long resultadoId;

    @NotNull(message = "El campo de partidaid no puede ser vacio")
    @Column(name = "partida_id", nullable = false, unique = true)
    private Long partidaId;

    @NotNull(message = "El campo de ganadorid no puede ser vacio")
    @Column(name = "ganador_id", nullable = false)
    private Long ganadorId;

    @PositiveOrZero(message = "El campo de puntajeA no puede ser vacio")
    @Column(name = "puntaje_a", nullable = false)
    private Integer puntajeA;

    @PositiveOrZero(message = "El campo de puntajeB no puede ser vacio")
    @Column(name = "puntaje_b", nullable = false)
    private Integer puntajeB;

    @NotBlank(message = "El campo de estadoValidacion no puede ser vacio")
    @Column(name = "estado_validacion", nullable = false)
    private String estadoValidacion;

    @NotNull(message = "El campo de fechaRegistro no puede ser vacio")
    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;

    @Embedded
    private Audit audit = new Audit();
}
