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
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;

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
            @ApiResponse(responseCode = "201", description = "Premio creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PremioResponseDTO.class),
                            examples = @ExampleObject(value = """
                {"premioId":6,"torneoId":2,"posicion":1,"descripcion":"Trofeo + $500.000 CLP","valor":500000.0,"estado":"PENDIENTE"}
                """))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                {"status":400,"error":"Bad Request","message":"El valor debe ser mayor a 0"}
                """)))
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
    public ResponseEntity<PremioResponseDTO> buscarPremio(@Parameter(description = "ID del premio", example = "6") @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(premioService.buscarPremio(id));
    }

    @Operation(summary = "Actualizar un premio")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Premio actualizado"),
            @ApiResponse(responseCode = "404", description = "Premio no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PremioResponseDTO> actualizarPremio(@Parameter(description = "ID del premio", example = "6") @PathVariable Long id, @Valid @RequestBody ActualizarPremioDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(premioService.actualizarPremio(id, dto));
    }

    @Operation(summary = "Asignar un premio a un participante")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Premio asignado",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "\"Premio asignado correctamente al participante 10\""))),
            @ApiResponse(responseCode = "404", description = "Premio no encontrado",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                {"status":404,"error":"Not Found","message":"Premio con id 99 no encontrado"}
                """)))
    })
    @PutMapping("/asignar/{id}")
    public ResponseEntity<String> asignarPremio(@Parameter(description = "ID del premio a asignar", example = "6") @PathVariable Long id,
                                                @Parameter(description = "ID del participante ganador", example = "10") @RequestParam Long participanteId) {
        return ResponseEntity.status(HttpStatus.OK).body(premioService.asignarPremio(id, participanteId));
    }
}
