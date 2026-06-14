package com.Esports.Msvcs_resultados.Controllers;

import com.Esports.Msvcs_resultados.Services.ResultadoService;
import com.Esports.Msvcs_resultados.assemblers.ResultadoModelAssembler;
import com.Esports.Msvcs_resultados.models.dtos.ActualizarResultadoDTO;
import com.Esports.Msvcs_resultados.models.dtos.CrearResultadoDTO;
import com.Esports.Msvcs_resultados.models.dtos.ResultadoResponseDTO;
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
@RequestMapping("/api/v2/resultados")
@Validated
@Tag(name = "Resultados V2", description = "Operaciones HATEOAS del microservicio de Resultados")
public class ResultadosControllerV2 {

    @Autowired
    private ResultadoService resultadoService;

    @Autowired
    private ResultadoModelAssembler assembler;

    @Operation(summary = "Registrar el resultado de una partida (HATEOAS)")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ResultadoResponseDTO>> registrarResultado(@Valid @RequestBody CrearResultadoDTO dto) {
        ResultadoResponseDTO nuevo = resultadoService.registrarResultado(dto);
        return ResponseEntity
                .created(linkTo(methodOn(ResultadosControllerV2.class).buscarResultado(nuevo.getResultadoId())).toUri())
                .body(assembler.toModel(nuevo));
    }

    @Operation(summary = "Listar resultados (HATEOAS)")
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<ResultadoResponseDTO>> listarResultados() {
        List<EntityModel<ResultadoResponseDTO>> resultados = resultadoService.listarResultados().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(resultados,
                linkTo(methodOn(ResultadosControllerV2.class).listarResultados()).withSelfRel());
    }

    @Operation(summary = "Buscar resultado por ID (HATEOAS)")
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<ResultadoResponseDTO> buscarResultado(@PathVariable Long id) {
        return assembler.toModel(resultadoService.buscarResultado(id));
    }

    @Operation(summary = "Actualizar un resultado (HATEOAS)")
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ResultadoResponseDTO>> actualizarResultado(@PathVariable Long id, @Valid @RequestBody ActualizarResultadoDTO dto) {
        return ResponseEntity.ok(assembler.toModel(resultadoService.actualizarResultado(id, dto)));
    }

    @Operation(summary = "Validar un resultado")
    @PutMapping("/validar/{id}")
    public ResponseEntity<String> validarResultado(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(resultadoService.validarResultado(id));
    }

    @Operation(summary = "Anular un resultado (HATEOAS)")
    @DeleteMapping(value = "/anular/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> anularResultado(@PathVariable Long id) {
        resultadoService.anularResultado(id);
        return ResponseEntity.noContent().build();
    }
}
