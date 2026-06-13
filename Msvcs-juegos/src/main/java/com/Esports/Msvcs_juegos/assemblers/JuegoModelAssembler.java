package com.Esports.Msvcs_juegos.assemblers;

import com.Esports.Msvcs_juegos.Controllers.JuegosControllerV2;
import com.Esports.Msvcs_juegos.models.dtos.JuegoResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class JuegoModelAssembler implements RepresentationModelAssembler<JuegoResponseDTO, EntityModel<JuegoResponseDTO>> {
    @Override
    public EntityModel<JuegoResponseDTO> toModel(JuegoResponseDTO juego) {
        return EntityModel.of(juego,
                linkTo(methodOn(JuegosControllerV2.class).buscarJuego(juego.getJuegosId())).withSelfRel(),
                linkTo(methodOn(JuegosControllerV2.class).listarJuegos()).withRel("juegos"));
    }
}
