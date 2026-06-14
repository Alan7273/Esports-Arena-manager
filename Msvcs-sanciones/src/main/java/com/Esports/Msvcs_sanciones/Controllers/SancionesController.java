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
            @ApiResponse(responseCode = "201", description = "Sanción creada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario sancionado no existe")
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
    public ResponseEntity<SancionResponseDTO> buscarSancion(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(sancionesService.buscarSancion(id));
    }

    @Operation(summary = "Cerrar una sanción")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sanción cerrada"),
            @ApiResponse(responseCode = "404", description = "Sanción no encontrada")
    })
    @DeleteMapping("/cerrar/{id}")
    public ResponseEntity<String> cerrarSancion(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(sancionesService.cerrarSancion(id));
    }

    @Operation(summary = "Validar si un usuario tiene una sanción activa", description = "Usado por otros microservicios vía Feign")
    @GetMapping("/validar/{usuarioId}")
    public ResponseEntity<Boolean> validarSancionActiva(@PathVariable Long usuarioId) {
        return ResponseEntity.status(HttpStatus.OK).body(sancionesService.validarSancionActiva(usuarioId));
    }

    @Operation(summary = "Actualizar una sanción")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sanción actualizada"),
            @ApiResponse(responseCode = "404", description = "Sanción no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<SancionResponseDTO> actualizarSancion(@PathVariable Long id, @Valid @RequestBody ActualizarSancionDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(sancionesService.actualizarSancion(id, dto));
    }
}
