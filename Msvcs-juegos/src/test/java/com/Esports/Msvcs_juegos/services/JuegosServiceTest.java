package com.Esports.Msvcs_juegos.services;

import com.Esports.Msvcs_juegos.Services.JuegosServiceImpl;
import com.Esports.Msvcs_juegos.exceptions.ResourceNotFoundException;
import com.Esports.Msvcs_juegos.models.Juegos;
import com.Esports.Msvcs_juegos.models.dtos.ActualizarJuegoDTO;
import com.Esports.Msvcs_juegos.models.dtos.CrearJuegoDTO;
import com.Esports.Msvcs_juegos.models.dtos.JuegoResponseDTO;
import com.Esports.Msvcs_juegos.repositories.JuegoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JuegosServiceTest {

    @Mock
    private JuegoRepository juegoRepository;

    @InjectMocks
    private JuegosServiceImpl juegosService;

    private Juegos juegoMock;

    @BeforeEach
    public void setUp() {
        juegoMock = new Juegos();
        juegoMock.setJuegosId(1L);
        juegoMock.setNombrejuegos("League of Legends");
        juegoMock.setGenerojuego("MOBA");
        juegoMock.setModalidadjuegos("5v5");
        juegoMock.setJugadores_por_equipo(5);
        juegoMock.setEstadojuego("ACTIVO");
    }

    @Test
    @DisplayName("shouldReturnAllJuegos")
    public void shouldReturnAllJuegos() {
        when(juegoRepository.findAll()).thenReturn(List.of(juegoMock));

        List<JuegoResponseDTO> result = juegosService.ListarJuegos();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNombrejuegos()).isEqualTo("League of Legends");
        verify(juegoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("shouldFindJuegoById")
    public void shouldFindJuegoById() {
        when(juegoRepository.findById(1L)).thenReturn(Optional.of(juegoMock));

        JuegoResponseDTO result = juegosService.BuscarJuego(1L);

        assertThat(result.getJuegosId()).isEqualTo(1L);
        assertThat(result.getNombrejuegos()).isEqualTo("League of Legends");
        verify(juegoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("shouldThrowExceptionWhenJuegoNotFound")
    public void shouldThrowExceptionWhenJuegoNotFound() {
        when(juegoRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> juegosService.BuscarJuego(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Juego no encontrado");
        verify(juegoRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("shouldCreateJuegoSuccessfully")
    public void shouldCreateJuegoSuccessfully() {
        CrearJuegoDTO dto = new CrearJuegoDTO();
        dto.setNombrejuegos("Valorant");
        dto.setGenerojuego("FPS");
        dto.setModalidadjuegos("5v5");
        dto.setJugadores_por_equipo(5);
        dto.setEstadojuego("ACTIVO");

        Juegos juegoGuardado = new Juegos();
        juegoGuardado.setJuegosId(2L);
        juegoGuardado.setNombrejuegos("Valorant");
        juegoGuardado.setGenerojuego("FPS");
        juegoGuardado.setModalidadjuegos("5v5");
        juegoGuardado.setJugadores_por_equipo(5);
        juegoGuardado.setEstadojuego("ACTIVO");

        when(juegoRepository.save(any(Juegos.class))).thenReturn(juegoGuardado);

        JuegoResponseDTO result = juegosService.CrearJuego(dto);

        assertThat(result.getNombrejuegos()).isEqualTo("Valorant");
        assertThat(result.getEstadojuego()).isEqualTo("ACTIVO");
        verify(juegoRepository, times(1)).save(any(Juegos.class));
    }

    @Test
    @DisplayName("shouldDeactivateJuegoSuccessfully")
    public void shouldDeactivateJuegoSuccessfully() {
        when(juegoRepository.findById(1L)).thenReturn(Optional.of(juegoMock));
        when(juegoRepository.save(any(Juegos.class))).thenReturn(juegoMock);

        String result = juegosService.desactivarJuego(1L);

        assertThat(result).isEqualTo("Juego desactivado correctamente");
        assertThat(juegoMock.getEstadojuego()).isEqualTo("INACTIVO");
        verify(juegoRepository, times(1)).save(juegoMock);
    }

    @Test
    @DisplayName("shouldUpdateJuegoSuccessfully")
    void shouldUpdateJuegoSuccessfully() {

        ActualizarJuegoDTO dto = new ActualizarJuegoDTO();
        dto.setNombrejuegos("Valorant");
        dto.setGenerojuego("FPS");
        dto.setModalidadjuegos("5v5");
        dto.setJugadores_por_equipo(5);
        dto.setEstadojuego("ACTIVO");

        when(juegoRepository.findById(1L)).thenReturn(Optional.of(juegoMock));

        when(juegoRepository.save(any(Juegos.class))).thenReturn(juegoMock);

        JuegoResponseDTO result = juegosService.actualizarJuego(1L, dto);

        assertThat(result).isNotNull();

        verify(juegoRepository).findById(1L);
        verify(juegoRepository).save(any(Juegos.class));
    }

    @Test
    @DisplayName("shouldThrowExceptionWhenUpdatingNonExistingJuego")
    void shouldThrowExceptionWhenUpdatingNonExistingJuego() {

        ActualizarJuegoDTO dto = new ActualizarJuegoDTO();

        when(juegoRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> juegosService.actualizarJuego(999L, dto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Juego no encontrado");
    }

    @Test
    @DisplayName("shouldThrowExceptionWhenDeactivatingNonExistingJuego")
    void shouldThrowExceptionWhenDeactivatingNonExistingJuego() {

        when(juegoRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> juegosService.desactivarJuego(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Juego no encontrado");
    }

    @Test
    @DisplayName("shouldReturnEmptyListWhenNoGamesExist")
    void shouldReturnEmptyListWhenNoGamesExist() {

        when(juegoRepository.findAll()).thenReturn(List.of());

        List<JuegoResponseDTO> result = juegosService.ListarJuegos();

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("shouldAssignActivoStateWhenCreatingGame")
    void shouldAssignActivoStateWhenCreatingGame() {

        CrearJuegoDTO dto = new CrearJuegoDTO();

        dto.setNombrejuegos("CS2");
        dto.setGenerojuego("FPS");
        dto.setModalidadjuegos("5v5");
        dto.setJugadores_por_equipo(5);

        Juegos juego = new Juegos();

        juego.setJuegosId(10L);
        juego.setEstadojuego("ACTIVO");

        when(juegoRepository.save(any(Juegos.class))).thenReturn(juego);

        JuegoResponseDTO result = juegosService.CrearJuego(dto);

        assertThat(result.getEstadojuego()).isEqualTo("ACTIVO");
    }
}
