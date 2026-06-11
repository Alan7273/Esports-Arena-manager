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
import com.Esports.Msvcs_resultados.clients.PartidaClient;
import com.Esports.Msvcs_resultados.exceptions.BadRequestException;
import com.Esports.Msvcs_resultados.models.dtos.PartidaResponseDTO;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ResultadoServiceImpl implements ResultadoService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ResultadoServiceImpl.class);
    @Autowired
    private ResultadosRepository resultadosRepository;

    @Autowired
    private PartidaClient partidaClient;

    @Override
    public ResultadoResponseDTO registrarResultado(CrearResultadoDTO dto) {
        log.info("Registrando resultado para partidaId: {} - ganadorId: {}", dto.getPartidaId(), dto.getGanadorId());
        // Regla: la partida debe existir y estar FINALIZADA o EN_CURSO
        PartidaResponseDTO partida = partidaClient.buscarPartida(dto.getPartidaId());
        if (partida.getEstadopartida().equals("CANCELADA")) {
            throw new BadRequestException("No se puede registrar resultado de una partida cancelada");
        }
        // Regla: el ganador debe ser uno de los dos participantes
        boolean ganadorValido = dto.getGanadorId().equals(partida.getParticipanteAId())
                || dto.getGanadorId().equals(partida.getParticipanteBId());
        if (!ganadorValido) {
            throw new BadRequestException("El ganador debe ser uno de los participantes de la partida");
        }
        // Regla: no registrar resultado duplicado para la misma partida
        if (resultadosRepository.existsByPartidaId(dto.getPartidaId())) {
            throw new BadRequestException("Ya existe un resultado registrado para esta partida");
        }
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

        log.info("Resultado registrado exitosamente con ID: {}", resultado.getResultadoId());
        return response;
    }

    @Override
    public List<ResultadoResponseDTO> listarResultados() {
        log.info("Listando todos los resultados");
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
        log.info("Buscando resultado con ID: {}", id);
        Resultado resultado = resultadosRepository.findById(id).orElseThrow(() -> {
            log.warn("Resultado no encontrado con ID: {}", id);
            return new ResourceNotFoundException("Resultado no encontrado");
        });

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
        log.info("Actualizando resultado con ID: {}", id);
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

        log.info("Resultado ID: {} actualizado exitosamente", id);
        return response;
    }

    @Override
    public String validarResultado(Long id) {
        log.info("Validando resultado con ID: {}", id);
        Resultado resultado = resultadosRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resultado no encontrado"));

        resultado.setEstadoValidacion("VALIDADO");
        resultadosRepository.save(resultado);

        log.info("Resultado ID: {} marcado como VALIDADO", id);
        return "Resultado validado";
    }

    @Override
    public String anularResultado(Long id) {
        log.warn("Anulando resultado con ID: {}", id);
        Resultado resultado = resultadosRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resultado no encontrado"));

        resultado.setEstadoValidacion("ANULADO");

        resultadosRepository.save(resultado);

        log.warn("Resultado ID: {} anulado", id);
        return "Resultado anulado";
    }
}
