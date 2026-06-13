package com.Esports.Msvcs_torneos.Controllers;

import com.Esports.Msvcs_torneos.Services.TorneoService;
import com.Esports.Msvcs_torneos.models.dtos.ActualizarTorneoDTO;
import com.Esports.Msvcs_torneos.models.dtos.CrearTorneoDTO;
import com.Esports.Msvcs_torneos.models.dtos.TorneoResponseDTO;
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
@RequestMapping("/api/v1/torneos")
@Validated
@Tag(name = "Torneos", description = "Operaciones del microservicio de Torneos")
public class TorneosController {

    @Autowired
    private TorneoService torneoService;

    @Operation(summary = "Crear un torneo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Torneo creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Fechas inválidas")
    })
    @PostMapping
    public ResponseEntity<TorneoResponseDTO> crearTorneo(@Valid @RequestBody CrearTorneoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(torneoService.crearTorneo(dto));
    }

    @Operation(summary = "Listar torneos")
    @GetMapping
    public ResponseEntity<List<TorneoResponseDTO>> listarTorneos() {
        return ResponseEntity.status(HttpStatus.OK).body(torneoService.listarTorneos());
    }

    @Operation(summary = "Buscar torneo por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Torneo encontrado"),
            @ApiResponse(responseCode = "404", description = "Torneo no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TorneoResponseDTO> buscarTorneo(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(torneoService.buscarTorneo(id));
    }

    @Operation(summary = "Actualizar un torneo existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Torneo actualizado"),
            @ApiResponse(responseCode = "400", description = "Torneo cerrado/cancelado o fechas inválidas"),
            @ApiResponse(responseCode = "404", description = "Torneo no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TorneoResponseDTO> actualizarTorneo(@Valid @PathVariable Long id,@RequestBody ActualizarTorneoDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(torneoService.actualizarTorneo(id, dto));
    }

    @Operation(summary = "Cerrar un torneo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Torneo cerrado"),
            @ApiResponse(responseCode = "404", description = "Torneo no encontrado")
    })
    @DeleteMapping("/cerrar/{id}")
    public ResponseEntity<String> cerrarTorneo(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(torneoService.cerrarTorneo(id));
    }

    @Operation(summary = "Cancelar un torneo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Torneo cancelado"),
            @ApiResponse(responseCode = "404", description = "Torneo no encontrado")
    })
    @DeleteMapping("/cancelar/{id}")
    public ResponseEntity<String> cancelarTorneo(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(torneoService.cancelarTorneo(id));
    }

    @Operation(summary = "Verificar si un torneo existe", description = "Usado por otros microservicios vía Feign")
    @GetMapping("/{id}/existe")
    public ResponseEntity<Boolean> existeTorneo(@PathVariable Long id) {
        try {
            torneoService.buscarTorneo(id);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(false);
        }
    }
}
