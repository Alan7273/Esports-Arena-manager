package com.Esports.Msvcs_inscripciones.Services;

import com.Esports.Msvcs_inscripciones.clients.EquipoClient;
import com.Esports.Msvcs_inscripciones.clients.SancionClient;
import com.Esports.Msvcs_inscripciones.clients.TorneoClient;
import com.Esports.Msvcs_inscripciones.exceptions.BadRequestException;
import com.Esports.Msvcs_inscripciones.exceptions.DuplicateResourceException;
import com.Esports.Msvcs_inscripciones.exceptions.ResourceNotFoundException;
import com.Esports.Msvcs_inscripciones.models.Inscripcion;
import com.Esports.Msvcs_inscripciones.models.dtos.CrearInscripcionDTO;
import com.Esports.Msvcs_inscripciones.models.dtos.InscripcionResponseDTO;
import com.Esports.Msvcs_inscripciones.models.dtos.TorneoResponseDTO;
import com.Esports.Msvcs_inscripciones.repositories.InscripcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InscripcionesServiceImpl implements InscripcionesService {

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Autowired
    private TorneoClient torneoClient;

    @Autowired
    private EquipoClient equipoClient;

    @Autowired
    private SancionClient sancionClient;

    @Override
    public InscripcionResponseDTO crearInscripcion(CrearInscripcionDTO dto) {
        if (!torneoClient.existeTorneo(dto.getTorneoId())) {
            throw new ResourceNotFoundException("El torneo no existe");
        }
        if (dto.getEquipoId() != null && !equipoClient.existeEquipo(dto.getEquipoId())) {
            throw new ResourceNotFoundException("El equipo no existe");
        }
        if (dto.getUsuarioId() != null && sancionClient.tieneSancionActiva(dto.getUsuarioId())) {
            throw new RuntimeException("El usuario tiene una sanción activa");
        }
        TorneoResponseDTO torneo = torneoClient.buscarTorneo(dto.getTorneoId());
        if (!torneo.getEstadoTorneo().equals("ACTIVO")) {
            throw new BadRequestException("Solo se puede inscribir en torneos ACTIVOS");
        }

        Long inscritos = inscripcionRepository.countByTorneoIdAndEstadoNot(dto.getTorneoId(), "CANCELADA");
        if (inscritos >= torneo.getCupoMaximo()) {
            throw new BadRequestException("El torneo no tiene cupos disponibles");
        }

        if (dto.getUsuarioId() != null &&
                inscripcionRepository.existsByTorneoIdAndUsuarioId(dto.getTorneoId(), dto.getUsuarioId())) {
            throw new DuplicateResourceException("El usuario ya está inscrito en este torneo");
        }
        if (dto.getEquipoId() != null &&
                inscripcionRepository.existsByTorneoIdAndEquipoId(dto.getTorneoId(), dto.getEquipoId())) {
            throw new DuplicateResourceException("El equipo ya está inscrito en este torneo");
        }
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setTorneoId(dto.getTorneoId());
        inscripcion.setEquipoId(dto.getEquipoId());
        inscripcion.setUsuarioId(dto.getUsuarioId());
        inscripcion.setNombreJugador(dto.getNombreJugador());
        inscripcion.setTipoParticipante(dto.getTipoParticipante());
        inscripcion.setEstado("PENDIENTE");
        inscripcion.setFechaInscripcion(dto.getFechaInscripcion());

        inscripcionRepository.save(inscripcion);

        InscripcionResponseDTO response = new InscripcionResponseDTO();
        response.setInscripcionId(inscripcion.getInscripcionId());
        response.setTorneoId(inscripcion.getTorneoId());
        response.setEquipoId(inscripcion.getEquipoId());
        response.setUsuarioId(inscripcion.getUsuarioId());
        response.setNombreJugador(inscripcion.getNombreJugador());
        response.setTipoParticipante(inscripcion.getTipoParticipante());
        response.setEstado(inscripcion.getEstado());
        response.setFechaInscripcion(inscripcion.getFechaInscripcion());

        return response;
    }

    @Override
    public List<InscripcionResponseDTO> listarInscripciones() {
        List<Inscripcion> inscripcions = inscripcionRepository.findAll();
        return inscripcions.stream().map(inscripcion -> {
            InscripcionResponseDTO dto = new InscripcionResponseDTO();
            dto.setInscripcionId(inscripcion.getInscripcionId());
            dto.setTorneoId(inscripcion.getTorneoId());
            dto.setEquipoId(inscripcion.getEquipoId());
            dto.setUsuarioId(inscripcion.getUsuarioId());
            dto.setNombreJugador(inscripcion.getNombreJugador());
            dto.setTipoParticipante(inscripcion.getTipoParticipante());
            dto.setEstado(inscripcion.getEstado());
            dto.setFechaInscripcion(inscripcion.getFechaInscripcion());
            return dto;
        }).toList();
    }

    @Override
    public InscripcionResponseDTO buscarInscripcion(Long id) {
        Inscripcion inscripcion = inscripcionRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Inscripcion no encontrada"));

        InscripcionResponseDTO dto = new InscripcionResponseDTO();
        dto.setInscripcionId(inscripcion.getInscripcionId());
        dto.setTorneoId(inscripcion.getTorneoId());
        dto.setEquipoId(inscripcion.getEquipoId());
        dto.setUsuarioId(inscripcion.getUsuarioId());
        dto.setNombreJugador(inscripcion.getNombreJugador());
        dto.setTipoParticipante(inscripcion.getTipoParticipante());
        dto.setEstado(inscripcion.getEstado());
        dto.setFechaInscripcion(inscripcion.getFechaInscripcion());

        return  dto;
    }

    @Override
    public InscripcionResponseDTO actualizarEstado(Long id, String estado) {
        Inscripcion inscripcion = inscripcionRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Inscripcion no encontrada"));

        inscripcion.setEstado(estado);
        inscripcionRepository.save(inscripcion);

        InscripcionResponseDTO dto = new InscripcionResponseDTO();
        dto.setInscripcionId(inscripcion.getInscripcionId());
        dto.setTorneoId(inscripcion.getTorneoId());
        dto.setEquipoId(inscripcion.getEquipoId());
        dto.setUsuarioId(inscripcion.getUsuarioId());
        dto.setNombreJugador(inscripcion.getNombreJugador());
        dto.setTipoParticipante(inscripcion.getTipoParticipante());
        dto.setEstado(estado);
        dto.setFechaInscripcion(inscripcion.getFechaInscripcion());

        return  dto;
    }

    @Override
    public String cancelarInscripcion(Long id) {
        Inscripcion inscripcion = inscripcionRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Inscripcion no encontrada"));

        inscripcion.setEstado("CANCELADA");
        inscripcionRepository.save(inscripcion);

        return "Inscripcion cancelada";
    }
}
