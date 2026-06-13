package com.Esports.Msvcs_usuarios.Controllers;

import com.Esports.Msvcs_usuarios.Services.UsuarioService;
import com.Esports.Msvcs_usuarios.assemblers.UsuarioModelAssembler;
import com.Esports.Msvcs_usuarios.models.dtos.ActualizarUsuarioDTO;
import com.Esports.Msvcs_usuarios.models.dtos.CrearUsuarioDTO;
import com.Esports.Msvcs_usuarios.models.dtos.UsuarioResponseDTO;
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
@RequestMapping("/api/v2/usuarios")
@Validated
@Tag(name = "Usuarios V2", description = "Operaciones HATEOAS del microservicio de Usuarios")
public class UsuariosControllerV2 {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioModelAssembler assembler;

    @Operation(summary = "Crear un usuario (HATEOAS)")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<UsuarioResponseDTO>> crearUsuario(@Valid @RequestBody CrearUsuarioDTO dto) {
        UsuarioResponseDTO nuevo = usuarioService.crearUsuario(dto);
        return ResponseEntity
                .created(linkTo(methodOn(UsuariosControllerV2.class).buscarUsuario(nuevo.getUsuarioId())).toUri())
                .body(assembler.toModel(nuevo));
    }

    @Operation(summary = "Listar usuarios (HATEOAS)")
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<UsuarioResponseDTO>> listarUsuarios(){
        List<EntityModel<UsuarioResponseDTO>> usuarios = usuarioService.ListarUsuarios().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(usuarios,
                linkTo(methodOn(UsuariosControllerV2.class).listarUsuarios()).withSelfRel());
    }

    @Operation(summary = "Buscar usuario por ID (HATEOAS)")
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<UsuarioResponseDTO> buscarUsuario(@PathVariable Long id) {
        return assembler.toModel(usuarioService.BuscarPorId(id));
    }

    @Operation(summary = "Actualizar un usuario (HATEOAS)")
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<UsuarioResponseDTO>> actualizarUsuario(@PathVariable Long id, @Valid @RequestBody ActualizarUsuarioDTO dto) {
        return ResponseEntity.ok(assembler.toModel(usuarioService.actualizarUsuario(id, dto)));
    }

    @Operation(summary = "Desactivar un usuario (HATEOAS)")
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<String> desactivarUsuario(@PathVariable Long id) {
        usuarioService.DesactivarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
