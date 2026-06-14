package com.Esports.Msvcs_notificaciones.assemblers;

import com.Esports.Msvcs_notificaciones.Controllers.NotificacionesControllerV2;
import com.Esports.Msvcs_notificaciones.models.dtos.NotificacionResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class NotificacionModelAssembler implements RepresentationModelAssembler<NotificacionResponseDTO, EntityModel<NotificacionResponseDTO>> {

    @Override
    public EntityModel<NotificacionResponseDTO> toModel(NotificacionResponseDTO notificacion) {
        return EntityModel.of(notificacion,
                linkTo(methodOn(NotificacionesControllerV2.class).buscarNotificacion(notificacion.getNotificacionId())).withSelfRel(),
                linkTo(methodOn(NotificacionesControllerV2.class).listarUsuario(notificacion.getUsuarioId())).withRel("notificacionesUsuario"));
    }
}
