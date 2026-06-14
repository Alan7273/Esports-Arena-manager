package com.Esports.Msvcs_sanciones.Controllers;

import com.Esports.Msvcs_sanciones.Services.SancionesService;
import com.Esports.Msvcs_sanciones.assemblers.SancionModelAssembler;
import com.Esports.Msvcs_sanciones.models.dtos.ActualizarSancionDTO;
import com.Esports.Msvcs_sanciones.models.dtos.CrearSancionDTO;
import com.Esports.Msvcs_sanciones.models.dtos.SancionResponseDTO;
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
@RequestMapping("/api/v2/sanciones")
@Validated
@Tag(name = "Sanciones V2", description = "Operaciones HATEOAS del microservicio de Sanciones")
public class SancionesControllerV2 {

    @Autowired
    private SancionesService sancionesService;

    @Autowired
    private SancionModelAssembler assembler;

    @Operation(summary = "Crear una sanción (HATEOAS)")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<SancionResponseDTO>> crearSancion(@Valid @RequestBody CrearSancionDTO dto) {
        SancionResponseDTO nueva = sancionesService.crearSancion(dto);
        return ResponseEntity
                .created(linkTo(methodOn(SancionesControllerV2.class).buscarSancion(nueva.getSancionId())).toUri())
                .body(assembler.toModel(nueva));
    }

    @Operation(summary = "Listar sanciones (HATEOAS)")
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<SancionResponseDTO>> listarSanciones() {
        List<EntityModel<SancionResponseDTO>> sanciones = sancionesService.listarSanciones().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(sanciones,
                linkTo(methodOn(SancionesControllerV2.class).listarSanciones()).withSelfRel());
    }

    @Operation(summary = "Buscar sanción por ID (HATEOAS)")
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<SancionResponseDTO> buscarSancion(@PathVariable Long id) {
        return assembler.toModel(sancionesService.buscarSancion(id));
    }

    @Operation(summary = "Cerrar una sanción (HATEOAS)")
    @DeleteMapping(value = "/cerrar/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> cerrarSancion(@PathVariable Long id) {
        sancionesService.cerrarSancion(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Validar si un usuario tiene una sanción activa")
    @GetMapping("/validar/{usuarioId}")
    public ResponseEntity<Boolean> validarSancionActiva(@PathVariable Long usuarioId) {
        return ResponseEntity.status(HttpStatus.OK).body(sancionesService.validarSancionActiva(usuarioId));
    }

    @Operation(summary = "Actualizar una sanción (HATEOAS)")
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<SancionResponseDTO>> actualizarSancion(@PathVariable Long id, @Valid @RequestBody ActualizarSancionDTO dto) {
        return ResponseEntity.ok(assembler.toModel(sancionesService.actualizarSancion(id, dto)));
    }
}
