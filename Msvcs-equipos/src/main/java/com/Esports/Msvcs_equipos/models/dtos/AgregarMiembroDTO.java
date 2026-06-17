package com.Esports.Msvcs_equipos.models.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AgregarMiembroDTO {

    @Schema(description = "ID del usuario a agregar como miembro", example = "7")
    @NotNull(message = "El usuario es obligatorio")
    private Long usuarioId;

    @Schema(description = "Rol del miembro dentro del equipo", example = "Support")
    @NotBlank(message = "El rol dentro del equipo no puede estar vacío")
    private String rolDentroEquipo;
}
