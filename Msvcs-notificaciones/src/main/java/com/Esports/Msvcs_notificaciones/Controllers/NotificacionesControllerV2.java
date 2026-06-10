package com.Esports.Msvcs_notificaciones.Controllers;

import com.Esports.Msvcs_notificaciones.Services.NotificacionService;
import com.Esports.Msvcs_notificaciones.models.dtos.CrearNotificacionDTO;
import com.Esports.Msvcs_notificaciones.models.dtos.NotificacionResponseDTO;
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
public class NotificacionesControllerV2 {

    @Autowired
    private NotificacionService notificacionService;

    @PostMapping
    public ResponseEntity<NotificacionResponseDTO> crearNotificacion(@Valid @RequestBody CrearNotificacionDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificacionService.crearNotificacion(dto));
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<NotificacionResponseDTO>> listarUsuario(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(notificacionService.listarUsuario(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificacionResponseDTO> buscarNotificacion(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(notificacionService.buscarNotificacion(id));
    }

    @PutMapping("/leida/{id}")
    public ResponseEntity<String> marcarLeida(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(notificacionService.marcarLeida(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarNotificacion(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(notificacionService.eliminarNotificacion(id));
    }
}
