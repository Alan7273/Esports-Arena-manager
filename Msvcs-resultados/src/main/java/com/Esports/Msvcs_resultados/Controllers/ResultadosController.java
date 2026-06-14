package com.Esports.Msvcs_resultados.Controllers;

import com.Esports.Msvcs_resultados.Services.ResultadoService;
import com.Esports.Msvcs_resultados.models.dtos.ActualizarResultadoDTO;
import com.Esports.Msvcs_resultados.models.dtos.CrearResultadoDTO;
import com.Esports.Msvcs_resultados.models.dtos.ResultadoResponseDTO;
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
@RequestMapping("/api/v1/resultados")
@Validated
@Tag(name = "Resultados", description = "Operaciones del microservicio de Resultados")
public class ResultadosController {

    @Autowired
    private ResultadoService resultadoService;

    @Operation(summary = "Registrar el resultado de una partida")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Resultado registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Partida cancelada, ganador inválido o resultado duplicado")
    })
    @PostMapping
    public ResponseEntity<ResultadoResponseDTO> registrarResultado(@Valid @RequestBody CrearResultadoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(resultadoService.registrarResultado(dto));
    }

    @Operation(summary = "Listar resultados")
    @GetMapping
    public ResponseEntity<List<ResultadoResponseDTO>> listarResultados() {
        return ResponseEntity.status(HttpStatus.OK).body(resultadoService.listarResultados());
    }

    @Operation(summary = "Buscar resultado por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resultado encontrado"),
            @ApiResponse(responseCode = "404", description = "Resultado no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResultadoResponseDTO> buscarResultado(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(resultadoService.buscarResultado(id));
    }

    @Operation(summary = "Actualizar un resultado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resultado actualizado"),
            @ApiResponse(responseCode = "404", description = "Resultado no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ResultadoResponseDTO> actualizarResultado(@PathVariable Long id, @Valid @RequestBody ActualizarResultadoDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(resultadoService.actualizarResultado(id, dto));
    }

    @Operation(summary = "Validar un resultado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resultado validado"),
            @ApiResponse(responseCode = "404", description = "Resultado no encontrado")
    })
    @PutMapping("/validar/{id}")
    public ResponseEntity<String> validarResultado(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(resultadoService.validarResultado(id));
    }

    @Operation(summary = "Anular un resultado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resultado anulado"),
            @ApiResponse(responseCode = "404", description = "Resultado no encontrado")
    })
    @DeleteMapping("/anular/{id}")
    public ResponseEntity<String> anularResultado(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(resultadoService.anularResultado(id));
    }
}
