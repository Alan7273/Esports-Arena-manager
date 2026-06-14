package com.Esports.Msvcs_rankings.assemblers;

import com.Esports.Msvcs_rankings.Controllers.RankingsControllerV2;
import com.Esports.Msvcs_rankings.models.dtos.RankingResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RankingModelAssembler implements RepresentationModelAssembler<RankingResponseDTO, EntityModel<RankingResponseDTO>> {

    @Override
    public EntityModel<RankingResponseDTO> toModel(RankingResponseDTO ranking) {
        return EntityModel.of(ranking,
                linkTo(methodOn(RankingsControllerV2.class).buscarRanking(ranking.getRankingId())).withSelfRel(),
                linkTo(methodOn(RankingsControllerV2.class).obtenerTabla(ranking.getTorneoId())).withRel("tabla"));
    }
}
