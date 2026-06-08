package com.Esports.Msvcs_juegos.Services;

import com.Esports.Msvcs_juegos.models.Juegos;
import com.Esports.Msvcs_juegos.models.dtos.ActualizarJuegoDTO;
import com.Esports.Msvcs_juegos.models.dtos.CrearJuegoDTO;
import com.Esports.Msvcs_juegos.models.dtos.JuegoResponseDTO;

import java.util.List;

public interface JuegosService {
    JuegoResponseDTO CrearJuego(CrearJuegoDTO dto);
    List<JuegoResponseDTO> ListarJuegos();
    JuegoResponseDTO BuscarJuego(Long juegosId);
    JuegoResponseDTO actualizarJuego(Long juegosId, ActualizarJuegoDTO dto);
    String desactivarJuego(Long juegosId);
}
