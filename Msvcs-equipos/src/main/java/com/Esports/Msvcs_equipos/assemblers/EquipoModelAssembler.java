package com.Esports.Msvcs_equipos.assemblers;

import com.Esports.Msvcs_equipos.Controllers.EquiposControllerV2;
import com.Esports.Msvcs_equipos.models.dtos.EquipoResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EquipoModelAssembler implements RepresentationModelAssembler<EquipoResponseDTO, EntityModel<EquipoResponseDTO>> {

    @Override
    public EntityModel<EquipoResponseDTO> toModel(EquipoResponseDTO equipo) {
        return EntityModel.of(equipo,
                linkTo(methodOn(EquiposControllerV2.class).buscarEquipo(equipo.getEquipoId())).withSelfRel(),
                linkTo(methodOn(EquiposControllerV2.class).listarEquipos()).withRel("equipos"));
    }
}
