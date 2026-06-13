package com.Esports.Msvcs_inscripciones.assemblers;

import com.Esports.Msvcs_inscripciones.Controllers.InscripcionControllerV2;
import com.Esports.Msvcs_inscripciones.models.dtos.InscripcionResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class InscripcionModelAssembler implements RepresentationModelAssembler<InscripcionResponseDTO, EntityModel<InscripcionResponseDTO>> {

    @Override
    public EntityModel<InscripcionResponseDTO> toModel(InscripcionResponseDTO inscripcion) {
        return EntityModel.of(inscripcion,
                linkTo(methodOn(InscripcionControllerV2.class).buscarInscripcion(inscripcion.getInscripcionId())).withSelfRel(),
                linkTo(methodOn(InscripcionControllerV2.class).listarInscripciones()).withRel("inscripciones"));
    }
}
