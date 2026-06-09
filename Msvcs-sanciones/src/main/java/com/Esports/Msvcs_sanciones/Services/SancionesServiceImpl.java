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

    @Autowired
    private SancionesRepository sancionRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    @Override
    public SancionResponseDTO crearSancion(CrearSancionDTO dto) {
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

        return response;
    }

    @Override
    public List<SancionResponseDTO> listarSanciones() {
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
        Sancion sancion = sancionRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Sanción no encontrada"));

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
        Sancion sancion = sancionRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Sanción no encontrada"));

        sancion.setEstadoSancion("CERRADA");

        sancionRepository.save(sancion);

        return "Sanción cerrada";
    }

    @Override
    public Boolean validarSancionActiva(Long usuarioId) {
        return sancionRepository.existsByUsuarioIdAndEstadoSancion(usuarioId, "ACTIVA");
    }

    @Override
    public SancionResponseDTO actualizarSancion(Long id, ActualizarSancionDTO dto) {
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

        return response;
    }
}
