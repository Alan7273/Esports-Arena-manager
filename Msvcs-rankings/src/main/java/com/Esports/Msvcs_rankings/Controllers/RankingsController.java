package com.Esports.Msvcs_rankings.Controllers;

import com.Esports.Msvcs_rankings.Services.RankingService;
import com.Esports.Msvcs_rankings.models.dtos.RankingResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/v1/rankings")
@Validated
@Tag(name = "Rankings", description = "Operaciones del microservicio de Rankings")
public class RankingsController {

    @Autowired
    private RankingService rankingService;

    @Operation(summary = "Crear el registro de ranking de un participante en un torneo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ranking creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "\"Ranking creado correctamente para el participante 10 en el torneo 2\"")))
    })
    @PutMapping("/torneo/{torneoId}/participante/{participanteId}")
    public ResponseEntity<String> crearRanking(@Parameter(description = "ID del torneo", example = "2") @PathVariable Long torneoId,
                                               @Parameter(description = "ID del participante", example = "10") @PathVariable Long participanteId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rankingService.crearRankingParticipante(torneoId, participanteId));
    }

    @Operation(summary = "Obtener la tabla de ranking de un torneo")
    @GetMapping("/{torneoId}")
    public ResponseEntity<List<RankingResponseDTO>> obtenerTabla(@Parameter(description = "ID del torneo", example = "2") @PathVariable Long torneoId) {
        return ResponseEntity.status(HttpStatus.OK).body(rankingService.obtenerTabla(torneoId));
    }

    @Operation(summary = "Buscar el ranking de un participante por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ranking encontrado"),
            @ApiResponse(responseCode = "404", description = "Ranking no encontrado")
    })
    @GetMapping("/participante/{id}")
    public ResponseEntity<RankingResponseDTO> buscarRanking(@Parameter(description = "ID del registro de ranking", example = "4") @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(rankingService.buscarRanking(id));
    }

    @Operation(summary = "Recalcular las posiciones del ranking de un torneo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ranking actualizado",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "\"Ranking recalculado correctamente\""))),
            @ApiResponse(responseCode = "404", description = "No hay rankings para el torneo",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                {"status":404,"error":"Not Found","message":"No se encontraron rankings para el torneo 99"}
                """)))
    })
    @PutMapping("/recalcular/{torneoId}")
    public ResponseEntity<String> actualizarRanking(@Parameter(description = "ID del torneo", example = "2") @PathVariable Long torneoId) {
        return ResponseEntity.status(HttpStatus.OK).body(rankingService.actualizarRanking(torneoId));
    }

    @Operation(summary = "Reiniciar el ranking de un torneo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ranking reiniciado"),
            @ApiResponse(responseCode = "404", description = "No hay rankings para el torneo")
    })
    @DeleteMapping("/reiniciar/{torneoId}")
    public ResponseEntity<String> reiniciarRanking(@Parameter(description = "ID del torneo a reiniciar", example = "2") @PathVariable Long torneoId) {
        return ResponseEntity.status(HttpStatus.OK).body(rankingService.reiniciarRanking(torneoId));
    }
}
