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
import com.Esports.Msvcs_torneos.exceptions.BadRequestException;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class TorneoServiceImpl implements TorneoService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TorneoServiceImpl.class);

    @Autowired
    private TorneosRepository torneoRepository;

    @Override
    public TorneoResponseDTO crearTorneo(CrearTorneoDTO dto) {
        log.info("Creando torneo con nombre: {}", dto.getNombretorneo());
        // Regla: fechaFin debe ser posterior a fechaInicio
        if (!dto.getFechaFin().isAfter(dto.getFechaInicio())) {
            throw new BadRequestException("La fecha de fin debe ser posterior a la fecha de inicio");
        }
        // Regla: el torneo no puede comenzar en el pasado
        if (dto.getFechaInicio().isBefore(LocalDate.now())) {
            throw new BadRequestException("La fecha de inicio no puede ser en el pasado");
        }

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

        log.info("Torneo creado exitosamente con ID: {}", torneo.getTorneoId());
        return response;
    }

    @Override
    public List<TorneoResponseDTO> listarTorneos() {
        log.info("Listando todos los torneos");
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
        log.info("Buscando torneo con ID: {}", id);
        Torneo torneo = torneoRepository.findById(id).orElseThrow(() -> {
            log.warn("Torneo no encontrado con ID: {}", id);
            return new ResourceNotFoundException("Torneo no encontrado");
        });

        TorneoResponseDTO dto = new TorneoResponseDTO();
        dto.setTorneoId(torneo.getTorneoId());
        dto.setNombretorneo(torneo.getNombretorneo());
        dto.setJuegoId(torneo.getJuegoId());
        dto.setFechaInicio(torneo.getFechaInicio());
        dto.setFechaFin(torneo.getFechaFin());
        dto.setCupoMaximo(torneo.getCupoMaximo());
        dto.setModalidadTorneo(torneo.getModalidadTorneo());
        dto.setEstadoTorneo(torneo.getEstadoTorneo());

        log.info("Torneo encontrado: {}", torneo.getNombretorneo());
        return dto;
    }

    @Override
    public TorneoResponseDTO actualizarTorneo(Long id, ActualizarTorneoDTO dto) {
        log.info("Actualizando torneo con ID: {}", id);
        Torneo torneo = torneoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Torneo no encontrado"));

        // Regla: no se puede modificar un torneo cerrado o cancelado
        if (torneo.getEstadoTorneo().equals("CERRADO") || torneo.getEstadoTorneo().equals("CANCELADO")) {
            throw new BadRequestException("No se puede modificar un torneo " + torneo.getEstadoTorneo());
        }
        // Validar fechas solo si vienen en el DTO
        LocalDate inicio = dto.getFechaInicio() != null ? dto.getFechaInicio() : torneo.getFechaInicio();
        LocalDate fin = dto.getFechaFin() != null ? dto.getFechaFin() : torneo.getFechaFin();
        if (!fin.isAfter(inicio)) {
            throw new BadRequestException("La fecha de fin debe ser posterior a la fecha de inicio");
        }

        if (dto.getNombretorneo() != null) torneo.setNombretorneo(dto.getNombretorneo());
        if (dto.getJuegoId() != null) torneo.setJuegoId(dto.getJuegoId());
        if (dto.getFechaInicio() != null) torneo.setFechaInicio(dto.getFechaInicio());
        if (dto.getFechaFin() != null) torneo.setFechaFin(dto.getFechaFin());
        if (dto.getCupoMaximo() != null) torneo.setCupoMaximo(dto.getCupoMaximo());
        if (dto.getModalidadTorneo() != null) torneo.setModalidadTorneo(dto.getModalidadTorneo());
        if (dto.getEstadoTorneo() != null) torneo.setEstadoTorneo(dto.getEstadoTorneo());

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

        log.info("Torneo actualizado exitosamente con ID: {}", id);
        return response;
    }

    @Override
    public String cerrarTorneo(Long id) {
        log.info("Cerrando torneo con ID: {}", id);
        Torneo torneo = torneoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Torneo no encontrado"));

        torneo.setEstadoTorneo("CERRADO");
        torneoRepository.save(torneo);

        log.info("Torneo cerrado exitosamente con ID: {}", id);
        return "Torneo cerrado correctamente";
    }

    @Override
    public String cancelarTorneo(Long id) {
        log.info("Cancelando torneo con ID: {}", id);
        Torneo torneo = torneoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Torneo no encontrado"));

        torneo.setEstadoTorneo("CANCELADO");
        torneoRepository.save(torneo);

        log.warn("Torneo cancelado con ID: {}", id);
        return "Torneo cancelado correctamente";
    }
}
