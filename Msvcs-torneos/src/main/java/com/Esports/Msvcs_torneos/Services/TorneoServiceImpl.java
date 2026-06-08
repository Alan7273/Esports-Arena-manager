package com.Esports.Msvcs_torneos.Services;

import com.Esports.Msvcs_torneos.exceptions.ResourceNotFoundException;
import com.Esports.Msvcs_torneos.models.Torneo;
import com.Esports.Msvcs_torneos.models.dtos.ActualizarTorneoDTO;
import com.Esports.Msvcs_torneos.models.dtos.CrearTorneoDTO;
import com.Esports.Msvcs_torneos.models.dtos.TorneoResponseDTO;
import com.Esports.Msvcs_torneos.repositories.TorneosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TorneoServiceImpl implements TorneoService {

    @Autowired
    private TorneosRepository torneoRepository;

    @Override
    public TorneoResponseDTO crearTorneo(CrearTorneoDTO dto) {
        Torneo torneo = new Torneo();
        torneo.setNombretorneo(dto.getNombretorneo());
        torneo.setJuegoId(dto.getJuegoId());
        torneo.setFechaInicio(dto.getFechaInicio());
        torneo.setFechaFin(dto.getFechaFin());
        torneo.setCupoMaximo(dto.getCupoMaximo());
        torneo.setModalidadTorneo(dto.getModalidadTorneo());
        torneo.setEstadoTorneo("ACTIVO");

        torneoRepository.save(torneo);

        TorneoResponseDTO response = new TorneoResponseDTO();
        response.setTorneoId(torneo.getTorneoId());
        response.setNombretorneo(torneo.getNombretorneo());
        response.setJuegoId(torneo.getJuegoId());
        response.setFechaInicio(torneo.getFechaInicio());
        response.setFechaFin(torneo.getFechaFin());
        response.setCupoMaximo(torneo.getCupoMaximo());
        response.setModalidadTorneo(torneo.getModalidadTorneo());
        response.setEstadoTorneo(torneo.getEstadoTorneo());

        return response;
    }

    @Override
    public List<TorneoResponseDTO> listarTorneos() {
        List<Torneo> torneos = torneoRepository.findAll();
        return torneos.stream().map(torneo -> {
            TorneoResponseDTO dto = new TorneoResponseDTO();
            dto.setTorneoId(torneo.getTorneoId());
            dto.setNombretorneo(torneo.getNombretorneo());
            dto.setJuegoId(torneo.getJuegoId());
            dto.setFechaInicio(torneo.getFechaInicio());
            dto.setFechaFin(torneo.getFechaFin());
            dto.setCupoMaximo(torneo.getCupoMaximo());
            dto.setModalidadTorneo(torneo.getModalidadTorneo());
            dto.setEstadoTorneo(torneo.getEstadoTorneo());
            return dto;
        }).toList();
    }

    @Override
    public TorneoResponseDTO buscarTorneo(Long id) {
        Torneo torneo = torneoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Torneo no encontrado"));

        TorneoResponseDTO dto = new TorneoResponseDTO();
        dto.setTorneoId(torneo.getTorneoId());
        dto.setNombretorneo(torneo.getNombretorneo());
        dto.setJuegoId(torneo.getJuegoId());
        dto.setFechaInicio(torneo.getFechaInicio());
        dto.setFechaFin(torneo.getFechaFin());
        dto.setCupoMaximo(torneo.getCupoMaximo());
        dto.setModalidadTorneo(torneo.getModalidadTorneo());
        dto.setEstadoTorneo(torneo.getEstadoTorneo());

        return dto;
    }

    @Override
    public TorneoResponseDTO actualizarTorneo(Long id, ActualizarTorneoDTO dto) {
        Torneo torneo = torneoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Torneo no encontrado"));

        torneo.setNombretorneo(dto.getNombretorneo());
        torneo.setJuegoId(dto.getJuegoId());
        torneo.setFechaInicio(dto.getFechaInicio());
        torneo.setFechaFin(dto.getFechaFin());
        torneo.setCupoMaximo(dto.getCupoMaximo());
        torneo.setModalidadTorneo(dto.getModalidadTorneo());
        torneo.setEstadoTorneo(dto.getEstadoTorneo());

        torneoRepository.save(torneo);

        TorneoResponseDTO response = new TorneoResponseDTO();
        response.setTorneoId(torneo.getTorneoId());
        response.setNombretorneo(torneo.getNombretorneo());
        response.setJuegoId(torneo.getJuegoId());
        response.setFechaInicio(torneo.getFechaInicio());
        response.setFechaFin(torneo.getFechaFin());
        response.setCupoMaximo(torneo.getCupoMaximo());
        response.setModalidadTorneo(torneo.getModalidadTorneo());
        response.setEstadoTorneo(torneo.getEstadoTorneo());

        return response;
    }

    @Override
    public String cerrarTorneo(Long id) {
        Torneo torneo = torneoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Torneo no encontrado"));

        torneo.setEstadoTorneo("CERRADO");
        torneoRepository.save(torneo);

        return "Torneo cerrado correctamente";
    }

    @Override
    public String cancelarTorneo(Long id) {
        Torneo torneo = torneoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Torneo no encontrado"));

        torneo.setEstadoTorneo("CANCELADO");
        torneoRepository.save(torneo);

        return "Torneo cancelado correctamente";
    }
}
