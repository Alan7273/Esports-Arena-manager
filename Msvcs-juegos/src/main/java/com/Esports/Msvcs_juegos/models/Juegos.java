package com.Esports.Msvcs_juegos.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "juegos")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Juegos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "juego_id")
    private Long juegosId;

    @NotBlank(message = "El campo de nombre de juegos no puede ser vacio")
    @Column(name = "nombre_Juegos",nullable = false, unique = true)
    private String nombrejuegos;

    @NotBlank(message = "El campo de genero del juego no puede ser vacio")
    @Column(name = "Genero_juego",nullable = false)
    private String Generojuego;

    @NotBlank(message = "El campo de modalidad del juego no puede ser vacio")
    @Column(name = "Modalidad_juegos",nullable = false)
    private String Modalidadjuegos;

    @Positive(message = "El campo de cantidad de jugadores de equipo no puede ser vacio")
    @Column(name = "Jugadores_por_equipo",nullable = false)
    private Integer Jugadores_por_equipo;

    @NotBlank(message = "El campo de estado del juego no puede ser vacio")
    @Column(name = "estado_juego",nullable = false)
    private String estadojuego;

    @Embedded
    private Audit audit = new Audit();
}
