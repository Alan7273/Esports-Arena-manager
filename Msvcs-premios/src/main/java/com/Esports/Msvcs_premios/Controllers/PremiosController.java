package com.Esports.Msvcs_premios.Controllers;

import com.Esports.Msvcs_premios.Services.PremioService;
import com.Esports.Msvcs_premios.models.dtos.ActualizarPremioDTO;
import com.Esports.Msvcs_premios.models.dtos.CrearPremioDTO;
import com.Esports.Msvcs_premios.models.dtos.PremioResponseDTO;
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
@RequestMapping("/api/v1/premios")
@Validated
@Tag(name = "Premios", description = "Operaciones del microservicio de Premios")
public class PremiosController {

    @Autowired
    private PremioService premioService;

    @Operation(summary = "Crear un premio")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Premio creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<PremioResponseDTO> crearPremio(@Valid @RequestBody CrearPremioDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(premioService.crearPremio(dto));
    }

    @Operation(summary = "Listar premios")
    @GetMapping
    public ResponseEntity<List<PremioResponseDTO>> listarPremios() {
        return ResponseEntity.status(HttpStatus.OK).body(premioService.listarPremios());
    }

    @Operation(summary = "Buscar premio por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Premio encontrado"),
            @ApiResponse(responseCode = "404", description = "Premio no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PremioResponseDTO> buscarPremio(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(premioService.buscarPremio(id));
    }

    @Operation(summary = "Actualizar un premio")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Premio actualizado"),
            @ApiResponse(responseCode = "404", description = "Premio no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PremioResponseDTO> actualizarPremio(@PathVariable Long id, @Valid @RequestBody ActualizarPremioDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(premioService.actualizarPremio(id, dto));
    }

    @Operation(summary = "Asignar un premio a un participante")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Premio asignado"),
            @ApiResponse(responseCode = "404", description = "Premio no encontrado")
    })
    @PutMapping("/asignar/{id}")
    public ResponseEntity<String> asignarPremio(@PathVariable Long id, @RequestParam Long participanteId) {
        return ResponseEntity.status(HttpStatus.OK).body(premioService.asignarPremio(id, participanteId));
    }
}
