package com.Esports.Msvcs_premios.Controllers;

import com.Esports.Msvcs_premios.Services.PremioService;
import com.Esports.Msvcs_premios.assemblers.PremioModelAssembler;
import com.Esports.Msvcs_premios.models.dtos.ActualizarPremioDTO;
import com.Esports.Msvcs_premios.models.dtos.CrearPremioDTO;
import com.Esports.Msvcs_premios.models.dtos.PremioResponseDTO;
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
@RequestMapping("/api/v2/premios")
@Validated
@Tag(name = "Premios V2", description = "Operaciones HATEOAS del microservicio de Premios")
public class PremiosControllerV2 {

    @Autowired
    private PremioService premioService;

    @Autowired
    private PremioModelAssembler assembler;

    @Operation(summary = "Crear un premio (HATEOAS)")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<PremioResponseDTO>> crearPremio(@Valid @RequestBody CrearPremioDTO dto) {
        PremioResponseDTO nuevo = premioService.crearPremio(dto);
        return ResponseEntity
                .created(linkTo(methodOn(PremiosControllerV2.class).buscarPremio(nuevo.getPremioId())).toUri())
                .body(assembler.toModel(nuevo));
    }

    @Operation(summary = "Listar premios (HATEOAS)")
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<PremioResponseDTO>> listarPremios() {
        List<EntityModel<PremioResponseDTO>> premios = premioService.listarPremios().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(premios,
                linkTo(methodOn(PremiosControllerV2.class).listarPremios()).withSelfRel());
    }

    @Operation(summary = "Buscar premio por ID (HATEOAS)")
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<PremioResponseDTO> buscarPremio(@PathVariable Long id) {
        return assembler.toModel(premioService.buscarPremio(id));
    }

    @Operation(summary = "Actualizar un premio (HATEOAS)")
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<PremioResponseDTO>> actualizarPremio(@PathVariable Long id, @Valid @RequestBody ActualizarPremioDTO dto) {
        return ResponseEntity.ok(assembler.toModel(premioService.actualizarPremio(id, dto)));
    }

    @Operation(summary = "Asignar un premio a un participante")
    @PutMapping("/asignar/{id}")
    public ResponseEntity<String> asignarPremio(@PathVariable Long id, @RequestParam Long participanteId) {
        return ResponseEntity.status(HttpStatus.OK).body(premioService.asignarPremio(id, participanteId));
    }
}
