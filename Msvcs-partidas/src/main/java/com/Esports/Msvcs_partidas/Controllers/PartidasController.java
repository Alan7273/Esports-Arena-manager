package com.Esports.Msvcs_partidas.Controllers;

import com.Esports.Msvcs_partidas.Services.PartidaService;
import com.Esports.Msvcs_partidas.models.dtos.CrearPartidaDTO;
import com.Esports.Msvcs_partidas.models.dtos.PartidaResponseDTO;
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

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/partidas")
@Validated
@Tag(name = "Partidas", description = "Operaciones del microservicio de Partidas")
public class PartidasController {

    @Autowired
    private PartidaService partidaService;

    @Operation(summary = "Crear una partida")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Partida creada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PartidaResponseDTO.class),
                            examples = @ExampleObject(value = """
                {"partidaId":20,"torneoId":2,"participanteAId":10,"participanteBId":11,"ronda":1,"fechaHora":"2025-07-05T15:00:00","estadopartida":"PROGRAMADA"}
                """))),
            @ApiResponse(responseCode = "404", description = "Torneo o participante no existe/no inscrito",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                {"status":404,"error":"Not Found","message":"El participante con id 99 no está inscrito en el torneo"}
                """)))
    })
    @PostMapping
    public ResponseEntity<PartidaResponseDTO> crearPartida(@Valid @RequestBody CrearPartidaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(partidaService.crearPartida(dto));
    }

    @Operation(summary = "Listar partidas")
    @GetMapping
    public ResponseEntity<List<PartidaResponseDTO>> listarPartidas() {
        return ResponseEntity.status(HttpStatus.OK).body(partidaService.listarPartidas());
    }

    @Operation(summary = "Buscar partida por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Partida encontrada"),
            @ApiResponse(responseCode = "404", description = "Partida no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PartidaResponseDTO> buscarPartida(@Parameter(description = "ID de la partida", example = "20") @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(partidaService.buscarPartida(id));
    }

    @Operation(summary = "Actualizar el horario de una partida")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Horario actualizado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PartidaResponseDTO.class),
                            examples = @ExampleObject(value = """
                {"partidaId":20,"torneoId":2,"participanteAId":10,"participanteBId":11,"ronda":1,"fechaHora":"2025-07-06T18:00:00","estadopartida":"PROGRAMADA"}
                """))),
            @ApiResponse(responseCode = "404", description = "Partida no encontrada",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                {"status":404,"error":"Not Found","message":"Partida con id 99 no encontrada"}
                """)))
    })
    @PutMapping("/horario/{id}")
    public ResponseEntity<PartidaResponseDTO> actualizarHorario(@Parameter(description = "ID de la partida", example = "20") @PathVariable Long id,
                                                                @Parameter(description = "Nueva fecha y hora", example = "2025-07-06T18:00:00") @RequestParam LocalDateTime fechaHora) {
        return ResponseEntity.status(HttpStatus.OK).body(partidaService.actualizarHorario(id, fechaHora));
    }

    @Operation(summary = "Cancelar una partida")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Partida cancelada",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "\"Partida cancelada correctamente\""))),
            @ApiResponse(responseCode = "404", description = "Partida no encontrada",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                {"status":404,"error":"Not Found","message":"Partida con id 99 no encontrada"}
                """)))
    })
    @DeleteMapping("/cancelar/{id}")
    public ResponseEntity<String> cancelarPartida(@Parameter(description = "ID de la partida a cancelar", example = "20") @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(partidaService.cancelarPartida(id));
    }
}
