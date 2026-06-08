package com.Esports.Msvcs_equipos.Services;

import com.Esports.Msvcs_equipos.models.dtos.AgregarMiembroDTO;
import com.Esports.Msvcs_equipos.models.dtos.CrearEquipoDTO;
import com.Esports.Msvcs_equipos.models.dtos.EquipoResponseDTO;
import com.Esports.Msvcs_equipos.models.dtos.MiembroEquipoResponseDTO;

import java.util.List;

public interface EquipoService {
    EquipoResponseDTO crearEquipo(CrearEquipoDTO dto);
    List<EquipoResponseDTO> listarEquipos();
    EquipoResponseDTO buscarEquipo(Long id);
    MiembroEquipoResponseDTO agregarMiembro(Long equipoId, AgregarMiembroDTO dto);
    String eliminarMiembro(Long equipoId, Long usuarioId);
    EquipoResponseDTO actualizarCapitan(Long id, Long capitanId);
    String desactivarEquipo(Long id);
}
