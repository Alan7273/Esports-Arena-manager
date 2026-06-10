package com.Esports.Msvcs_torneos.Controllers;

import com.Esports.Msvcs_torneos.Services.TorneoService;
import com.Esports.Msvcs_torneos.models.dtos.ActualizarTorneoDTO;
import com.Esports.Msvcs_torneos.models.dtos.CrearTorneoDTO;
import com.Esports.Msvcs_torneos.models.dtos.TorneoResponseDTO;
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
public class TorneosControllerV2 {

    @Autowired
    private TorneoService torneoService;

    @PostMapping
    public ResponseEntity<TorneoResponseDTO> crearTorneo(@Valid @RequestBody CrearTorneoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(torneoService.crearTorneo(dto));
    }

    @GetMapping
    public ResponseEntity<List<TorneoResponseDTO>> listarTorneos() {
        return ResponseEntity.status(HttpStatus.OK).body(torneoService.listarTorneos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TorneoResponseDTO> buscarTorneo(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(torneoService.buscarTorneo(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TorneoResponseDTO> actualizarTorneo(@Valid @PathVariable Long id,@RequestBody ActualizarTorneoDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(torneoService.actualizarTorneo(id, dto));
    }

    @DeleteMapping("/cerrar/{id}")
    public ResponseEntity<String> cerrarTorneo(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(torneoService.cerrarTorneo(id));
    }

    @DeleteMapping("/cancelar/{id}")
    public ResponseEntity<String> cancelarTorneo(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(torneoService.cancelarTorneo(id));
    }

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
