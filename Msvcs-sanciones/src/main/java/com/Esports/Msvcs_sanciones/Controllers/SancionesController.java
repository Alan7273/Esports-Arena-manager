package com.Esports.Msvcs_sanciones.Controllers;

import com.Esports.Msvcs_sanciones.Services.SancionesService;
import com.Esports.Msvcs_sanciones.models.dtos.ActualizarSancionDTO;
import com.Esports.Msvcs_sanciones.models.dtos.CrearSancionDTO;
import com.Esports.Msvcs_sanciones.models.dtos.SancionResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sanciones")
@Validated
@Tag(name = "Sanciones", description = "Operaciones del microservicio de Sanciones")
public class SancionesController {

    @Autowired
    private SancionesService sancionesService;

    @Operation(summary = "Crear una sanción")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Sanción creada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SancionResponseDTO.class),
                            examples = @ExampleObject(value = """
                {"sancionId":3,"usuarioId":7,"equipoId":null,"motivo":"Conducta antideportiva","fechaInicio":"2025-06-01","fechaFin":"2025-06-15","estadoSancion":"ACTIVA","severidad":"MEDIA"}
                """))),
            @ApiResponse(responseCode = "404", description = "Usuario sancionado no existe",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                {"status":404,"error":"Not Found","message":"Usuario con id 99 no encontrado"}
                """)))
    })
    @PostMapping
    public ResponseEntity<SancionResponseDTO> crearSancion(@Valid @RequestBody CrearSancionDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sancionesService.crearSancion(dto));
    }

    @Operation(summary = "Listar sanciones")
    @GetMapping
    public ResponseEntity<List<SancionResponseDTO>> listarSanciones() {
        return ResponseEntity.status(HttpStatus.OK).body(sancionesService.listarSanciones());
    }

    @Operation(summary = "Buscar sanción por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sanción encontrada"),
            @ApiResponse(responseCode = "404", description = "Sanción no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<SancionResponseDTO> buscarSancion(@Parameter(description = "ID de la sanción", example = "3") @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(sancionesService.buscarSancion(id));
    }

    @Operation(summary = "Cerrar una sanción")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sanción cerrada",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "\"Sanción cerrada correctamente\""))),
            @ApiResponse(responseCode = "404", description = "Sanción no encontrada",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                {"status":404,"error":"Not Found","message":"Sanción con id 99 no encontrada"}
                """)))
    })
    @DeleteMapping("/cerrar/{id}")
    public ResponseEntity<String> cerrarSancion(@Parameter(description = "ID de la sanción a cerrar", example = "3") @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(sancionesService.cerrarSancion(id));
    }

    @Operation(summary = "Validar si un usuario tiene una sanción activa", description = "Usado por otros microservicios vía Feign")
    @GetMapping("/validar/{usuarioId}")
    public ResponseEntity<Boolean> validarSancionActiva(@Parameter(description = "ID del usuario a validar", example = "7") @PathVariable Long usuarioId) {
        return ResponseEntity.status(HttpStatus.OK).body(sancionesService.validarSancionActiva(usuarioId));
    }

    @Operation(summary = "Actualizar una sanción")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sanción actualizada"),
            @ApiResponse(responseCode = "404", description = "Sanción no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<SancionResponseDTO> actualizarSancion(@Parameter(description = "ID de la sanción", example = "3") @PathVariable Long id, @Valid @RequestBody ActualizarSancionDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(sancionesService.actualizarSancion(id, dto));
    }
}
