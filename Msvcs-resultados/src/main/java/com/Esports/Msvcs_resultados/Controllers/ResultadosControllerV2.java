package com.Esports.Msvcs_resultados.Controllers;

import com.Esports.Msvcs_resultados.Services.ResultadoService;
import com.Esports.Msvcs_resultados.models.dtos.ActualizarResultadoDTO;
import com.Esports.Msvcs_resultados.models.dtos.CrearResultadoDTO;
import com.Esports.Msvcs_resultados.models.dtos.ResultadoResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resultados")
@Validated
public class ResultadosControllerV2 {

    @Autowired
    private ResultadoService resultadoService;

    @PostMapping
    public ResponseEntity<ResultadoResponseDTO> registrarResultado(@Valid @RequestBody CrearResultadoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(resultadoService.registrarResultado(dto));
    }

    @GetMapping
    public ResponseEntity<List<ResultadoResponseDTO>> listarResultados() {
        return ResponseEntity.status(HttpStatus.OK).body(resultadoService.listarResultados());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultadoResponseDTO> buscarResultado(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(resultadoService.buscarResultado(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultadoResponseDTO> actualizarResultado(@PathVariable Long id, @Valid @RequestBody ActualizarResultadoDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(resultadoService.actualizarResultado(id, dto));
    }

    @PutMapping("/validar/{id}")
    public ResponseEntity<String> validarResultado(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(resultadoService.validarResultado(id));
    }

    @DeleteMapping("/anular/{id}")
    public ResponseEntity<String> anularResultado(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(resultadoService.anularResultado(id));
    }
}
