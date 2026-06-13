package com.Esports.Msvcs_juegos.Controllers;

import com.Esports.Msvcs_juegos.Services.JuegosService;
import com.Esports.Msvcs_juegos.assemblers.JuegoModelAssembler;
import com.Esports.Msvcs_juegos.models.dtos.ActualizarJuegoDTO;
import com.Esports.Msvcs_juegos.models.dtos.CrearJuegoDTO;
import com.Esports.Msvcs_juegos.models.dtos.JuegoResponseDTO;
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
@RequestMapping("/api/v2/juegos")
@Validated
@Tag(name = "Juegos V2", description = "Operaciones HATEOAS del microservicio de Juegos")
public class JuegosControllerV2 {

    @Autowired
    private JuegosService juegosService;

    @Autowired
    private JuegoModelAssembler assembler;

    @Operation(summary = "Listar juegos (HATEOAS)")
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<JuegoResponseDTO>> listarJuegos() {
        List<EntityModel<JuegoResponseDTO>> juegos = juegosService.ListarJuegos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(juegos,
                linkTo(methodOn(JuegosControllerV2.class).listarJuegos()).withSelfRel());
    }

    @Operation(summary = "Buscar juego por ID (HATEOAS)")
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<JuegoResponseDTO> buscarJuego(@PathVariable Long id) {
        return assembler.toModel(juegosService.BuscarJuego(id));
    }

    @Operation(summary = "Crear juego (HATEOAS)")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<JuegoResponseDTO>> crearJuego(@Valid @RequestBody CrearJuegoDTO dto) {
        JuegoResponseDTO nuevo = juegosService.CrearJuego(dto);
        return ResponseEntity
                .created(linkTo(methodOn(JuegosControllerV2.class).buscarJuego(nuevo.getJuegosId())).toUri())
                .body(assembler.toModel(nuevo));
    }

    @Operation(summary = "Actualizar juego (HATEOAS)")
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<JuegoResponseDTO>> actualizarJuego(@PathVariable Long id, @Valid @RequestBody ActualizarJuegoDTO dto) {
        return ResponseEntity.ok(assembler.toModel(juegosService.actualizarJuego(id, dto)));
    }

    @Operation(summary = "Desactivar juego (HATEOAS)")
    @DeleteMapping(value = "/desactivar/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<String> desactivarJuego(@PathVariable Long id) {
        juegosService.desactivarJuego(id);
        return ResponseEntity.noContent().build();
    }
}
