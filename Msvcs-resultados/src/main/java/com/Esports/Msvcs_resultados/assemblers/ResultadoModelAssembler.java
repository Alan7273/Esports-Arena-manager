package com.Esports.Msvcs_resultados.assemblers;

import com.Esports.Msvcs_resultados.Controllers.ResultadosControllerV2;
import com.Esports.Msvcs_resultados.models.dtos.ResultadoResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ResultadoModelAssembler implements RepresentationModelAssembler<ResultadoResponseDTO, EntityModel<ResultadoResponseDTO>> {

    @Override
    public EntityModel<ResultadoResponseDTO> toModel(ResultadoResponseDTO resultado) {
        return EntityModel.of(resultado,
                linkTo(methodOn(ResultadosControllerV2.class).buscarResultado(resultado.getResultadoId())).withSelfRel(),
                linkTo(methodOn(ResultadosControllerV2.class).listarResultados()).withRel("resultados"));
    }
}
