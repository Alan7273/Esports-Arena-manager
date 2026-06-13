package com.Esports.Msvcs_equipos.Controllers;

import com.Esports.Msvcs_equipos.Services.EquipoService;
import com.Esports.Msvcs_equipos.assemblers.EquipoModelAssembler;
import com.Esports.Msvcs_equipos.models.dtos.AgregarMiembroDTO;
import com.Esports.Msvcs_equipos.models.dtos.CrearEquipoDTO;
import com.Esports.Msvcs_equipos.models.dtos.EquipoResponseDTO;
import com.Esports.Msvcs_equipos.models.dtos.MiembroEquipoResponseDTO;
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
@RequestMapping("/api/v2/equipos")
@Validated
@Tag(name = "Equipos V2", description = "Operaciones HATEOAS del microservicio de Equipos")
public class EquiposControllerV2 {

    @Autowired
    private EquipoService equipoService;

    @Autowired
    private EquipoModelAssembler assembler;

    @Operation(summary = "Crear un equipo (HATEOAS)")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<EquipoResponseDTO>> crearEquipo(@Valid @RequestBody CrearEquipoDTO dto) {
        EquipoResponseDTO nuevo = equipoService.crearEquipo(dto);
        return ResponseEntity
                .created(linkTo(methodOn(EquiposControllerV2.class).buscarEquipo(nuevo.getEquipoId())).toUri())
                .body(assembler.toModel(nuevo));
    }

    @Operation(summary = "Listar equipos (HATEOAS)")
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<EquipoResponseDTO>> listarEquipos() {
        List<EntityModel<EquipoResponseDTO>> equipos = equipoService.listarEquipos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(equipos,
                linkTo(methodOn(EquiposControllerV2.class).listarEquipos()).withSelfRel());
    }

    @Operation(summary = "Buscar equipo por ID (HATEOAS)")
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<EquipoResponseDTO> buscarEquipo(@PathVariable Long id) {
        return assembler.toModel(equipoService.buscarEquipo(id));
    }

    @Operation(summary = "Agregar miembro a un equipo")
    @PostMapping("/{id}/miembros")
    public ResponseEntity<MiembroEquipoResponseDTO> agregarMiembro(@PathVariable Long id, @Valid @RequestBody AgregarMiembroDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(equipoService.agregarMiembro(id, dto));
    }

    @Operation(summary = "Eliminar un miembro de un equipo")
    @DeleteMapping("/{equipoId}/miembros/{usuarioId}")
    public ResponseEntity<Void> eliminarMiembro(@PathVariable Long equipoId, @PathVariable Long usuarioId) {
        equipoService.eliminarMiembro(equipoId, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Actualizar el capitán de un equipo (HATEOAS)")
    @PutMapping(value = "/capitan/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<EquipoResponseDTO>> actualizarCapitan(@PathVariable Long id, @RequestParam Long capitanId) {
        return ResponseEntity.ok(assembler.toModel(equipoService.actualizarCapitan(id, capitanId)));
    }

    @Operation(summary = "Desactivar un equipo (HATEOAS)")
    @DeleteMapping(value = "/desactivar/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> desactivarEquipo(@PathVariable Long id) {
        equipoService.desactivarEquipo(id);
        return ResponseEntity.noContent().build();
    }
}
