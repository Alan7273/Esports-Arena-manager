package com.Esports.Msvcs_usuarios.Controllers;

import com.Esports.Msvcs_usuarios.Services.UsuarioService;
import com.Esports.Msvcs_usuarios.models.dtos.ActualizarUsuarioDTO;
import com.Esports.Msvcs_usuarios.models.dtos.CrearUsuarioDTO;
import com.Esports.Msvcs_usuarios.models.dtos.UsuarioResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@Validated
@Tag(name = "Usuarios", description = "Operaciones del microservicio de Usuarios")
public class UsuariosController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Crear un usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(@Valid @RequestBody CrearUsuarioDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crearUsuario(dto));
    }

    @Operation(summary = "Listar usuarios")
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios(){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.ListarUsuarios());
    }

    @Operation(summary = "Buscar usuario por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuario(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.BuscarPorId(id));
    }

    @Operation(summary = "Actualizar un usuario existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario actualizado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(@PathVariable Long id, @Valid @RequestBody ActualizarUsuarioDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.actualizarUsuario(id, dto));
    }

    @Operation(summary = "Desactivar un usuario (borrado lógico)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario desactivado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> desactivarUsuario(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.DesactivarUsuario(id));
    }

    @Operation(summary = "Verificar si un usuario existe", description = "Usado por otros microservicios vía Feign")
    @GetMapping("/{id}/existe")
    public ResponseEntity<Boolean> existeUsuario(@PathVariable Long id) {
        try {
            usuarioService.BuscarPorId(id);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(false);
        }
    }
}
