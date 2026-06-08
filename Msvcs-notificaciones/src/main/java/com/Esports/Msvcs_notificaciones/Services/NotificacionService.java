package com.Esports.Msvcs_notificaciones.Services;

import com.Esports.Msvcs_notificaciones.models.Notificacion;
import com.Esports.Msvcs_notificaciones.models.dtos.CrearNotificacionDTO;
import com.Esports.Msvcs_notificaciones.models.dtos.NotificacionResponseDTO;

import java.util.List;

public interface NotificacionService {
    NotificacionResponseDTO crearNotificacion(CrearNotificacionDTO dto);
    List<NotificacionResponseDTO> listarUsuario(Long usuarioId);
    NotificacionResponseDTO buscarNotificacion(Long id);
    String marcarLeida(Long id);
}
