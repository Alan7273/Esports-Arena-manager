package com.Esports.Msvcs_equipos.Controllers;

import com.Esports.Msvcs_equipos.Services.EquipoService;
import com.Esports.Msvcs_equipos.models.dtos.AgregarMiembroDTO;
import com.Esports.Msvcs_equipos.models.dtos.CrearEquipoDTO;
import com.Esports.Msvcs_equipos.models.dtos.EquipoResponseDTO;
import com.Esports.Msvcs_equipos.models.dtos.MiembroEquipoResponseDTO;
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
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@RestController
@RequestMapping("/api/v1/equipos")
@Validated
@Tag(name = "Equipos", description = "Operaciones del microservicio de Equipos")
public class EquiposController {

    @Autowired
    private EquipoService equipoService;

    @Operation(summary = "Crear un equipo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Equipo creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EquipoResponseDTO.class),
                            examples = @ExampleObject(value = """
                {"equipoId":10,"nombreequipo":"Team Phantom","capitanId":3,"juegoprincipalId":1,"estadoequipo":"ACTIVO"}
                """))),
            @ApiResponse(responseCode = "404", description = "Capitán o juego principal no existe",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                {"status":404,"error":"Not Found","message":"Capitán con id 99 no encontrado"}
                """)))
    })
    @PostMapping
    public ResponseEntity<EquipoResponseDTO> crearEquipo(@Valid @RequestBody CrearEquipoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(equipoService.crearEquipo(dto));
    }

    @Operation(summary = "Listar equipos")
    @GetMapping
    public ResponseEntity<List<EquipoResponseDTO>> listarEquipos() {
        return ResponseEntity.status(HttpStatus.OK).body(equipoService.listarEquipos());
    }

    @Operation(summary = "Buscar equipo por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Equipo encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EquipoResponseDTO.class),
                            examples = @ExampleObject(value = """
                {"equipoId":10,"nombreequipo":"Team Phantom","capitanId":3,"juegoprincipalId":1,"estadoequipo":"ACTIVO"}
                """))),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                {"status":404,"error":"Not Found","message":"Equipo con id 99 no encontrado"}
                """)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<EquipoResponseDTO> buscarEquipo(@Parameter(description = "ID del equipo a buscar", example = "10") @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(equipoService.buscarEquipo(id));
    }

    @Operation(summary = "Agregar miembro a un equipo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Miembro agregado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MiembroEquipoResponseDTO.class),
                            examples = @ExampleObject(value = """
                {"MiembroId":5,"equipoId":10,"usuarioId":7,"rolDentroEquipo":"Support","fechaIngreso":"2025-06-01T14:30:00"}
                """))),
            @ApiResponse(responseCode = "404", description = "Equipo o usuario no encontrado",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                {"status":404,"error":"Not Found","message":"Usuario con id 99 no encontrado"}
                """)))
    })
    @PostMapping("/{id}/miembros")
    public ResponseEntity<MiembroEquipoResponseDTO> agregarMiembro(@Parameter(description = "ID del equipo", example = "10") @PathVariable Long id, @Valid @RequestBody AgregarMiembroDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(equipoService.agregarMiembro(id, dto));
    }

    @Operation(summary = "Eliminar un miembro de un equipo")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Miembro eliminado"),
            @ApiResponse(responseCode = "404", description = "Miembro no encontrado")
    })
    @DeleteMapping("/{equipoId}/miembros/{usuarioId}")
    public ResponseEntity<Void> eliminarMiembro(@Parameter(description = "ID del equipo", example = "10") @PathVariable Long equipoId,
                                                @Parameter(description = "ID del usuario a eliminar", example = "7") @PathVariable Long usuarioId) {
        equipoService.eliminarMiembro(equipoId, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Actualizar el capitán de un equipo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Capitán actualizado"),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado")
    })
    @PutMapping("/capitan/{id}")
    public ResponseEntity<EquipoResponseDTO> actualizarCapitan(@Parameter(description = "ID del equipo", example = "10") @PathVariable Long id,
                                                               @Parameter(description = "ID del nuevo capitán", example = "5") @RequestParam Long capitanId) {
        return ResponseEntity.status(HttpStatus.OK).body(equipoService.actualizarCapitan(id, capitanId));
    }

    @Operation(summary = "Desactivar un equipo (borrado lógico)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Equipo desactivado",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "\"Equipo desactivado correctamente\""))),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                {"status":404,"error":"Not Found","message":"Equipo con id 99 no encontrado"}
                """)))
    })
    @DeleteMapping("/desactivar/{id}")
    public ResponseEntity<String> desactivarEquipo(@Parameter(description = "ID del equipo a desactivar", example = "10") @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(equipoService.desactivarEquipo(id));
    }

    @Operation(summary = "Verificar si un equipo existe", description = "Usado por otros microservicios vía Feign")
    @GetMapping("/{id}/existe")
    public ResponseEntity<Boolean> existeEquipo(@PathVariable Long id) {
        try {
            equipoService.buscarEquipo(id);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(false);
        }
    }
}
