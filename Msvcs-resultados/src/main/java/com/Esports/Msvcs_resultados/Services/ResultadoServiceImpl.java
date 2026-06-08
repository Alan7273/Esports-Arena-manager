package com.Esports.Msvcs_resultados.Services;

import com.Esports.Msvcs_resultados.exceptions.ResourceNotFoundException;
import com.Esports.Msvcs_resultados.models.Resultado;
import com.Esports.Msvcs_resultados.models.dtos.ActualizarResultadoDTO;
import com.Esports.Msvcs_resultados.models.dtos.CrearResultadoDTO;
import com.Esports.Msvcs_resultados.models.dtos.ResultadoResponseDTO;
import com.Esports.Msvcs_resultados.repositories.ResultadosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ResultadoServiceImpl implements ResultadoService {

    @Autowired
    private ResultadosRepository resultadosRepository;

    @Override
    public ResultadoResponseDTO registrarResultado(CrearResultadoDTO dto) {
        Resultado resultado = new Resultado();
        resultado.setPartidaId(dto.getPartidaId());
        resultado.setGanadorId(dto.getGanadorId());
        resultado.setPuntajeA(dto.getPuntajeA());
        resultado.setPuntajeB(dto.getPuntajeB());
        resultado.setEstadoValidacion("PENDIENTE");
        resultado.setFechaRegistro(LocalDate.now());

        resultadosRepository.save(resultado);

        ResultadoResponseDTO response = new ResultadoResponseDTO();
        response.setResultadoId(resultado.getResultadoId());
        response.setPartidaId(resultado.getPartidaId());
        response.setGanadorId(resultado.getGanadorId());
        response.setPuntajeA(resultado.getPuntajeA());
        response.setPuntajeB(resultado.getPuntajeB());
        response.setEstadoValidacion(resultado.getEstadoValidacion());
        response.setFechaRegistro(resultado.getFechaRegistro());

        return response;
    }

    @Override
    public List<ResultadoResponseDTO> listarResultados() {
        return resultadosRepository.findAll().stream().map(resultado -> {
            ResultadoResponseDTO dto = new ResultadoResponseDTO();
            dto.setResultadoId(resultado.getResultadoId());
            dto.setPartidaId(resultado.getPartidaId());
            dto.setGanadorId(resultado.getGanadorId());
            dto.setPuntajeA(resultado.getPuntajeA());
            dto.setPuntajeB(resultado.getPuntajeB());
            dto.setEstadoValidacion(resultado.getEstadoValidacion());
            dto.setFechaRegistro(resultado.getFechaRegistro());
            return dto;
        }).toList();
    }

    @Override
    public ResultadoResponseDTO buscarResultado(Long id) {
        Resultado resultado = resultadosRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resultado no encontrado"));

        ResultadoResponseDTO dto = new ResultadoResponseDTO();
        dto.setResultadoId(resultado.getResultadoId());
        dto.setPartidaId(resultado.getPartidaId());
        dto.setGanadorId(resultado.getGanadorId());
        dto.setPuntajeA(resultado.getPuntajeA());
        dto.setPuntajeB(resultado.getPuntajeB());
        dto.setEstadoValidacion(resultado.getEstadoValidacion());
        dto.setFechaRegistro(resultado.getFechaRegistro());

        return dto;
    }

    @Override
    public ResultadoResponseDTO actualizarResultado(Long id, ActualizarResultadoDTO dto) {
        Resultado resultado = resultadosRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resultado no encontrado"));

        resultado.setPartidaId(dto.getPartidaId());
        resultado.setGanadorId(Long.valueOf(dto.getGanadorId()));
        resultado.setPuntajeA(dto.getPuntajeA());
        resultado.setPuntajeB(dto.getPuntajeB());
        resultado.setEstadoValidacion(dto.getEstadoValidacion());
        resultado.setFechaRegistro(dto.getFechaRegistro());

        resultadosRepository.save(resultado);

        ResultadoResponseDTO response = new ResultadoResponseDTO();
        response.setResultadoId(resultado.getResultadoId());
        response.setPartidaId(resultado.getPartidaId());
        response.setGanadorId(resultado.getGanadorId());
        response.setPuntajeA(resultado.getPuntajeA());
        response.setPuntajeB(resultado.getPuntajeB());
        response.setEstadoValidacion(resultado.getEstadoValidacion());
        response.setFechaRegistro(resultado.getFechaRegistro());

        return response;
    }

    @Override
    public String validarResultado(Long id) {
        Resultado resultado = resultadosRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resultado no encontrado"));

        resultado.setEstadoValidacion("VALIDADO");
        resultadosRepository.save(resultado);

        return "Resultado validado";
    }

    @Override
    public String anularResultado(Long id) {
        Resultado resultado = resultadosRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resultado no encontrado"));

        resultado.setEstadoValidacion("ANULADO");

        resultadosRepository.save(resultado);

        return "Resultado anulado";
    }
}
