package com.Esports.Msvcs_premios.Controllers;

import com.Esports.Msvcs_premios.Services.PremioService;
import com.Esports.Msvcs_premios.models.dtos.ActualizarPremioDTO;
import com.Esports.Msvcs_premios.models.dtos.CrearPremioDTO;
import com.Esports.Msvcs_premios.models.dtos.PremioResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/premios")
@Validated
public class PremiosControllerV2 {

    @Autowired
    private PremioService premioService;

    @PostMapping
    public ResponseEntity<PremioResponseDTO> crearPremio(@Valid @RequestBody CrearPremioDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(premioService.crearPremio(dto));
    }

    @GetMapping
    public ResponseEntity<List<PremioResponseDTO>> listarPremios() {
        return ResponseEntity.status(HttpStatus.OK).body(premioService.listarPremios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PremioResponseDTO> buscarPremio(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(premioService.buscarPremio(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PremioResponseDTO> actualizarPremio(@PathVariable Long id, @Valid @RequestBody ActualizarPremioDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(premioService.actualizarPremio(id, dto));
    }

    @PutMapping("/asignar/{id}")
    public ResponseEntity<String> asignarPremio(@PathVariable Long id, @RequestParam Long participanteId) {
        return ResponseEntity.status(HttpStatus.OK).body(premioService.asignarPremio(id, participanteId));
    }
}
