package com.Esports.Msvcs_inscripciones.services;

import com.Esports.Msvcs_inscripciones.Services.InscripcionesServiceImpl;
import com.Esports.Msvcs_inscripciones.clients.EquipoClient;
import com.Esports.Msvcs_inscripciones.clients.SancionClient;
import com.Esports.Msvcs_inscripciones.clients.TorneoClient;
import com.Esports.Msvcs_inscripciones.exceptions.BadRequestException;
import com.Esports.Msvcs_inscripciones.exceptions.DuplicateResourceException;
import com.Esports.Msvcs_inscripciones.exceptions.ResourceNotFoundException;
import com.Esports.Msvcs_inscripciones.models.Inscripcion;
import com.Esports.Msvcs_inscripciones.models.dtos.CrearInscripcionDTO;
import com.Esports.Msvcs_inscripciones.models.dtos.InscripcionResponseDTO;
import com.Esports.Msvcs_inscripciones.models.dtos.TorneoResponseDTO;
import com.Esports.Msvcs_inscripciones.repositories.InscripcionRepository;
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
public class InscripcionesServiceTest {
    @Mock
    private InscripcionRepository inscripcionRepository;

    @Mock
    private TorneoClient torneoClient;

    @Mock
    private EquipoClient equipoClient;

    @Mock
    private SancionClient sancionClient;

    @InjectMocks
    private InscripcionesServiceImpl inscripcionesService;

    private Inscripcion inscripcionMock;
    private TorneoResponseDTO torneoActivo;

    @BeforeEach
    public void setUp() {
        inscripcionMock = new Inscripcion();
        inscripcionMock.setInscripcionId(1L);
        inscripcionMock.setTorneoId(10L);
        inscripcionMock.setUsuarioId(100L);
        inscripcionMock.setNombreJugador("Jugador1");
        inscripcionMock.setTipoParticipante("INDIVIDUAL");
        inscripcionMock.setEstado("PENDIENTE");
        inscripcionMock.setFechaInscripcion(LocalDate.now());

        torneoActivo = new TorneoResponseDTO();
        torneoActivo.setTorneoId(10L);
        torneoActivo.setCupoMaximo(16);
        torneoActivo.setEstadoTorneo("ACTIVO");
    }

    @Test
    @DisplayName("shouldCreateInscripcionSuccessfully")
    public void shouldCreateInscripcionSuccessfully() {
        CrearInscripcionDTO dto = new CrearInscripcionDTO();
        dto.setTorneoId(10L);
        dto.setUsuarioId(100L);
        dto.setNombreJugador("Jugador1");
        dto.setTipoParticipante("INDIVIDUAL");
        dto.setEstado("PENDIENTE");
        dto.setFechaInscripcion(LocalDate.now());

        when(torneoClient.existeTorneo(10L)).thenReturn(true);
        when(sancionClient.tieneSancionActiva(100L)).thenReturn(false);
        when(torneoClient.buscarTorneo(10L)).thenReturn(torneoActivo);
        when(inscripcionRepository.countByTorneoIdAndEstadoNot(10L, "CANCELADA")).thenReturn(5L);
        when(inscripcionRepository.existsByTorneoIdAndUsuarioId(10L, 100L)).thenReturn(false);
        when(inscripcionRepository.save(any(Inscripcion.class))).thenReturn(inscripcionMock);

        InscripcionResponseDTO result = inscripcionesService.crearInscripcion(dto);

        assertThat(result.getEstado()).isEqualTo("PENDIENTE");
        assertThat(result.getNombreJugador()).isEqualTo("Jugador1");
        verify(inscripcionRepository, times(1)).save(any(Inscripcion.class));
    }

