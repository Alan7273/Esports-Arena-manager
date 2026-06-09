package com.Esports.Msvcs_sanciones.Services;

import com.Esports.Msvcs_sanciones.models.dtos.ActualizarSancionDTO;
import com.Esports.Msvcs_sanciones.models.dtos.CrearSancionDTO;
import com.Esports.Msvcs_sanciones.models.dtos.SancionResponseDTO;

import java.util.List;

public interface SancionesService {
    SancionResponseDTO crearSancion(CrearSancionDTO dto);
    List<SancionResponseDTO> listarSanciones();
    SancionResponseDTO buscarSancion(Long id);
    String cerrarSancion(Long id);
    Boolean validarSancionActiva(Long usuarioId);
    SancionResponseDTO actualizarSancion(Long id, ActualizarSancionDTO dto);
}
