package com.Esports.Msvcs_partidas.services;

import com.Esports.Msvcs_partidas.Services.PartidaServiceImpl;
import com.Esports.Msvcs_partidas.clients.InscripcionClient;
import com.Esports.Msvcs_partidas.clients.TorneoClient;
import com.Esports.Msvcs_partidas.exceptions.ResourceNotFoundException;
import com.Esports.Msvcs_partidas.models.Partida;
import com.Esports.Msvcs_partidas.models.dtos.CrearPartidaDTO;
import com.Esports.Msvcs_partidas.models.dtos.PartidaResponseDTO;
import com.Esports.Msvcs_partidas.repositories.PartidaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PartidaServiceTest {

    @Mock
    private PartidaRepository partidaRepository;

    @Mock
    private TorneoClient torneoClient;

    @Mock
    private InscripcionClient inscripcionClient;

    @InjectMocks
    private PartidaServiceImpl partidaService;

    private Partida partidaMock;

    @BeforeEach
    public void setUp() {
        partidaMock = new Partida();
        partidaMock.setPartidaId(1L);
        partidaMock.setTorneoId(10L);
        partidaMock.setParticipanteAId(100L);
        partidaMock.setParticipanteBId(200L);
        partidaMock.setRonda(1);
        partidaMock.setFechaHora(LocalDateTime.now().plusDays(1));
        partidaMock.setEstadopartida("PROGRAMADA");
    }

    @Test
    @DisplayName("shouldCreatePartidaSuccessfully")
    public void shouldCreatePartidaSuccessfully() {
        CrearPartidaDTO dto = new CrearPartidaDTO();
        dto.setTorneoId(10L);
        dto.setParticipanteAId(100L);
        dto.setParticipanteBId(200L);
        dto.setRonda(1);
        dto.setFechaHora(LocalDateTime.now().plusDays(1));
        dto.setEstadopartida("PROGRAMADA");

        when(torneoClient.existeTorneo(10L)).thenReturn(true);
        when(inscripcionClient.existeParticipante(10L, 100L)).thenReturn(true);
        when(inscripcionClient.existeParticipante(10L, 200L)).thenReturn(true);
        when(partidaRepository.save(any(Partida.class))).thenReturn(partidaMock);

        PartidaResponseDTO result = partidaService.crearPartida(dto);

        assertThat(result.getTorneoId()).isEqualTo(10L);
        assertThat(result.getEstadopartida()).isEqualTo("PROGRAMADA");
        verify(partidaRepository, times(1)).save(any(Partida.class));
    }

    @Test
    @DisplayName("shouldThrowExceptionWhenTorneoDoesNotExist")
    public void shouldThrowExceptionWhenTorneoDoesNotExist() {
        CrearPartidaDTO dto = new CrearPartidaDTO();
        dto.setTorneoId(999L);
        dto.setParticipanteAId(100L);
        dto.setParticipanteBId(200L);
        dto.setRonda(1);
        dto.setFechaHora(LocalDateTime.now().plusDays(1));
        dto.setEstadopartida("PROGRAMADA");

        when(torneoClient.existeTorneo(999L)).thenReturn(false);

        assertThatThrownBy(() -> partidaService.crearPartida(dto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("El torneo no existe");

        verify(partidaRepository, never()).save(any(Partida.class));
    }

    @Test
    @DisplayName("shouldThrowExceptionWhenParticipanteANotInscrito")
    public void shouldThrowExceptionWhenParticipanteANotInscrito() {
        CrearPartidaDTO dto = new CrearPartidaDTO();
        dto.setTorneoId(10L);
        dto.setParticipanteAId(100L);
        dto.setParticipanteBId(200L);
        dto.setRonda(1);
        dto.setFechaHora(LocalDateTime.now().plusDays(1));
        dto.setEstadopartida("PROGRAMADA");

        when(torneoClient.existeTorneo(10L)).thenReturn(true);
        when(inscripcionClient.existeParticipante(10L, 100L)).thenReturn(false);

        assertThatThrownBy(() -> partidaService.crearPartida(dto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("El participante A no está inscrito en el torneo");
    }

    @Test
    @DisplayName("shouldReturnAllPartidas")
    public void shouldReturnAllPartidas() {
        when(partidaRepository.findAll()).thenReturn(List.of(partidaMock));

        List<PartidaResponseDTO> result = partidaService.listarPartidas();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTorneoId()).isEqualTo(10L);
        verify(partidaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("shouldFindPartidaById")
    public void shouldFindPartidaById() {
        when(partidaRepository.findById(1L)).thenReturn(Optional.of(partidaMock));

        PartidaResponseDTO result = partidaService.buscarPartida(1L);

        assertThat(result.getPartidaId()).isEqualTo(1L);
        verify(partidaRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("shouldThrowExceptionWhenPartidaNotFound")
    public void shouldThrowExceptionWhenPartidaNotFound() {
        when(partidaRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> partidaService.buscarPartida(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Partida no encontrada");
    }

    @Test
    @DisplayName("shouldUpdateHorarioSuccessfully")
    public void shouldUpdateHorarioSuccessfully() {
        LocalDateTime nuevaFecha = LocalDateTime.now().plusDays(3);

        when(partidaRepository.findById(1L)).thenReturn(Optional.of(partidaMock));
        when(partidaRepository.save(any(Partida.class))).thenReturn(partidaMock);

        PartidaResponseDTO result = partidaService.actualizarHorario(1L, nuevaFecha);

        assertThat(result.getFechaHora()).isEqualTo(nuevaFecha);
        verify(partidaRepository, times(1)).save(partidaMock);
    }

    @Test
    @DisplayName("shouldCancelPartidaSuccessfully")
    public void shouldCancelPartidaSuccessfully() {
        when(partidaRepository.findById(1L)).thenReturn(Optional.of(partidaMock));
        when(partidaRepository.save(any(Partida.class))).thenReturn(partidaMock);

        String result = partidaService.cancelarPartida(1L);

        assertThat(result).isEqualTo("Partida cancelada correctamente");
        assertThat(partidaMock.getEstadopartida()).isEqualTo("CANCELADA");
        verify(partidaRepository, times(1)).save(partidaMock);
    }
}
