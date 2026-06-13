package com.Esports.Msvcs_usuarios.assemblers;

import com.Esports.Msvcs_usuarios.Controllers.UsuariosControllerV2;
import com.Esports.Msvcs_usuarios.models.dtos.UsuarioResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<UsuarioResponseDTO, EntityModel<UsuarioResponseDTO>>{

    @Override
    public EntityModel<UsuarioResponseDTO> toModel(UsuarioResponseDTO usuario) {
        return EntityModel.of(usuario,
                linkTo(methodOn(UsuariosControllerV2.class).buscarUsuario(usuario.getUsuarioId())).withSelfRel(),
                linkTo(methodOn(UsuariosControllerV2.class).listarUsuarios()).withRel("usuarios"));
    }
}
