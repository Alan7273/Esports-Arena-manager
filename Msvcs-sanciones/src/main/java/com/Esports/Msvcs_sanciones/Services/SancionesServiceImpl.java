package com.Esports.Msvcs_sanciones.Services;

import com.Esports.Msvcs_sanciones.clients.UsuarioClient;
import com.Esports.Msvcs_sanciones.exceptions.ResourceNotFoundException;
import com.Esports.Msvcs_sanciones.models.Sancion;
import com.Esports.Msvcs_sanciones.models.dtos.ActualizarSancionDTO;
import com.Esports.Msvcs_sanciones.models.dtos.CrearSancionDTO;
import com.Esports.Msvcs_sanciones.models.dtos.SancionResponseDTO;
import com.Esports.Msvcs_sanciones.repositories.SancionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SancionesServiceImpl implements SancionesService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SancionesServiceImpl.class);
    @Autowired
    private SancionesRepository sancionRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    @Override
    public SancionResponseDTO crearSancion(CrearSancionDTO dto) {
        log.info("Creando sancion para usuarioId: {} - motivo: {}", dto.getUsuarioId(), dto.getMotivo());
        if(!usuarioClient.existeUsuario(dto.getUsuarioId())){
            throw new ResourceNotFoundException("El usuario sancionado no existe");
        }

        Sancion sancion = new Sancion();
        sancion.setUsuarioId(dto.getUsuarioId());
        sancion.setEquipoId(dto.getEquipoId());
        sancion.setMotivo(dto.getMotivo());
        sancion.setFechaInicio(dto.getFechaInicio());
        sancion.setFechaFin(dto.getFechaFin());
        sancion.setEstadoSancion(dto.getEstadoSancion());
        sancion.setSeveridad(dto.getSeveridad());

        sancionRepository.save(sancion);

        SancionResponseDTO response = new SancionResponseDTO();
        response.setSancionId(sancion.getSancionId());
        response.setUsuarioId(sancion.getUsuarioId());
        response.setEquipoId(sancion.getEquipoId());
        response.setMotivo(sancion.getMotivo());
        response.setFechaInicio(sancion.getFechaInicio());
        response.setFechaFin(sancion.getFechaFin());
        response.setEstadoSancion(sancion.getEstadoSancion());
        response.setSeveridad(sancion.getSeveridad());

        log.warn("Sancion creada con ID: {} para usuarioId: {}", sancion.getSancionId(), sancion.getUsuarioId());
        return response;
    }

    @Override
    public List<SancionResponseDTO> listarSanciones() {
        log.info("Listando todas las sanciones");
        return sancionRepository.findAll().stream().map(sancion -> {
            SancionResponseDTO dto = new SancionResponseDTO();
            dto.setSancionId(sancion.getSancionId());
            dto.setUsuarioId(sancion.getUsuarioId());
            dto.setEquipoId(sancion.getEquipoId());
            dto.setMotivo(sancion.getMotivo());
            dto.setFechaInicio(sancion.getFechaInicio());
            dto.setFechaFin(sancion.getFechaFin());
            dto.setEstadoSancion(sancion.getEstadoSancion());
            dto.setSeveridad(sancion.getSeveridad());

            return dto;

        }).toList();
    }

    @Override
    public SancionResponseDTO buscarSancion(Long id) {
        log.info("Buscando sancion con ID: {}", id);
        Sancion sancion = sancionRepository.findById(id).orElseThrow(() -> {
            log.warn("Sancion no encontrada con ID: {}", id);
            return new ResourceNotFoundException("Sanción no encontrada");
        });

        SancionResponseDTO dto = new SancionResponseDTO();
        dto.setSancionId(sancion.getSancionId());
        dto.setUsuarioId(sancion.getUsuarioId());
        dto.setEquipoId(sancion.getEquipoId());
        dto.setMotivo(sancion.getMotivo());
        sancion.setFechaInicio(sancion.getFechaInicio());
        sancion.setFechaFin(sancion.getFechaFin());
        sancion.setEstadoSancion(sancion.getEstadoSancion());
        sancion.setSeveridad(sancion.getSeveridad());

        return dto;
    }

    @Override
    public String cerrarSancion(Long id) {
        log.info("Cerrando sancion con ID: {}", id);
        Sancion sancion = sancionRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Sanción no encontrada"));

        sancion.setEstadoSancion("CERRADA");

        sancionRepository.save(sancion);

        log.info("Sancion ID: {} cerrada exitosamente", id);
        return "Sanción cerrada";
    }

    @Override
    public Boolean validarSancionActiva(Long usuarioId) {
        log.info("Validando sancion activa para usuarioId: {}", usuarioId);
        Boolean resultado = sancionRepository.existsByUsuarioIdAndEstadoSancion(usuarioId, "ACTIVA");
        log.info("Resultado validacion sancion usuarioId {}: {}", usuarioId, resultado);
        return resultado;
    }

    @Override
    public SancionResponseDTO actualizarSancion(Long id, ActualizarSancionDTO dto) {
        log.info("Actualizando sancion con ID: {}", id);
        Sancion sancion = sancionRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Sanción no encontrada"));

        if (dto.getMotivo() != null) sancion.setMotivo(dto.getMotivo());
        if (dto.getFechaInicio() != null) sancion.setFechaInicio(dto.getFechaInicio());
        if (dto.getFechaFin() != null) sancion.setFechaFin(dto.getFechaFin());
        if (dto.getEstadoSancion() != null) sancion.setEstadoSancion(dto.getEstadoSancion());
        if (dto.getSeveridad() != null) sancion.setSeveridad(dto.getSeveridad());

        sancionRepository.save(sancion);

        SancionResponseDTO response = new SancionResponseDTO();
        response.setSancionId(sancion.getSancionId());
        response.setUsuarioId(sancion.getUsuarioId());
        response.setEquipoId(sancion.getEquipoId());
        response.setMotivo(sancion.getMotivo());
        response.setFechaInicio(sancion.getFechaInicio());
        response.setFechaFin(sancion.getFechaFin());
        response.setEstadoSancion(sancion.getEstadoSancion());
        response.setSeveridad(sancion.getSeveridad());

        log.info("Sancion ID: {} actualizada exitosamente", id);
        return response;
    }
}