    @Test
    @DisplayName("shouldThrowExceptionWhenTorneoDoesNotExist")
    public void shouldThrowExceptionWhenTorneoDoesNotExist() {
        CrearInscripcionDTO dto = new CrearInscripcionDTO();
        dto.setTorneoId(999L);
        dto.setUsuarioId(100L);
        dto.setNombreJugador("Jugador1");
        dto.setTipoParticipante("INDIVIDUAL");
        dto.setEstado("PENDIENTE");
        dto.setFechaInscripcion(LocalDate.now());

        when(torneoClient.existeTorneo(999L)).thenReturn(false);

        assertThatThrownBy(() -> inscripcionesService.crearInscripcion(dto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("El torneo no existe");

        verify(inscripcionRepository, never()).save(any(Inscripcion.class));
    }

    @Test
    @DisplayName("shouldThrowExceptionWhenUsuarioTieneSancionActiva")
    public void shouldThrowExceptionWhenUsuarioTieneSancionActiva() {
        CrearInscripcionDTO dto = new CrearInscripcionDTO();
        dto.setTorneoId(10L);
        dto.setUsuarioId(100L);
        dto.setNombreJugador("Jugador1");
        dto.setTipoParticipante("INDIVIDUAL");
        dto.setEstado("PENDIENTE");
        dto.setFechaInscripcion(LocalDate.now());

        when(torneoClient.existeTorneo(10L)).thenReturn(true);
        when(sancionClient.tieneSancionActiva(100L)).thenReturn(true);

        assertThatThrownBy(() -> inscripcionesService.crearInscripcion(dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("El usuario tiene una sanción activa");
    }

    @Test
    @DisplayName("shouldThrowExceptionWhenTorneoNotActivo")
    public void shouldThrowExceptionWhenTorneoNotActivo() {
        CrearInscripcionDTO dto = new CrearInscripcionDTO();
        dto.setTorneoId(10L);
        dto.setUsuarioId(100L);
        dto.setNombreJugador("Jugador1");
        dto.setTipoParticipante("INDIVIDUAL");
        dto.setEstado("PENDIENTE");
        dto.setFechaInscripcion(LocalDate.now());

        TorneoResponseDTO torneoCerrado = new TorneoResponseDTO();
        torneoCerrado.setTorneoId(10L);
        torneoCerrado.setCupoMaximo(16);
        torneoCerrado.setEstadoTorneo("CERRADO");

        when(torneoClient.existeTorneo(10L)).thenReturn(true);
        when(sancionClient.tieneSancionActiva(100L)).thenReturn(false);
        when(torneoClient.buscarTorneo(10L)).thenReturn(torneoCerrado);

        assertThatThrownBy(() -> inscripcionesService.crearInscripcion(dto))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Solo se puede inscribir en torneos ACTIVOS");
    }

    @Test
    @DisplayName("shouldThrowExceptionWhenTorneoSinCupos")
    public void shouldThrowExceptionWhenTorneoSinCupos() {
        CrearInscripcionDTO dto = new CrearInscripcionDTO();
        dto.setTorneoId(10L);
        dto.setUsuarioId(100L);
        dto.setNombreJugador("Jugador1");
        dto.setTipoParticipante("INDIVIDUAL");
        dto.setEstado("PENDIENTE");
        dto.setFechaInscripcion(LocalDate.now());

        when(torneoClient.existeTorneo(10L)).thenReturn(true);
        when(sancionClient.tieneSancionActiva(100L)).thenReturn(false);
        when(torneoClient.buscarTorneo(10L)).thenReturn(torneoActivo);
        when(inscripcionRepository.countByTorneoIdAndEstadoNot(10L, "CANCELADA")).thenReturn(16L);

        assertThatThrownBy(() -> inscripcionesService.crearInscripcion(dto))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("El torneo no tiene cupos disponibles");
    }

    @Test
    @DisplayName("shouldThrowExceptionWhenUsuarioYaInscrito")
    public void shouldThrowExceptionWhenUsuarioYaInscrito() {
        CrearInscripcionDTO dto = new CrearInscripcionDTO();
        dto.setTorneoId(10L);
        dto.setUsuarioId(100L);
        dto.setNombreJugador("Jugador1");
        dto.setTipoParticipante("INDIVIDUAL");
        dto.setEstado("PENDIENTE");
        dto.setFechaInscripcion(LocalDate.now());

        when(torneoClient.existeTorneo(10L)).thenReturn(true);
        when(sancionClient.tieneSancionActiva(100L)).thenReturn(false);
        when(torneoClient.buscarTorneo(10L)).thenReturn(torneoActivo);
        when(inscripcionRepository.countByTorneoIdAndEstadoNot(10L, "CANCELADA")).thenReturn(5L);
        when(inscripcionRepository.existsByTorneoIdAndUsuarioId(10L, 100L)).thenReturn(true);

        assertThatThrownBy(() -> inscripcionesService.crearInscripcion(dto))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("El usuario ya está inscrito en este torneo");
    }

    @Test
    @DisplayName("shouldReturnAllInscripciones")
    public void shouldReturnAllInscripciones() {
        when(inscripcionRepository.findAll()).thenReturn(List.of(inscripcionMock));

        List<InscripcionResponseDTO> result = inscripcionesService.listarInscripciones();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNombreJugador()).isEqualTo("Jugador1");
        verify(inscripcionRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("shouldFindInscripcionById")
    public void shouldFindInscripcionById() {
        when(inscripcionRepository.findById(1L)).thenReturn(Optional.of(inscripcionMock));

        InscripcionResponseDTO result = inscripcionesService.buscarInscripcion(1L);

        assertThat(result.getInscripcionId()).isEqualTo(1L);
        verify(inscripcionRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("shouldThrowExceptionWhenInscripcionNotFound")
    public void shouldThrowExceptionWhenInscripcionNotFound() {
        when(inscripcionRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> inscripcionesService.buscarInscripcion(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Inscripcion no encontrada");
    }

    @Test
    @DisplayName("shouldUpdateEstadoSuccessfully")
    public void shouldUpdateEstadoSuccessfully() {
        when(inscripcionRepository.findById(1L)).thenReturn(Optional.of(inscripcionMock));
        when(inscripcionRepository.save(any(Inscripcion.class))).thenReturn(inscripcionMock);

        InscripcionResponseDTO result = inscripcionesService.actualizarEstado(1L, "CONFIRMADA");

        assertThat(result.getEstado()).isEqualTo("CONFIRMADA");
        verify(inscripcionRepository, times(1)).save(inscripcionMock);
    }

    @Test
    @DisplayName("shouldCancelInscripcionSuccessfully")
    public void shouldCancelInscripcionSuccessfully() {
        when(inscripcionRepository.findById(1L)).thenReturn(Optional.of(inscripcionMock));
        when(inscripcionRepository.save(any(Inscripcion.class))).thenReturn(inscripcionMock);

        String result = inscripcionesService.cancelarInscripcion(1L);

        assertThat(result).isEqualTo("Inscripcion cancelada");
        assertThat(inscripcionMock.getEstado()).isEqualTo("CANCELADA");
        verify(inscripcionRepository, times(1)).save(inscripcionMock);
    }
}
