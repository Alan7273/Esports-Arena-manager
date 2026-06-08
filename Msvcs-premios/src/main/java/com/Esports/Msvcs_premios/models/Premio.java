package com.Esports.Msvcs_premios.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "premios")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Premio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "premio_id")
    private Long premioId;

    @NotNull(message = "El campo de torneoid no puede ser vacio")
    @Column(name = "torneo_id", nullable = false)
    private Long torneoId;

    @Positive(message = "El campo de posicion no puede ser vacio")
    @Column(name = "posicion", nullable = false)
    private Integer posicion;

    @NotBlank(message = "El campo de descripcion no puede ser vacio")
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Positive(message = "El campo de valor no puede ser vacio")
    @Column(name = "valor", nullable = false)
    private Double valor;

    @NotBlank(message = "El campo de estado no puede ser vacio")
    @Column(name = "estado", nullable = false)
    private String estado;

    @Embedded
    private Audit audit = new Audit();
}
