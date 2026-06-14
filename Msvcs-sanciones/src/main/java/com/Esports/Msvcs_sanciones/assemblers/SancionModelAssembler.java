package com.Esports.Msvcs_sanciones.assemblers;

import com.Esports.Msvcs_sanciones.Controllers.SancionesControllerV2;
import com.Esports.Msvcs_sanciones.models.dtos.SancionResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SancionModelAssembler implements RepresentationModelAssembler<SancionResponseDTO, EntityModel<SancionResponseDTO>> {

    @Override
    public EntityModel<SancionResponseDTO> toModel(SancionResponseDTO sancion) {
        return EntityModel.of(sancion,
                linkTo(methodOn(SancionesControllerV2.class).buscarSancion(sancion.getSancionId())).withSelfRel(),
                linkTo(methodOn(SancionesControllerV2.class).listarSanciones()).withRel("sanciones"));
    }
}
