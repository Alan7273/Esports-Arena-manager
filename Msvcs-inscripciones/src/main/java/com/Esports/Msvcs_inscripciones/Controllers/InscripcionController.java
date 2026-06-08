package com.Esports.Msvcs_inscripciones.Controllers;

import com.Esports.Msvcs_inscripciones.Services.InscripcionesService;
import com.Esports.Msvcs_inscripciones.models.Inscripcion;
import com.Esports.Msvcs_inscripciones.models.dtos.CrearInscripcionDTO;
import com.Esports.Msvcs_inscripciones.models.dtos.InscripcionResponseDTO;
import com.Esports.Msvcs_inscripciones.repositories.InscripcionRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inscripciones")
public class InscripcionController {

    @Autowired
    private InscripcionesService inscripcionesService;

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @PostMapping
    public ResponseEntity<InscripcionResponseDTO> crearInscripcion(@Valid @RequestBody CrearInscripcionDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inscripcionesService.crearInscripcion(dto));
    }

    @GetMapping
    public ResponseEntity<List<InscripcionResponseDTO>> listarInscripciones() {
        return ResponseEntity.status(HttpStatus.OK).body(inscripcionesService.listarInscripciones());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InscripcionResponseDTO> buscarInscripcion(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(inscripcionesService.buscarInscripcion(id));
    }

    @PutMapping("/estado/{id}")
    public ResponseEntity<InscripcionResponseDTO> actualizarEstado(@PathVariable Long id, @RequestParam String estado) {
        return ResponseEntity.status(HttpStatus.OK).body(inscripcionesService.actualizarEstado(id, estado));
    }

    @DeleteMapping("/cancelar/{id}")
    public ResponseEntity<String> cancelarInscripcion(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(inscripcionesService.cancelarInscripcion(id));
    }

    @GetMapping("/torneo/{torneoId}/participante/{participanteId}/existe")
    public ResponseEntity<Boolean> existeParticipante(@PathVariable Long torneoId, @PathVariable Long participanteId) {
        boolean existe = inscripcionRepository.existsByTorneoIdAndUsuarioId(torneoId, participanteId)
                || inscripcionRepository.existsByTorneoIdAndEquipoId(torneoId, participanteId);
        return ResponseEntity.status(HttpStatus.OK).body(existe);
    }
}
