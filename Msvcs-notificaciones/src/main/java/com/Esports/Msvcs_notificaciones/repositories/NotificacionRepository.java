package com.Esports.Msvcs_notificaciones.repositories;

import com.Esports.Msvcs_notificaciones.models.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    // Metodo que me permite mostrar al usuario por el id en la notificacion
    List<Notificacion> findByUsuarioId(Long usuarioId);
    // Metodo que me permite saber si fue leida la notificacion
    List<Notificacion> findByLeidaNotificacion(Boolean leida);
    // Metodo que me permite Buscar la notificacion por el id del usuario y saber si esta leida
    List<Notificacion> findByUsuarioIdAndLeidaNotificacion(Long usuarioId, Boolean leida);
}
