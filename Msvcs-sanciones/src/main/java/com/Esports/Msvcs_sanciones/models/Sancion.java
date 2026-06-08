package com.Esports.Msvcs_sanciones.models;

import jakarta.annotation.Nullable;
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

import java.time.LocalDate;

@Entity
@Table(name = "sanciones")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Sancion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sancion_id")
    private Long sancionId;

    @Nullable
    @Column(name = "usuario_id")
    private Long usuarioId;

    @Nullable
    @Column(name = "equipo_id")
    private Long equipoId;

    @NotBlank(message = "El campo de motivo no puede ser vacio")
    @Column(name = "motivo", nullable = false)
    private String motivo;

    @NotNull(message = "El campo de fechaInicio no puede ser vacio")
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Nullable
    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @NotBlank(message = "El campo de estadoSancion no puede ser vacio")
    @Column(name = "estado_Sancion", nullable = false)
    private String estadoSancion;

    @NotBlank(message = "El campo de severidad no puede ser vacio")
    @Column(name = "severidad", nullable = false)
    private String severidad;

    @Embedded
    private Audit audit = new Audit();
}
