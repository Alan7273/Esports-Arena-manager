package com.Esports.Msvcs_partidas.assemblers;

import com.Esports.Msvcs_partidas.Controllers.PartidasControllerV2;
import com.Esports.Msvcs_partidas.models.dtos.PartidaResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PartidaModelAssembler implements RepresentationModelAssembler<PartidaResponseDTO, EntityModel<PartidaResponseDTO>> {

    @Override
    public EntityModel<PartidaResponseDTO> toModel(PartidaResponseDTO partida) {
        return EntityModel.of(partida,
                linkTo(methodOn(PartidasControllerV2.class).buscarPartida(partida.getPartidaId())).withSelfRel(),
                linkTo(methodOn(PartidasControllerV2.class).listarPartidas()).withRel("partidas"));
    }
}
