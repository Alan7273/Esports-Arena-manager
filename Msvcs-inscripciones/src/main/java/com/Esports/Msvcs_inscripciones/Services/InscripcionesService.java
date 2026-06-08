package com.Esports.Msvcs_inscripciones.Services;

import com.Esports.Msvcs_inscripciones.models.Inscripcion;
import com.Esports.Msvcs_inscripciones.models.dtos.CrearInscripcionDTO;
import com.Esports.Msvcs_inscripciones.models.dtos.InscripcionResponseDTO;

import java.util.List;

public interface InscripcionesService {
    InscripcionResponseDTO crearInscripcion(CrearInscripcionDTO dto);
    List<InscripcionResponseDTO> listarInscripciones();
    InscripcionResponseDTO buscarInscripcion(Long id);
    InscripcionResponseDTO actualizarEstado(Long id, String estado);
    String cancelarInscripcion(Long id);
}
