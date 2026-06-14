package com.Esports.Msvcs_premios.assemblers;

import com.Esports.Msvcs_premios.Controllers.PremiosControllerV2;
import com.Esports.Msvcs_premios.models.dtos.PremioResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PremioModelAssembler implements RepresentationModelAssembler<PremioResponseDTO, EntityModel<PremioResponseDTO>> {

    @Override
    public EntityModel<PremioResponseDTO> toModel(PremioResponseDTO premio) {
        return EntityModel.of(premio,
                linkTo(methodOn(PremiosControllerV2.class).buscarPremio(premio.getPremioId())).withSelfRel(),
                linkTo(methodOn(PremiosControllerV2.class).listarPremios()).withRel("premios"));
    }
}
