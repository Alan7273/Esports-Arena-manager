package com.Esports.Msvcs_partidas.Controllers;

import com.Esports.Msvcs_partidas.Services.PartidaService;
import com.Esports.Msvcs_partidas.assemblers.PartidaModelAssembler;
import com.Esports.Msvcs_partidas.models.dtos.CrearPartidaDTO;
import com.Esports.Msvcs_partidas.models.dtos.PartidaResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/api/v1/partidas")
@Validated
@Tag(name = "Partidas V2", description = "Operaciones HATEOAS del microservicio de Partidas")
public class PartidasControllerV2 {

    @Autowired
    private PartidaService partidaService;

    @Autowired
    private PartidaModelAssembler assembler;

    @Operation(summary = "Crear una partida (HATEOAS)")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<PartidaResponseDTO>> crearPartida(@Valid @RequestBody CrearPartidaDTO dto) {
        PartidaResponseDTO nueva = partidaService.crearPartida(dto);
        return ResponseEntity
                .created(linkTo(methodOn(PartidasControllerV2.class).buscarPartida(nueva.getPartidaId())).toUri())
                .body(assembler.toModel(nueva));
    }

    @Operation(summary = "Listar partidas (HATEOAS)")
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<PartidaResponseDTO>> listarPartidas() {
        List<EntityModel<PartidaResponseDTO>> partidas = partidaService.listarPartidas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(partidas,
                linkTo(methodOn(PartidasControllerV2.class).listarPartidas()).withSelfRel());
    }

    @Operation(summary = "Buscar partida por ID (HATEOAS)")
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<PartidaResponseDTO> buscarPartida(@PathVariable Long id) {
        return assembler.toModel(partidaService.buscarPartida(id));
    }

    @Operation(summary = "Actualizar el horario de una partida (HATEOAS)")
    @PutMapping(value = "/horario/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<PartidaResponseDTO>> actualizarHorario(@PathVariable Long id, @RequestParam LocalDateTime fechaHora) {
        return ResponseEntity.ok(assembler.toModel(partidaService.actualizarHorario(id, fechaHora)));
    }

    @Operation(summary = "Cancelar una partida (HATEOAS)")
    @DeleteMapping(value = "/cancelar/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> cancelarPartida(@PathVariable Long id) {
        partidaService.cancelarPartida(id);
        return ResponseEntity.noContent().build();
    }
}
