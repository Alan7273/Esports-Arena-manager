package com.Esports.Msvcs_premios.Services;

import com.Esports.Msvcs_premios.exceptions.ResourceNotFoundException;
import com.Esports.Msvcs_premios.models.Premio;
import com.Esports.Msvcs_premios.models.Premio_asignado;
import com.Esports.Msvcs_premios.models.dtos.ActualizarPremioDTO;
import com.Esports.Msvcs_premios.models.dtos.CrearPremioDTO;
import com.Esports.Msvcs_premios.models.dtos.PremioResponseDTO;
import com.Esports.Msvcs_premios.repositories.PremioAsignadoRepository;
import com.Esports.Msvcs_premios.repositories.PremioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class PremioServiceImpl implements PremioService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PremioServiceImpl.class);
    @Autowired
    private PremioRepository premioRepository;

    @Autowired
    private PremioAsignadoRepository premioAsignadoRepository;

    @Override
    public PremioResponseDTO crearPremio(CrearPremioDTO dto) {
        log.info("Creando premio para torneoId: {} - posicion: {}", dto.getTorneoId(), dto.getPosicion());
        Premio premio = new Premio();
        premio.setTorneoId(dto.getTorneoId());
        premio.setPosicion(dto.getPosicion());
        premio.setDescripcion(dto.getDescripcion());
        premio.setValor(dto.getValor());
        premio.setEstado("PENDIENTE"); // ← faltaba

        premioRepository.save(premio);

        PremioResponseDTO response = new PremioResponseDTO();
        response.setPremioId(premio.getPremioId());
        response.setTorneoId(premio.getTorneoId());
        response.setPosicion(premio.getPosicion());
        response.setDescripcion(premio.getDescripcion());
        response.setValor(premio.getValor());
        response.setEstado(premio.getEstado());

        log.info("Premio creado exitosamente con ID: {}", premio.getPremioId());
        return response;
    }

    @Override
    public List<PremioResponseDTO> listarPremios() {
        log.info("Listando todos los premios");
        return premioRepository.findAll().stream().map(premio -> {
            PremioResponseDTO dto = new PremioResponseDTO();
            dto.setPremioId(premio.getPremioId());
            dto.setTorneoId(premio.getTorneoId());
            dto.setPosicion(premio.getPosicion());
            dto.setDescripcion(premio.getDescripcion());
            dto.setValor(premio.getValor());
            dto.setEstado(premio.getEstado());
            return dto;
        }).toList();
    }

    @Override
    public PremioResponseDTO buscarPremio(Long id) {
        log.info("Buscando premio con ID: {}", id);
        Premio premio = premioRepository.findById(id).orElseThrow(() -> {
            log.warn("Premio no encontrado con ID: {}", id);
            return new ResourceNotFoundException("Premio no encontrado");
        });

        PremioResponseDTO dto = new PremioResponseDTO();
        dto.setPremioId(premio.getPremioId());
        dto.setTorneoId(premio.getTorneoId());
        dto.setPosicion(premio.getPosicion());
        dto.setDescripcion(premio.getDescripcion());
        dto.setValor(premio.getValor());
        dto.setEstado(premio.getEstado());

        return dto;
    }

    @Override
    public PremioResponseDTO actualizarPremio(Long id, ActualizarPremioDTO dto) {
        log.info("Actualizando premio con ID: {}", id);
        Premio premio = premioRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Premio no encontrado"));

        premio.setTorneoId(dto.getTorneoId());
        premio.setPosicion(dto.getPosicion());
        premio.setDescripcion(dto.getDescripcion());
        premio.setValor(dto.getValor());
        premio.setEstado(dto.getEstado());

        premioRepository.save(premio);

        PremioResponseDTO response = new PremioResponseDTO();
        response.setPremioId(premio.getPremioId());
        response.setTorneoId(premio.getTorneoId());
        response.setPosicion(premio.getPosicion());
        response.setDescripcion(premio.getDescripcion());
        response.setValor(premio.getValor());
        response.setEstado(premio.getEstado());

        log.info("Premio ID: {} actualizado exitosamente", id);
        return response;
    }

    @Override
    public String asignarPremio(Long id, Long participanteId) {
        log.info("Asignando premio ID: {} al participanteId: {}", id, participanteId);
        Premio premio = premioRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Premio no encontrado"));

        Premio_asignado asignado = new Premio_asignado();
        asignado.setPremioId(premio.getPremioId());
        asignado.setParticipanteId(participanteId);
        asignado.setFechaAsignacion(LocalDate.now());

        premioAsignadoRepository.save(asignado);

        premio.setEstado("ASIGNADO");
        premioRepository.save(premio);

        log.info("Premio ID: {} asignado al participanteId: {}", id, participanteId);
        return "Premio asignado correctamente";
    }
}