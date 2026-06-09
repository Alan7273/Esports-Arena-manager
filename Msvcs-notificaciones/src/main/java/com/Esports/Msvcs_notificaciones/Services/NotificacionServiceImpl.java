package com.Esports.Msvcs_notificaciones.Services;

import com.Esports.Msvcs_notificaciones.clients.UsuarioClient;
import com.Esports.Msvcs_notificaciones.exceptions.ResourceNotFoundException;
import com.Esports.Msvcs_notificaciones.models.Notificacion;
import com.Esports.Msvcs_notificaciones.models.dtos.CrearNotificacionDTO;
import com.Esports.Msvcs_notificaciones.models.dtos.NotificacionResponseDTO;
import com.Esports.Msvcs_notificaciones.repositories.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class NotificacionServiceImpl implements NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    @Override
    public NotificacionResponseDTO crearNotificacion(CrearNotificacionDTO dto) {
        if (!usuarioClient.existeUsuario(dto.getUsuarioId())){
            throw new ResourceNotFoundException("El usuario destinatario no existe");
        }
        Notificacion notificacion = new Notificacion();

        notificacion.setUsuarioId(dto.getUsuarioId());
        notificacion.setEquipoId(dto.getEquipoId());
        notificacion.setTipo(dto.getTipo());
        notificacion.setMensaje(dto.getMensaje());
        notificacion.setLeidaNotificacion(false);
        notificacion.setFecha(LocalDate.now());

        notificacionRepository.save(notificacion);

        NotificacionResponseDTO response = new NotificacionResponseDTO();

        response.setNotificacionId(notificacion.getNotificacionId());
        response.setUsuarioId(notificacion.getUsuarioId());
        response.setEquipoId(notificacion.getEquipoId());
        response.setTipo(notificacion.getTipo());
        response.setMensaje(notificacion.getMensaje());
        response.setLeidaNotificacion(notificacion.getLeidaNotificacion());
        response.setFecha(notificacion.getFecha());

        return response;
    }

    @Override
    public List<NotificacionResponseDTO> listarUsuario(Long usuarioId) {
        List<Notificacion> notificaciones = notificacionRepository.findByUsuarioId(usuarioId);

        return notificaciones.stream().map(notificacion -> {
            NotificacionResponseDTO dto = new NotificacionResponseDTO();

            dto.setNotificacionId(notificacion.getNotificacionId());
            dto.setUsuarioId(notificacion.getUsuarioId());
            dto.setEquipoId(notificacion.getEquipoId());
            dto.setTipo(notificacion.getTipo());
            dto.setMensaje(notificacion.getMensaje());
            dto.setLeidaNotificacion(notificacion.getLeidaNotificacion());
            dto.setFecha(notificacion.getFecha());

            return dto;
        }).toList();
    }

    @Override
    public NotificacionResponseDTO buscarNotificacion(Long notificacionId) {
        Notificacion notificacion = notificacionRepository.findById(notificacionId).orElseThrow(
                () -> new ResourceNotFoundException("Notificación no encontrada"));

        NotificacionResponseDTO dto = new NotificacionResponseDTO();

        dto.setNotificacionId(notificacion.getNotificacionId());
        dto.setUsuarioId(notificacion.getUsuarioId());
        dto.setEquipoId(notificacion.getEquipoId());
        dto.setTipo(notificacion.getTipo());
        dto.setMensaje(notificacion.getMensaje());
        dto.setLeidaNotificacion(notificacion.getLeidaNotificacion());
        dto.setFecha(notificacion.getFecha());

        return dto;
    }

    @Override
    public String marcarLeida(Long notificacionId) {
        Notificacion notificacion = notificacionRepository.findById(notificacionId).orElseThrow(
                () -> new ResourceNotFoundException("Notificación no encontrada"));

        notificacion.setLeidaNotificacion(true);

        notificacionRepository.save(notificacion);

        return "Notificación marcada como leída";
    }

    @Override
    public String eliminarNotificacion(Long id) {
        Notificacion notificacion = notificacionRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Notificación no encontrada"));

        notificacionRepository.delete(notificacion);

        return "Notificación eliminada";
    }
}
