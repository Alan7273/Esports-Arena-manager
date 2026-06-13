package com.Esports.Msvcs_inscripciones.Controllers;

import com.Esports.Msvcs_inscripciones.Services.InscripcionesService;
import com.Esports.Msvcs_inscripciones.assemblers.InscripcionModelAssembler;
import com.Esports.Msvcs_inscripciones.models.dtos.CrearInscripcionDTO;
import com.Esports.Msvcs_inscripciones.models.dtos.InscripcionResponseDTO;
import com.Esports.Msvcs_inscripciones.repositories.InscripcionRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@RequestMapping("/api/v2/inscripciones")
@Validated
@Tag(name = "Inscripciones V2", description = "Operaciones HATEOAS del microservicio de Inscripciones")
public class InscripcionControllerV2 {

    @Autowired
    private InscripcionesService inscripcionesService;

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Autowired
    private InscripcionModelAssembler assembler;

    @Operation(summary = "Crear una inscripción (HATEOAS)")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<InscripcionResponseDTO>> crearInscripcion(@Valid @RequestBody CrearInscripcionDTO dto) {
        InscripcionResponseDTO nueva = inscripcionesService.crearInscripcion(dto);
        return ResponseEntity
                .created(linkTo(methodOn(InscripcionControllerV2.class).buscarInscripcion(nueva.getInscripcionId())).toUri())
                .body(assembler.toModel(nueva));
    }

    @Operation(summary = "Listar inscripciones (HATEOAS)")
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<InscripcionResponseDTO>> listarInscripciones() {
        List<EntityModel<InscripcionResponseDTO>> inscripciones = inscripcionesService.listarInscripciones().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(inscripciones,
                linkTo(methodOn(InscripcionControllerV2.class).listarInscripciones()).withSelfRel());
    }

    @Operation(summary = "Buscar inscripción por ID (HATEOAS)")
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<InscripcionResponseDTO> buscarInscripcion(@PathVariable Long id) {
        return assembler.toModel(inscripcionesService.buscarInscripcion(id));
    }

    @Operation(summary = "Actualizar el estado de una inscripción (HATEOAS)")
    @PutMapping(value = "/estado/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<InscripcionResponseDTO>> actualizarEstado(@PathVariable Long id, @RequestParam String estado) {
        return ResponseEntity.ok(assembler.toModel(inscripcionesService.actualizarEstado(id, estado)));
    }

    @Operation(summary = "Cancelar una inscripción (HATEOAS)")
    @DeleteMapping(value = "/cancelar/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> cancelarInscripcion(@PathVariable Long id) {
        inscripcionesService.cancelarInscripcion(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Verificar si un participante está inscrito en un torneo")
    @GetMapping("/torneo/{torneoId}/participante/{participanteId}/existe")
    public ResponseEntity<Boolean> existeParticipante(@PathVariable Long torneoId, @PathVariable Long participanteId) {
        boolean existe = inscripcionRepository.existsByTorneoIdAndUsuarioId(torneoId, participanteId)
                || inscripcionRepository.existsByTorneoIdAndEquipoId(torneoId, participanteId);
        return ResponseEntity.status(HttpStatus.OK).body(existe);
    }
}
