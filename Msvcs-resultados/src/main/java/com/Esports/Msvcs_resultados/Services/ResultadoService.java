package com.Esports.Msvcs_resultados.Services;

import com.Esports.Msvcs_resultados.models.dtos.ActualizarResultadoDTO;
import com.Esports.Msvcs_resultados.models.dtos.CrearResultadoDTO;
import com.Esports.Msvcs_resultados.models.dtos.ResultadoResponseDTO;

import java.util.List;

public interface ResultadoService {
    ResultadoResponseDTO registrarResultado(CrearResultadoDTO dto);
    List<ResultadoResponseDTO> listarResultados();
    ResultadoResponseDTO buscarResultado(Long id);
    ResultadoResponseDTO actualizarResultado(Long id, ActualizarResultadoDTO dto);
    String validarResultado(Long id);
    String anularResultado(Long id);
}
