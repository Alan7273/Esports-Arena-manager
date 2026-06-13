package com.Esports.Msvcs_equipos.Controllers;
import com.Esports.Msvcs_equipos.Services.EquipoService;
import com.Esports.Msvcs_equipos.models.Equipos;
import com.Esports.Msvcs_equipos.models.Miembro_equipo;
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
            @ApiResponse(responseCode = "201", description = "Equipo creado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Capitán o juego principal no existe")
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
            @ApiResponse(responseCode = "200", description = "Equipo encontrado"),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EquipoResponseDTO> buscarEquipo(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(equipoService.buscarEquipo(id));
    }

    @Operation(summary = "Agregar miembro a un equipo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Miembro agregado"),
            @ApiResponse(responseCode = "404", description = "Equipo o usuario no encontrado")
    })
    @PostMapping("/{id}/miembros")
    public ResponseEntity<MiembroEquipoResponseDTO> agregarMiembro(@PathVariable Long id, @Valid @RequestBody AgregarMiembroDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(equipoService.agregarMiembro(id, dto));
    }

    @Operation(summary = "Eliminar un miembro de un equipo")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Miembro eliminado"),
            @ApiResponse(responseCode = "404", description = "Miembro no encontrado")
    })
    @DeleteMapping("/{equipoId}/miembros/{usuarioId}")
    public ResponseEntity<Void> eliminarMiembro(@PathVariable Long equipoId, @PathVariable Long usuarioId) {
        equipoService.eliminarMiembro(equipoId, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Actualizar el capitán de un equipo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Capitán actualizado"),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado")
    })
    @PutMapping("/capitan/{id}")
    public ResponseEntity<EquipoResponseDTO> actualizarCapitan(@PathVariable Long id, @RequestParam Long capitanId) {
        return ResponseEntity.status(HttpStatus.OK).body(equipoService.actualizarCapitan(id, capitanId));
    }

    @Operation(summary = "Desactivar un equipo (borrado lógico)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Equipo desactivado"),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado")
    })
    @DeleteMapping("/desactivar/{id}")
    public ResponseEntity<String> desactivarEquipo(@PathVariable Long id) {
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
