package com.Esports.Msvcs_torneos.Controllers;

import com.Esports.Msvcs_torneos.Services.TorneoService;
import com.Esports.Msvcs_torneos.assemblers.TorneoModelAssembler;
import com.Esports.Msvcs_torneos.models.dtos.ActualizarTorneoDTO;
import com.Esports.Msvcs_torneos.models.dtos.CrearTorneoDTO;
import com.Esports.Msvcs_torneos.models.dtos.TorneoResponseDTO;
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

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v2/torneos")
@Validated
@Tag(name = "Torneos V2", description = "Operaciones HATEOAS del microservicio de Torneos")
public class TorneosControllerV2 {

    @Autowired
    private TorneoService torneoService;

    @Autowired
    private TorneoModelAssembler assembler;

    @Operation(summary = "Crear un torneo (HATEOAS)")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<TorneoResponseDTO>> crearTorneo(@Valid @RequestBody CrearTorneoDTO dto) {
        TorneoResponseDTO nuevo = torneoService.crearTorneo(dto);
        return ResponseEntity
                .created(linkTo(methodOn(TorneosControllerV2.class).buscarTorneo(nuevo.getTorneoId())).toUri())
                .body(assembler.toModel(nuevo));
    }

    @Operation(summary = "Listar torneos (HATEOAS)")
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<TorneoResponseDTO>> listarTorneos() {
        List<EntityModel<TorneoResponseDTO>> torneos = torneoService.listarTorneos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(torneos,
                linkTo(methodOn(TorneosControllerV2.class).listarTorneos()).withSelfRel());
    }

    @Operation(summary = "Buscar torneo por ID (HATEOAS)")
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<TorneoResponseDTO> buscarTorneo(@PathVariable Long id) {
        return assembler.toModel(torneoService.buscarTorneo(id));
    }

    @Operation(summary = "Actualizar un torneo (HATEOAS)")
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<TorneoResponseDTO>> actualizarTorneo(@Valid @PathVariable Long id, @RequestBody ActualizarTorneoDTO dto) {
        return ResponseEntity.ok(assembler.toModel(torneoService.actualizarTorneo(id, dto)));
    }

    @Operation(summary = "Cerrar un torneo (HATEOAS)")
    @DeleteMapping(value = "/cerrar/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<String> cerrarTorneo(@PathVariable Long id) {
        torneoService.cerrarTorneo(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Cancelar un torneo (HATEOAS)")
    @DeleteMapping(value = "/cancelar/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<String> cancelarTorneo(@PathVariable Long id) {
        torneoService.cancelarTorneo(id);
        return ResponseEntity.noContent().build();
    }
}
