package com.Esports.Msvcs_torneos.services;

import com.Esports.Msvcs_torneos.Services.TorneoServiceImpl;
import com.Esports.Msvcs_torneos.exceptions.BadRequestException;
import com.Esports.Msvcs_torneos.exceptions.ResourceNotFoundException;
import com.Esports.Msvcs_torneos.models.Torneo;
import com.Esports.Msvcs_torneos.models.dtos.ActualizarTorneoDTO;
import com.Esports.Msvcs_torneos.models.dtos.CrearTorneoDTO;
import com.Esports.Msvcs_torneos.models.dtos.TorneoResponseDTO;
import com.Esports.Msvcs_torneos.repositories.TorneosRepository;
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
public class TorneoServiceTest {

    @Mock
    private TorneosRepository torneoRepository;

    @InjectMocks
    private TorneoServiceImpl torneoService;

    private Torneo torneoMock;

    @BeforeEach
    public void setUp() {
        torneoMock = new Torneo();
        torneoMock.setTorneoId(1L);
        torneoMock.setNombretorneo("Copa Esports 2026");
        torneoMock.setJuegoId(10L);
        torneoMock.setFechaInicio(LocalDate.now().plusDays(5));
        torneoMock.setFechaFin(LocalDate.now().plusDays(10));
        torneoMock.setCupoMaximo(16);
        torneoMock.setModalidadTorneo("Eliminacion directa");
        torneoMock.setEstadoTorneo("ACTIVO");
    }

    @Test
    @DisplayName("shouldCreateTorneoSuccessfully")
    public void shouldCreateTorneoSuccessfully() {
        CrearTorneoDTO dto = new CrearTorneoDTO();
        dto.setNombretorneo("Copa Esports 2026");
        dto.setJuegoId(10L);
        dto.setFechaInicio(LocalDate.now().plusDays(5));
        dto.setFechaFin(LocalDate.now().plusDays(10));
        dto.setCupoMaximo(16);
        dto.setModalidadTorneo("Eliminacion directa");
        dto.setEstadoTorneo("ACTIVO");

        when(torneoRepository.save(any(Torneo.class))).thenReturn(torneoMock);

        TorneoResponseDTO result = torneoService.crearTorneo(dto);

        assertThat(result.getNombretorneo()).isEqualTo("Copa Esports 2026");
        assertThat(result.getEstadoTorneo()).isEqualTo("ACTIVO");
        verify(torneoRepository, times(1)).save(any(Torneo.class));
    }

    @Test
    @DisplayName("shouldThrowExceptionWhenFechaFinNotAfterFechaInicio")
    public void shouldThrowExceptionWhenFechaFinNotAfterFechaInicio() {
        CrearTorneoDTO dto = new CrearTorneoDTO();
        dto.setNombretorneo("Copa Esports 2026");
        dto.setJuegoId(10L);
        dto.setFechaInicio(LocalDate.now().plusDays(10));
        dto.setFechaFin(LocalDate.now().plusDays(5));
        dto.setCupoMaximo(16);
        dto.setModalidadTorneo("Eliminacion directa");
        dto.setEstadoTorneo("ACTIVO");

        assertThatThrownBy(() -> torneoService.crearTorneo(dto))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("La fecha de fin debe ser posterior a la fecha de inicio");

        verify(torneoRepository, never()).save(any(Torneo.class));
    }

    @Test
    @DisplayName("shouldThrowExceptionWhenFechaInicioInPast")
    public void shouldThrowExceptionWhenFechaInicioInPast() {
        CrearTorneoDTO dto = new CrearTorneoDTO();
        dto.setNombretorneo("Copa Esports 2026");
        dto.setJuegoId(10L);
        dto.setFechaInicio(LocalDate.now().minusDays(1));
        dto.setFechaFin(LocalDate.now().plusDays(5));
        dto.setCupoMaximo(16);
        dto.setModalidadTorneo("Eliminacion directa");
        dto.setEstadoTorneo("ACTIVO");

        assertThatThrownBy(() -> torneoService.crearTorneo(dto))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("La fecha de inicio no puede ser en el pasado");
    }

    @Test
    @DisplayName("shouldReturnAllTorneos")
    public void shouldReturnAllTorneos() {
        when(torneoRepository.findAll()).thenReturn(List.of(torneoMock));

        List<TorneoResponseDTO> result = torneoService.listarTorneos();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNombretorneo()).isEqualTo("Copa Esports 2026");
        verify(torneoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("shouldFindTorneoById")
    public void shouldFindTorneoById() {
        when(torneoRepository.findById(1L)).thenReturn(Optional.of(torneoMock));

        TorneoResponseDTO result = torneoService.buscarTorneo(1L);

        assertThat(result.getTorneoId()).isEqualTo(1L);
        verify(torneoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("shouldThrowExceptionWhenTorneoNotFound")
    public void shouldThrowExceptionWhenTorneoNotFound() {
        when(torneoRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> torneoService.buscarTorneo(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Torneo no encontrado");
    }

    @Test
    @DisplayName("shouldUpdateTorneoSuccessfully")
    public void shouldUpdateTorneoSuccessfully() {
        ActualizarTorneoDTO dto = new ActualizarTorneoDTO();
        dto.setNombretorneo("Copa Esports Final 2026");
        dto.setCupoMaximo(32);

        when(torneoRepository.findById(1L)).thenReturn(Optional.of(torneoMock));
        when(torneoRepository.save(any(Torneo.class))).thenReturn(torneoMock);

        TorneoResponseDTO result = torneoService.actualizarTorneo(1L, dto);

        assertThat(result.getNombretorneo()).isEqualTo("Copa Esports Final 2026");
        assertThat(result.getCupoMaximo()).isEqualTo(32);
        verify(torneoRepository, times(1)).save(torneoMock);
    }

    @Test
    @DisplayName("shouldThrowExceptionWhenUpdatingClosedTorneo")
    public void shouldThrowExceptionWhenUpdatingClosedTorneo() {
        torneoMock.setEstadoTorneo("CERRADO");
        ActualizarTorneoDTO dto = new ActualizarTorneoDTO();
        dto.setNombretorneo("Nuevo nombre");

        when(torneoRepository.findById(1L)).thenReturn(Optional.of(torneoMock));

        assertThatThrownBy(() -> torneoService.actualizarTorneo(1L, dto))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("No se puede modificar un torneo CERRADO");

        verify(torneoRepository, never()).save(any(Torneo.class));
    }

    @Test
    @DisplayName("shouldCloseTorneoSuccessfully")
    public void shouldCloseTorneoSuccessfully() {
        when(torneoRepository.findById(1L)).thenReturn(Optional.of(torneoMock));
        when(torneoRepository.save(any(Torneo.class))).thenReturn(torneoMock);

        String result = torneoService.cerrarTorneo(1L);

        assertThat(result).isEqualTo("Torneo cerrado correctamente");
        assertThat(torneoMock.getEstadoTorneo()).isEqualTo("CERRADO");
        verify(torneoRepository, times(1)).save(torneoMock);
    }

    @Test
    @DisplayName("shouldCancelTorneoSuccessfully")
    public void shouldCancelTorneoSuccessfully() {
        when(torneoRepository.findById(1L)).thenReturn(Optional.of(torneoMock));
        when(torneoRepository.save(any(Torneo.class))).thenReturn(torneoMock);

        String result = torneoService.cancelarTorneo(1L);

        assertThat(result).isEqualTo("Torneo cancelado correctamente");
        assertThat(torneoMock.getEstadoTorneo()).isEqualTo("CANCELADO");
        verify(torneoRepository, times(1)).save(torneoMock);
    }
}
