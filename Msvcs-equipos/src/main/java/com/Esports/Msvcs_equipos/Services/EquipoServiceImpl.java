package com.Esports.Msvcs_equipos.Services;

import com.Esports.Msvcs_equipos.clients.JuegoClient;
import com.Esports.Msvcs_equipos.clients.UsuarioClient;
import com.Esports.Msvcs_equipos.exceptions.ResourceNotFoundException;
import com.Esports.Msvcs_equipos.models.Equipos;
import com.Esports.Msvcs_equipos.models.Miembro_equipo;
import com.Esports.Msvcs_equipos.models.dtos.AgregarMiembroDTO;
import com.Esports.Msvcs_equipos.models.dtos.CrearEquipoDTO;
import com.Esports.Msvcs_equipos.models.dtos.EquipoResponseDTO;
import com.Esports.Msvcs_equipos.models.dtos.MiembroEquipoResponseDTO;
import com.Esports.Msvcs_equipos.repositories.EquiposRepository;
import com.Esports.Msvcs_equipos.repositories.MiembroEquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class EquipoServiceImpl implements EquipoService {

    @Autowired
    private EquiposRepository equiposRepository;

    @Autowired
    private MiembroEquipoRepository miembroEquipoRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private JuegoClient juegoClient;

    @Override
    public EquipoResponseDTO crearEquipo(CrearEquipoDTO dto) {
        if (!usuarioClient.existeUsuario(dto.getCapitanId())){
            throw new ResourceNotFoundException("El usuario capitan no existe");
        }
        if (!juegoClient.existeJuego(dto.getJuegoprincipalId())){
            throw new ResourceNotFoundException("El juego principal no existe");
        }

        Equipos equipos = new Equipos();
        equipos.setNombreequipo(dto.getNombreequipo());
        equipos.setCapitanId(dto.getCapitanId());
        equipos.setJuegoprincipalId(dto.getJuegoprincipalId());
        equipos.setEstadoequipo("ACTIVO");

        equiposRepository.save(equipos);

        EquipoResponseDTO response = new EquipoResponseDTO();
        response.setEquipoId(equipos.getEquipoId());
        response.setNombreequipo(equipos.getNombreequipo());
        response.setCapitanId(equipos.getCapitanId());
        response.setJuegoprincipalId(equipos.getJuegoprincipalId());
        response.setEstadoequipo(equipos.getEstadoequipo());

        return response;
    }

    @Override
    public List<EquipoResponseDTO> listarEquipos() {
        return equiposRepository.findAll().stream().map(equipos -> {
            EquipoResponseDTO dto = new EquipoResponseDTO();
            dto.setEquipoId(equipos.getEquipoId());
            dto.setNombreequipo(equipos.getNombreequipo());
            dto.setCapitanId(equipos.getCapitanId());
            dto.setJuegoprincipalId(equipos.getJuegoprincipalId());
            dto.setEstadoequipo(equipos.getEstadoequipo());
            return dto;
        }).toList();
    }

    @Override
    public EquipoResponseDTO buscarEquipo(Long id) {
        Equipos equipos = equiposRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Equipo no encontrado"));

        EquipoResponseDTO dto = new EquipoResponseDTO();
        dto.setEquipoId(equipos.getEquipoId());
        dto.setNombreequipo(equipos.getNombreequipo());
        dto.setCapitanId(equipos.getCapitanId());
        dto.setJuegoprincipalId(equipos.getJuegoprincipalId());
        dto.setEstadoequipo(equipos.getEstadoequipo());

        return dto;
    }

    @Override
    public MiembroEquipoResponseDTO agregarMiembro(Long equipoId, AgregarMiembroDTO dto) {
        if (!usuarioClient.existeUsuario(dto.getUsuarioId())){
            throw new ResourceNotFoundException("El usuario no existe");
        }
        Boolean existe = miembroEquipoRepository.existsByEquipoIdAndUsuarioId(equipoId, dto.getUsuarioId());

        if (existe) {
            throw new RuntimeException("El usuario ya pertenece al equipo");
        }

        Miembro_equipo miembro = new Miembro_equipo();
        miembro.setEquipoId(equipoId);
        miembro.setUsuarioId(dto.getUsuarioId());
        miembro.setRolDentroEquipo(dto.getRolDentroEquipo());
        miembro.setFechaIngreso(LocalDateTime.now());

        miembroEquipoRepository.save(miembro);

        MiembroEquipoResponseDTO response = new MiembroEquipoResponseDTO();
        response.setMiembroId(miembro.getMiembroId());
        response.setEquipoId(miembro.getEquipoId());
        response.setUsuarioId(miembro.getUsuarioId());
        response.setRolDentroEquipo(miembro.getRolDentroEquipo());
        response.setFechaIngreso(miembro.getFechaIngreso());

        return response;
    }

    @Override
    public String eliminarMiembro(Long equipoId, Long usuarioId) {
        Miembro_equipo miembro = miembroEquipoRepository.findByEquipoIdAndUsuarioId(equipoId, usuarioId).orElseThrow(
                () -> new ResourceNotFoundException("Miembro no encontrado"));

        miembroEquipoRepository.delete(miembro);

        return "Miembro eliminado";
    }

    @Override
    public EquipoResponseDTO actualizarCapitan(Long equipoId, Long capitanId) {
        Equipos equipos = equiposRepository.findById(equipoId).orElseThrow(
                () -> new ResourceNotFoundException("Equipo no encontrado")
        );

        equipos.setCapitanId(capitanId);

        equiposRepository.save(equipos);

        EquipoResponseDTO response = new EquipoResponseDTO();
        response.setEquipoId(equipos.getEquipoId());
        response.setNombreequipo(equipos.getNombreequipo());
        response.setCapitanId(equipos.getCapitanId());
        response.setJuegoprincipalId(equipos.getJuegoprincipalId());
        response.setEstadoequipo(equipos.getEstadoequipo());

        return response;
    }

    @Override
    public String desactivarEquipo(Long id) {
        Equipos equipos = equiposRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Equipo no encontrado"));

        equipos.setEstadoequipo("INACTIVO");
        equiposRepository.save(equipos);

        return "Equipo desactivado correctamente";
    }
}
