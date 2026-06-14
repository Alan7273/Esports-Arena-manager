package com.Esports.Msvcs_rankings.Controllers;

import com.Esports.Msvcs_rankings.Services.RankingService;
import com.Esports.Msvcs_rankings.assemblers.RankingModelAssembler;
import com.Esports.Msvcs_rankings.models.dtos.RankingResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v2/rankings")
@Validated
@Tag(name = "Rankings V2", description = "Operaciones HATEOAS del microservicio de Rankings")
public class RankingsControllerV2 {

    @Autowired
    private RankingService rankingService;

    @Autowired
    private RankingModelAssembler assembler;

    @Operation(summary = "Crear el registro de ranking de un participante en un torneo")
    @PutMapping("/torneo/{torneoId}/participante/{participanteId}")
    public ResponseEntity<String> crearRanking(@PathVariable Long torneoId, @PathVariable Long participanteId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rankingService.crearRankingParticipante(torneoId, participanteId));
    }

    @Operation(summary = "Obtener la tabla de ranking de un torneo (HATEOAS)")
    @GetMapping(value = "/{torneoId}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<RankingResponseDTO>> obtenerTabla(@PathVariable Long torneoId) {
        List<EntityModel<RankingResponseDTO>> tabla = rankingService.obtenerTabla(torneoId).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(tabla,
                linkTo(methodOn(RankingsControllerV2.class).obtenerTabla(torneoId)).withSelfRel());
    }

    @Operation(summary = "Buscar el ranking de un participante por ID (HATEOAS)")
    @GetMapping(value = "/participante/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<RankingResponseDTO> buscarRanking(@PathVariable Long id) {
        return assembler.toModel(rankingService.buscarRanking(id));
    }

    @Operation(summary = "Recalcular las posiciones del ranking de un torneo")
    @PutMapping("/recalcular/{torneoId}")
    public ResponseEntity<String> actualizarRanking(@PathVariable Long torneoId) {
        return ResponseEntity.status(HttpStatus.OK).body(rankingService.actualizarRanking(torneoId));
    }

    @Operation(summary = "Reiniciar el ranking de un torneo")
    @DeleteMapping("/reiniciar/{torneoId}")
    public ResponseEntity<String> reiniciarRanking(@PathVariable Long torneoId) {
        return ResponseEntity.status(HttpStatus.OK).body(rankingService.reiniciarRanking(torneoId));
    }
}
