package com.Esports.Msvcs_premios.services;

import com.Esports.Msvcs_premios.Services.PremioServiceImpl;
import com.Esports.Msvcs_premios.exceptions.ResourceNotFoundException;
import com.Esports.Msvcs_premios.models.Premio;
import com.Esports.Msvcs_premios.models.Premio_asignado;
import com.Esports.Msvcs_premios.models.dtos.ActualizarPremioDTO;
import com.Esports.Msvcs_premios.models.dtos.CrearPremioDTO;
import com.Esports.Msvcs_premios.models.dtos.PremioResponseDTO;
import com.Esports.Msvcs_premios.repositories.PremioAsignadoRepository;
import com.Esports.Msvcs_premios.repositories.PremioRepository;
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
public class PremioServiceTest {

    @Mock
    private PremioRepository premioRepository;

    @Mock
    private PremioAsignadoRepository premioAsignadoRepository;

    @InjectMocks
    private PremioServiceImpl premioService;

    private Premio premioMock;

    @BeforeEach
    public void setUp() {
        premioMock = new Premio();
        premioMock.setPremioId(1L);
        premioMock.setTorneoId(10L);
        premioMock.setPosicion(1);
        premioMock.setDescripcion("Trofeo + premio en efectivo");
        premioMock.setValor(500000.0);
        premioMock.setEstado("PENDIENTE");
    }

    @Test
    @DisplayName("shouldCreatePremioSuccessfully")
    public void shouldCreatePremioSuccessfully() {
        CrearPremioDTO dto = new CrearPremioDTO();
        dto.setTorneoId(10L);
        dto.setPosicion(1);
        dto.setDescripcion("Trofeo + premio en efectivo");
        dto.setValor(500000.0);
        dto.setEstado("PENDIENTE");

        when(premioRepository.save(any(Premio.class))).thenReturn(premioMock);

        PremioResponseDTO result = premioService.crearPremio(dto);

        assertThat(result.getDescripcion()).isEqualTo("Trofeo + premio en efectivo");
        assertThat(result.getEstado()).isEqualTo("PENDIENTE");
        verify(premioRepository, times(1)).save(any(Premio.class));
    }

    @Test
    @DisplayName("shouldReturnAllPremios")
    public void shouldReturnAllPremios() {
        when(premioRepository.findAll()).thenReturn(List.of(premioMock));

        List<PremioResponseDTO> result = premioService.listarPremios();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getPosicion()).isEqualTo(1);
        verify(premioRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("shouldFindPremioById")
    public void shouldFindPremioById() {
        when(premioRepository.findById(1L)).thenReturn(Optional.of(premioMock));

        PremioResponseDTO result = premioService.buscarPremio(1L);

        assertThat(result.getPremioId()).isEqualTo(1L);
        verify(premioRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("shouldThrowExceptionWhenPremioNotFound")
    public void shouldThrowExceptionWhenPremioNotFound() {
        when(premioRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> premioService.buscarPremio(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Premio no encontrado");
    }

    @Test
    @DisplayName("shouldUpdatePremioSuccessfully")
    public void shouldUpdatePremioSuccessfully() {
        ActualizarPremioDTO dto = new ActualizarPremioDTO();
        dto.setTorneoId(10L);
        dto.setPosicion(1);
        dto.setDescripcion("Trofeo, medalla y premio en efectivo");
        dto.setValor(600000.0);
        dto.setEstado("PENDIENTE");

        when(premioRepository.findById(1L)).thenReturn(Optional.of(premioMock));
        when(premioRepository.save(any(Premio.class))).thenReturn(premioMock);

        PremioResponseDTO result = premioService.actualizarPremio(1L, dto);

        assertThat(result.getDescripcion()).isEqualTo("Trofeo, medalla y premio en efectivo");
        assertThat(result.getValor()).isEqualTo(600000.0);
        verify(premioRepository, times(1)).save(premioMock);
    }

    @Test
    @DisplayName("shouldAssignPremioSuccessfully")
    public void shouldAssignPremioSuccessfully() {
        when(premioRepository.findById(1L)).thenReturn(Optional.of(premioMock));
        when(premioAsignadoRepository.save(any(Premio_asignado.class))).thenReturn(new Premio_asignado());
        when(premioRepository.save(any(Premio.class))).thenReturn(premioMock);

        String result = premioService.asignarPremio(1L, 100L);

        assertThat(result).isEqualTo("Premio asignado correctamente");
        assertThat(premioMock.getEstado()).isEqualTo("ASIGNADO");
        verify(premioAsignadoRepository, times(1)).save(any(Premio_asignado.class));
        verify(premioRepository, times(1)).save(premioMock);
    }

    @Test
    @DisplayName("shouldThrowExceptionWhenAssigningToNonexistentPremio")
    public void shouldThrowExceptionWhenAssigningToNonexistentPremio() {
        when(premioRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> premioService.asignarPremio(999L, 100L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Premio no encontrado");

        verify(premioAsignadoRepository, never()).save(any(Premio_asignado.class));
    }
}
