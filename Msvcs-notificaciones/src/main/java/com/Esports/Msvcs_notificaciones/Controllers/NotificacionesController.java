package com.Esports.Msvcs_notificaciones.Controllers;

import com.Esports.Msvcs_notificaciones.Services.NotificacionService;
import com.Esports.Msvcs_notificaciones.models.dtos.CrearNotificacionDTO;
import com.Esports.Msvcs_notificaciones.models.dtos.NotificacionResponseDTO;
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
@RequestMapping("/api/v1/notificaciones")
@Validated
@Tag(name = "Notificaciones", description = "Operaciones del microservicio de Notificaciones")
public class NotificacionesController {

    @Autowired
    private NotificacionService notificacionService;

    @Operation(summary = "Crear una notificación")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Notificación creada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario destinatario no existe")
    })
    @PostMapping
    public ResponseEntity<NotificacionResponseDTO> crearNotificacion(@Valid @RequestBody CrearNotificacionDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificacionService.crearNotificacion(dto));
    }

    @Operation(summary = "Listar notificaciones de un usuario")
    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<NotificacionResponseDTO>> listarUsuario(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(notificacionService.listarUsuario(id));
    }

    @Operation(summary = "Buscar notificación por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notificación encontrada"),
            @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<NotificacionResponseDTO> buscarNotificacion(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(notificacionService.buscarNotificacion(id));
    }

    @Operation(summary = "Marcar una notificación como leída")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notificación marcada como leída"),
            @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
    })
    @PutMapping("/leida/{id}")
    public ResponseEntity<String> marcarLeida(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(notificacionService.marcarLeida(id));
    }

    @Operation(summary = "Eliminar una notificación")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notificación eliminada"),
            @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarNotificacion(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(notificacionService.eliminarNotificacion(id));
    }
}
