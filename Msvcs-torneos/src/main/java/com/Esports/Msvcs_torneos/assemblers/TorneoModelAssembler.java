package com.Esports.Msvcs_torneos.assemblers;

import com.Esports.Msvcs_torneos.Controllers.TorneosControllerV2;
import com.Esports.Msvcs_torneos.models.dtos.TorneoResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TorneoModelAssembler implements RepresentationModelAssembler<TorneoResponseDTO, EntityModel<TorneoResponseDTO>> {

    @Override
    public EntityModel<TorneoResponseDTO> toModel(TorneoResponseDTO torneo) {
        return EntityModel.of(torneo,
                linkTo(methodOn(TorneosControllerV2.class).buscarTorneo(torneo.getTorneoId())).withSelfRel(),
                linkTo(methodOn(TorneosControllerV2.class).listarTorneos()).withRel("torneos"));
    }
}
