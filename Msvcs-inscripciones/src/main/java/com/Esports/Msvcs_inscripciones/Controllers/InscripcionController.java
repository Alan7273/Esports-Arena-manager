package com.Esports.Msvcs_inscripciones.Controllers;

import com.Esports.Msvcs_inscripciones.Services.InscripcionesService;
import com.Esports.Msvcs_inscripciones.models.dtos.CrearInscripcionDTO;
import com.Esports.Msvcs_inscripciones.models.dtos.InscripcionResponseDTO;
import com.Esports.Msvcs_inscripciones.repositories.InscripcionRepository;
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
@RequestMapping("/api/v1/inscripciones")
@Validated
@Tag(name = "Inscripciones", description = "Operaciones del microservicio de Inscripciones")
public class InscripcionController {

    @Autowired
    private InscripcionesService inscripcionesService;

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Operation(summary = "Crear una inscripción")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Inscripción creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Torneo no activo o sin cupos"),
            @ApiResponse(responseCode = "404", description = "Torneo o equipo no existe"),
            @ApiResponse(responseCode = "409", description = "Usuario/equipo ya inscrito")
    })
    @PostMapping
    public ResponseEntity<InscripcionResponseDTO> crearInscripcion(@Valid @RequestBody CrearInscripcionDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inscripcionesService.crearInscripcion(dto));
    }

    @Operation(summary = "Listar inscripciones")
    @GetMapping
    public ResponseEntity<List<InscripcionResponseDTO>> listarInscripciones() {
        return ResponseEntity.status(HttpStatus.OK).body(inscripcionesService.listarInscripciones());
    }

    @Operation(summary = "Buscar inscripción por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Inscripción encontrada"),
            @ApiResponse(responseCode = "404", description = "Inscripción no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<InscripcionResponseDTO> buscarInscripcion(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(inscripcionesService.buscarInscripcion(id));
    }

    @Operation(summary = "Actualizar el estado de una inscripción")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado actualizado"),
            @ApiResponse(responseCode = "404", description = "Inscripción no encontrada")
    })
    @PutMapping("/estado/{id}")
    public ResponseEntity<InscripcionResponseDTO> actualizarEstado(@PathVariable Long id, @RequestParam String estado) {
        return ResponseEntity.status(HttpStatus.OK).body(inscripcionesService.actualizarEstado(id, estado));
    }

    @Operation(summary = "Cancelar una inscripción")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Inscripción cancelada"),
            @ApiResponse(responseCode = "404", description = "Inscripción no encontrada")
    })
    @DeleteMapping("/cancelar/{id}")
    public ResponseEntity<String> cancelarInscripcion(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(inscripcionesService.cancelarInscripcion(id));
    }

    @Operation(summary = "Verificar si un participante está inscrito en un torneo", description = "Usado por otros microservicios vía Feign")
    @GetMapping("/torneo/{torneoId}/participante/{participanteId}/existe")
    public ResponseEntity<Boolean> existeParticipante(@PathVariable Long torneoId, @PathVariable Long participanteId) {
        boolean existe = inscripcionRepository.existsByTorneoIdAndUsuarioId(torneoId, participanteId)
                || inscripcionRepository.existsByTorneoIdAndEquipoId(torneoId, participanteId);
        return ResponseEntity.status(HttpStatus.OK).body(existe);
    }
}
