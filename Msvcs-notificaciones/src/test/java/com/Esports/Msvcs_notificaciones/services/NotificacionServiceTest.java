package com.Esports.Msvcs_notificaciones.services;

import com.Esports.Msvcs_notificaciones.Services.NotificacionServiceImpl;
import com.Esports.Msvcs_notificaciones.clients.UsuarioClient;
import com.Esports.Msvcs_notificaciones.exceptions.ResourceNotFoundException;
import com.Esports.Msvcs_notificaciones.models.Notificacion;
import com.Esports.Msvcs_notificaciones.models.dtos.CrearNotificacionDTO;
import com.Esports.Msvcs_notificaciones.models.dtos.NotificacionResponseDTO;
import com.Esports.Msvcs_notificaciones.repositories.NotificacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificacionServiceTest {

    @Mock
    private NotificacionRepository notificacionRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private NotificacionServiceImpl notificacionService;

    private Notificacion notificacionMock;

    @BeforeEach
    public void setUp() {
        notificacionMock = new Notificacion();
        notificacionMock.setNotificacionId(1L);
        notificacionMock.setUsuarioId(100L);
        notificacionMock.setTipo("INFO");
        notificacionMock.setMensaje("Tu partida comienza en 30 minutos");
        notificacionMock.setLeidaNotificacion(false);
        notificacionMock.setFecha(LocalDate.now());
    }

    @Test
    @DisplayName("shouldCreateNotificacionSuccessfully")
    public void shouldCreateNotificacionSuccessfully() {
        CrearNotificacionDTO dto = new CrearNotificacionDTO();
        dto.setUsuarioId(100L);
        dto.setMensaje("Tu partida comienza en 30 minutos");
        dto.setTipo("INFO");

        when(usuarioClient.existeUsuario(100L)).thenReturn(true);
        when(notificacionRepository.save(any(Notificacion.class))).thenReturn(notificacionMock);

        NotificacionResponseDTO result = notificacionService.crearNotificacion(dto);

        assertThat(result.getMensaje()).isEqualTo("Tu partida comienza en 30 minutos");
        assertThat(result.getLeidaNotificacion()).isFalse();
        verify(notificacionRepository, times(1)).save(any(Notificacion.class));
    }

    @Test
    @DisplayName("shouldThrowExceptionWhenUsuarioDoesNotExist")
    public void shouldThrowExceptionWhenUsuarioDoesNotExist() {
        CrearNotificacionDTO dto = new CrearNotificacionDTO();
        dto.setUsuarioId(999L);
        dto.setMensaje("Tu partida comienza en 30 minutos");
        dto.setTipo("INFO");

        when(usuarioClient.existeUsuario(999L)).thenReturn(false);

        assertThatThrownBy(() -> notificacionService.crearNotificacion(dto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("El usuario destinatario no existe");

        verify(notificacionRepository, never()).save(any(Notificacion.class));
    }

    @Test
    @DisplayName("shouldListNotificacionesByUsuario")
    public void shouldListNotificacionesByUsuario() {
        when(notificacionRepository.findByUsuarioId(100L)).thenReturn(List.of(notificacionMock));

        List<NotificacionResponseDTO> result = notificacionService.listarUsuario(100L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUsuarioId()).isEqualTo(100L);
        verify(notificacionRepository, times(1)).findByUsuarioId(100L);
    }

    @Test
    @DisplayName("shouldFindNotificacionById")
    public void shouldFindNotificacionById() {
        when(notificacionRepository.findById(1L)).thenReturn(Optional.of(notificacionMock));

        NotificacionResponseDTO result = notificacionService.buscarNotificacion(1L);

        assertThat(result.getNotificacionId()).isEqualTo(1L);
        verify(notificacionRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("shouldThrowExceptionWhenNotificacionNotFound")
    public void shouldThrowExceptionWhenNotificacionNotFound() {
        when(notificacionRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> notificacionService.buscarNotificacion(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Notificación no encontrada");
    }

    @Test
    @DisplayName("shouldMarkNotificacionAsRead")
    public void shouldMarkNotificacionAsRead() {
        when(notificacionRepository.findById(1L)).thenReturn(Optional.of(notificacionMock));
        when(notificacionRepository.save(any(Notificacion.class))).thenReturn(notificacionMock);

        String result = notificacionService.marcarLeida(1L);

        assertThat(result).isEqualTo("Notificación marcada como leída");
        assertThat(notificacionMock.getLeidaNotificacion()).isTrue();
        verify(notificacionRepository, times(1)).save(notificacionMock);
    }

    @Test
    @DisplayName("shouldDeleteNotificacionSuccessfully")
    public void shouldDeleteNotificacionSuccessfully() {
        when(notificacionRepository.findById(1L)).thenReturn(Optional.of(notificacionMock));

        String result = notificacionService.eliminarNotificacion(1L);

        assertThat(result).isEqualTo("Notificación eliminada");
        verify(notificacionRepository, times(1)).delete(notificacionMock);
    }
}
