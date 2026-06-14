package com.Esports.Msvcs_sanciones.services;

import com.Esports.Msvcs_sanciones.Services.SancionesServiceImpl;
import com.Esports.Msvcs_sanciones.clients.UsuarioClient;
import com.Esports.Msvcs_sanciones.exceptions.ResourceNotFoundException;
import com.Esports.Msvcs_sanciones.models.Sancion;
import com.Esports.Msvcs_sanciones.models.dtos.ActualizarSancionDTO;
import com.Esports.Msvcs_sanciones.models.dtos.CrearSancionDTO;
import com.Esports.Msvcs_sanciones.models.dtos.SancionResponseDTO;
import com.Esports.Msvcs_sanciones.repositories.SancionesRepository;
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
public class SancionesServiceTest {

    @Mock
    private SancionesRepository sancionRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private SancionesServiceImpl sancionesService;

    private Sancion sancionMock;

    @BeforeEach
    public void setUp() {
        sancionMock = new Sancion();
        sancionMock.setSancionId(1L);
        sancionMock.setUsuarioId(100L);
        sancionMock.setMotivo("Conducta antideportiva");
        sancionMock.setFechaInicio(LocalDate.now());
        sancionMock.setFechaFin(LocalDate.now().plusDays(7));
        sancionMock.setEstadoSancion("ACTIVA");
        sancionMock.setSeveridad("MEDIA");
    }

    @Test
    @DisplayName("shouldCreateSancionSuccessfully")
    public void shouldCreateSancionSuccessfully() {
        CrearSancionDTO dto = new CrearSancionDTO();
        dto.setUsuarioId(100L);
        dto.setMotivo("Conducta antideportiva");
        dto.setFechaInicio(LocalDate.now());
        dto.setFechaFin(LocalDate.now().plusDays(7));
        dto.setEstadoSancion("ACTIVA");
        dto.setSeveridad("MEDIA");

        when(usuarioClient.existeUsuario(100L)).thenReturn(true);
        when(sancionRepository.save(any(Sancion.class))).thenReturn(sancionMock);

        SancionResponseDTO result = sancionesService.crearSancion(dto);

        assertThat(result.getMotivo()).isEqualTo("Conducta antideportiva");
        assertThat(result.getEstadoSancion()).isEqualTo("ACTIVA");
        verify(sancionRepository, times(1)).save(any(Sancion.class));
    }

    @Test
    @DisplayName("shouldThrowExceptionWhenUsuarioDoesNotExist")
    public void shouldThrowExceptionWhenUsuarioDoesNotExist() {
        CrearSancionDTO dto = new CrearSancionDTO();
        dto.setUsuarioId(999L);
        dto.setMotivo("Conducta antideportiva");
        dto.setFechaInicio(LocalDate.now());
        dto.setEstadoSancion("ACTIVA");
        dto.setSeveridad("MEDIA");

        when(usuarioClient.existeUsuario(999L)).thenReturn(false);

        assertThatThrownBy(() -> sancionesService.crearSancion(dto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("El usuario sancionado no existe");

        verify(sancionRepository, never()).save(any(Sancion.class));
    }

    @Test
    @DisplayName("shouldReturnAllSanciones")
    public void shouldReturnAllSanciones() {
        when(sancionRepository.findAll()).thenReturn(List.of(sancionMock));

        List<SancionResponseDTO> result = sancionesService.listarSanciones();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getMotivo()).isEqualTo("Conducta antideportiva");
        verify(sancionRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("shouldFindSancionById")
    public void shouldFindSancionById() {
        when(sancionRepository.findById(1L)).thenReturn(Optional.of(sancionMock));

        SancionResponseDTO result = sancionesService.buscarSancion(1L);

        assertThat(result.getSancionId()).isEqualTo(1L);
        assertThat(result.getEstadoSancion()).isEqualTo("ACTIVA");
        assertThat(result.getSeveridad()).isEqualTo("MEDIA");
        verify(sancionRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("shouldThrowExceptionWhenSancionNotFound")
    public void shouldThrowExceptionWhenSancionNotFound() {
        when(sancionRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sancionesService.buscarSancion(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Sanción no encontrada");
    }

    @Test
    @DisplayName("shouldCloseSancionSuccessfully")
    public void shouldCloseSancionSuccessfully() {
        when(sancionRepository.findById(1L)).thenReturn(Optional.of(sancionMock));
        when(sancionRepository.save(any(Sancion.class))).thenReturn(sancionMock);

        String result = sancionesService.cerrarSancion(1L);

        assertThat(result).isEqualTo("Sanción cerrada");
        assertThat(sancionMock.getEstadoSancion()).isEqualTo("CERRADA");
        verify(sancionRepository, times(1)).save(sancionMock);
    }

    @Test
    @DisplayName("shouldValidateSancionActiva")
    public void shouldValidateSancionActiva() {
        when(sancionRepository.existsByUsuarioIdAndEstadoSancion(100L, "ACTIVA")).thenReturn(true);

        Boolean result = sancionesService.validarSancionActiva(100L);

        assertThat(result).isTrue();
        verify(sancionRepository, times(1)).existsByUsuarioIdAndEstadoSancion(100L, "ACTIVA");
    }

    @Test
    @DisplayName("shouldUpdateSancionSuccessfully")
    public void shouldUpdateSancionSuccessfully() {
        ActualizarSancionDTO dto = new ActualizarSancionDTO();
        dto.setSeveridad("ALTA");
        dto.setEstadoSancion("ACTIVA");

        when(sancionRepository.findById(1L)).thenReturn(Optional.of(sancionMock));
        when(sancionRepository.save(any(Sancion.class))).thenReturn(sancionMock);

        SancionResponseDTO result = sancionesService.actualizarSancion(1L, dto);

        assertThat(result.getSeveridad()).isEqualTo("ALTA");
        verify(sancionRepository, times(1)).save(sancionMock);
    }
}
