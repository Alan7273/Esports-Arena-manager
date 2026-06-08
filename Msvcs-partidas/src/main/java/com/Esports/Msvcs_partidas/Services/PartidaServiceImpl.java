package com.Esports.Msvcs_partidas.Services;

import com.Esports.Msvcs_partidas.clients.InscripcionClient;
import com.Esports.Msvcs_partidas.clients.TorneoClient;
import com.Esports.Msvcs_partidas.exceptions.ResourceNotFoundException;
import com.Esports.Msvcs_partidas.models.Partida;
import com.Esports.Msvcs_partidas.models.dtos.CrearPartidaDTO;
import com.Esports.Msvcs_partidas.models.dtos.PartidaResponseDTO;
import com.Esports.Msvcs_partidas.repositories.PartidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PartidaServiceImpl implements PartidaService {

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private TorneoClient torneoClient;

    @Autowired
    private InscripcionClient inscripcionClient;

    @Override
    public PartidaResponseDTO crearPartida(CrearPartidaDTO dto) {
        if (!torneoClient.existeTorneo(dto.getTorneoId())) {
            throw new ResourceNotFoundException("El torneo no existe");
        }
        if (!inscripcionClient.existeParticipante(dto.getTorneoId(), dto.getParticipanteAId())) {
            throw new ResourceNotFoundException("El participante A no está inscrito en el torneo");
        }
        if (!inscripcionClient.existeParticipante(dto.getTorneoId(), dto.getParticipanteBId())) {
            throw new ResourceNotFoundException("El participante B no está inscrito en el torneo");
        }
hola
        Partida partida = new Partida();
        partida.setTorneoId(dto.getTorneoId());
        partida.setParticipanteAId(dto.getParticipanteAId());
        partida.setParticipanteBId(dto.getParticipanteBId());
        partida.setRonda(dto.getRonda());
        partida.setFechaHora(dto.getFechaHora());
        partida.setEstadopartida("PROGRAMADA");

        partidaRepository.save(partida);

        PartidaResponseDTO response = new PartidaResponseDTO();

        response.setPartidaId(partida.getPartidaId());
        response.setTorneoId(partida.getTorneoId());
        response.setParticipanteAId(partida.getParticipanteAId());
        response.setParticipanteBId(partida.getParticipanteBId());
        response.setRonda(partida.getRonda());
        response.setFechaHora(partida.getFechaHora());
        response.setEstadopartida(partida.getEstadopartida());

        return response;
    }

    @Override
    public List<PartidaResponseDTO> listarPartidas() {
        List<Partida> partidas = partidaRepository.findAll();

        return partidas.stream().map(partida -> {
            PartidaResponseDTO dto = new PartidaResponseDTO();

            dto.setPartidaId(partida.getPartidaId());
            dto.setTorneoId(partida.getTorneoId());
            dto.setParticipanteAId(partida.getParticipanteAId());
            dto.setParticipanteBId(partida.getParticipanteBId());
            dto.setRonda(partida.getRonda());
            dto.setFechaHora(partida.getFechaHora());
            dto.setEstadopartida(partida.getEstadopartida());

            return dto;
        }).toList();
    }

    @Override
    public PartidaResponseDTO buscarPartida(Long partidaId) {
        Partida partida = partidaRepository.findById(partidaId).orElseThrow(
                () -> new ResourceNotFoundException("Partida no encontrada"));

        PartidaResponseDTO dto = new PartidaResponseDTO();

        dto.setPartidaId(partida.getPartidaId());
        dto.setTorneoId(partida.getTorneoId());
        dto.setParticipanteAId(partida.getParticipanteAId());
        dto.setParticipanteBId(partida.getParticipanteBId());
        dto.setRonda(partida.getRonda());
        dto.setFechaHora(partida.getFechaHora());
        dto.setEstadopartida(partida.getEstadopartida());

        return dto;
    }

    @Override
    public PartidaResponseDTO actualizarHorario(Long partidaId, LocalDateTime fechaHora) {
        Partida partida = partidaRepository.findById(partidaId).orElseThrow(
                () -> new ResourceNotFoundException("Partida no encontrada"));

        partida.setFechaHora(fechaHora);

        partidaRepository.save(partida);

        PartidaResponseDTO dto = new PartidaResponseDTO();

        dto.setPartidaId(partida.getPartidaId());
        dto.setTorneoId(partida.getTorneoId());
        dto.setParticipanteAId(partida.getParticipanteAId());
        dto.setParticipanteBId(partida.getParticipanteBId());
        dto.setRonda(partida.getRonda());
        dto.setFechaHora(partida.getFechaHora());
        dto.setEstadopartida(partida.getEstadopartida());

        return dto;
    }

    @Override
    public String cancelarPartida(Long partidaId) {
        Partida partida = partidaRepository.findById(partidaId).orElseThrow(
                () -> new ResourceNotFoundException("Partida no encontrada"));

        partida.setEstadopartida("CANCELADA");

        partidaRepository.save(partida);

        return "Partida cancelada correctamente";
    }
}