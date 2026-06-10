package com.Esports.Msvcs_partidas.Controllers;

import com.Esports.Msvcs_partidas.Services.PartidaService;
import com.Esports.Msvcs_partidas.models.dtos.CrearPartidaDTO;
import com.Esports.Msvcs_partidas.models.dtos.PartidaResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/partidas")
@Validated
public class PartidasControllerV2 {

    @Autowired
    private PartidaService partidaService;

    @PostMapping
    public ResponseEntity<PartidaResponseDTO> crearPartida(@Valid @RequestBody CrearPartidaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(partidaService.crearPartida(dto));
    }

    @GetMapping
    public ResponseEntity<List<PartidaResponseDTO>> listarPartidas() {
        return ResponseEntity.status(HttpStatus.OK).body(partidaService.listarPartidas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartidaResponseDTO> buscarPartida(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(partidaService.buscarPartida(id));
    }

    @PutMapping("/horario/{id}")
    public ResponseEntity<PartidaResponseDTO> actualizarHorario(@PathVariable Long id, @RequestParam LocalDateTime fechaHora) {
        return ResponseEntity.status(HttpStatus.OK).body(partidaService.actualizarHorario(id, fechaHora));
    }

    @DeleteMapping("/cancelar/{id}")
    public ResponseEntity<String> cancelarPartida(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(partidaService.cancelarPartida(id));
    }
}
