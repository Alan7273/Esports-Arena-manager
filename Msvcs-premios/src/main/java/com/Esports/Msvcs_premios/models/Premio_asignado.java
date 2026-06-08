package com.Esports.Msvcs_premios.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "premio_asignado")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Premio_asignado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long premio_asignadoId;

    @NotNull(message = "El campo de premio_id no puede ser vacio")
    @Column(name = "premio_id", nullable = false)
    private Long premioId;

    @NotNull(message = "El campo de participante_id no puede ser vacio")
    @Column(name = "participante_id", nullable = false)
    private Long participanteId;

    @NotNull(message = "El campo de fechaAsignacion no puede ser vacio")
    @Column(name = "fecha_asignacion", nullable = false)
    private LocalDate fechaAsignacion;

    @Embedded
    private Audit audit = new Audit();
}
