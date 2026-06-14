package com.Esports.Msvcs_notificaciones.Controllers;

import com.Esports.Msvcs_notificaciones.Services.NotificacionService;
import com.Esports.Msvcs_notificaciones.assemblers.NotificacionModelAssembler;
import com.Esports.Msvcs_notificaciones.models.dtos.CrearNotificacionDTO;
import com.Esports.Msvcs_notificaciones.models.dtos.NotificacionResponseDTO;
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
@RequestMapping("/api/v2/notificaciones")
@Validated
@Tag(name = "Notificaciones V2", description = "Operaciones HATEOAS del microservicio de Notificaciones")
public class NotificacionesControllerV2 {

    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private NotificacionModelAssembler assembler;

    @Operation(summary = "Crear una notificación (HATEOAS)")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<NotificacionResponseDTO>> crearNotificacion(@Valid @RequestBody CrearNotificacionDTO dto) {
        NotificacionResponseDTO nueva = notificacionService.crearNotificacion(dto);
        return ResponseEntity
                .created(linkTo(methodOn(NotificacionesControllerV2.class).buscarNotificacion(nueva.getNotificacionId())).toUri())
                .body(assembler.toModel(nueva));
    }

    @Operation(summary = "Listar notificaciones de un usuario (HATEOAS)")
    @GetMapping(value = "/usuario/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<NotificacionResponseDTO>> listarUsuario(@PathVariable Long id) {
        List<EntityModel<NotificacionResponseDTO>> notificaciones = notificacionService.listarUsuario(id).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(notificaciones,
                linkTo(methodOn(NotificacionesControllerV2.class).listarUsuario(id)).withSelfRel());
    }

    @Operation(summary = "Buscar notificación por ID (HATEOAS)")
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<NotificacionResponseDTO> buscarNotificacion(@PathVariable Long id) {
        return assembler.toModel(notificacionService.buscarNotificacion(id));
    }

    @Operation(summary = "Marcar una notificación como leída")
    @PutMapping("/leida/{id}")
    public ResponseEntity<String> marcarLeida(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(notificacionService.marcarLeida(id));
    }

    @Operation(summary = "Eliminar una notificación (HATEOAS)")
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> eliminarNotificacion(@PathVariable Long id) {
        notificacionService.eliminarNotificacion(id);
        return ResponseEntity.noContent().build();
    }
}
