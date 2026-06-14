package com.Esports.Msvcs_resultados.services;

import com.Esports.Msvcs_resultados.Services.ResultadoServiceImpl;
import com.Esports.Msvcs_resultados.clients.PartidaClient;
import com.Esports.Msvcs_resultados.exceptions.BadRequestException;
import com.Esports.Msvcs_resultados.exceptions.ResourceNotFoundException;
import com.Esports.Msvcs_resultados.models.Resultado;
import com.Esports.Msvcs_resultados.models.dtos.ActualizarResultadoDTO;
import com.Esports.Msvcs_resultados.models.dtos.CrearResultadoDTO;
import com.Esports.Msvcs_resultados.models.dtos.PartidaResponseDTO;
import com.Esports.Msvcs_resultados.models.dtos.ResultadoResponseDTO;
import com.Esports.Msvcs_resultados.repositories.ResultadosRepository;
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
public class ResultadoServiceTest {

    @Mock
    private ResultadosRepository resultadosRepository;

    @Mock
    private PartidaClient partidaClient;

    @InjectMocks
    private ResultadoServiceImpl resultadoService;

    private Resultado resultadoMock;
    private PartidaResponseDTO partidaMock;

    @BeforeEach
    public void setUp() {
        resultadoMock = new Resultado();
        resultadoMock.setResultadoId(1L);
        resultadoMock.setPartidaId(10L);
        resultadoMock.setGanadorId(100L);
        resultadoMock.setPuntajeA(2);
        resultadoMock.setPuntajeB(1);
        resultadoMock.setEstadoValidacion("PENDIENTE");
        resultadoMock.setFechaRegistro(LocalDate.now());

        partidaMock = new PartidaResponseDTO();
        partidaMock.setPartidaId(10L);
        partidaMock.setParticipanteAId(100L);
        partidaMock.setParticipanteBId(200L);
        partidaMock.setEstadopartida("FINALIZADA");
    }

    @Test
    @DisplayName("shouldRegistrarResultadoSuccessfully")
    public void shouldRegistrarResultadoSuccessfully() {
        CrearResultadoDTO dto = new CrearResultadoDTO();
        dto.setPartidaId(10L);
        dto.setGanadorId(100L);
        dto.setPuntajeA(2);
        dto.setPuntajeB(1);
        dto.setEstadoValidacion("PENDIENTE");
        dto.setFechaRegistro(LocalDate.now());

        when(partidaClient.buscarPartida(10L)).thenReturn(partidaMock);
        when(resultadosRepository.existsByPartidaId(10L)).thenReturn(false);
        when(resultadosRepository.save(any(Resultado.class))).thenReturn(resultadoMock);

        ResultadoResponseDTO result = resultadoService.registrarResultado(dto);

        assertThat(result.getGanadorId()).isEqualTo(100L);
        assertThat(result.getEstadoValidacion()).isEqualTo("PENDIENTE");
        verify(resultadosRepository, times(1)).save(any(Resultado.class));
    }

    @Test
    @DisplayName("shouldThrowExceptionWhenPartidaCancelada")
    public void shouldThrowExceptionWhenPartidaCancelada() {
        CrearResultadoDTO dto = new CrearResultadoDTO();
        dto.setPartidaId(10L);
        dto.setGanadorId(100L);
        dto.setPuntajeA(2);
        dto.setPuntajeB(1);
        dto.setEstadoValidacion("PENDIENTE");
        dto.setFechaRegistro(LocalDate.now());

        PartidaResponseDTO partidaCancelada = new PartidaResponseDTO();
        partidaCancelada.setPartidaId(10L);
        partidaCancelada.setParticipanteAId(100L);
        partidaCancelada.setParticipanteBId(200L);
        partidaCancelada.setEstadopartida("CANCELADA");

        when(partidaClient.buscarPartida(10L)).thenReturn(partidaCancelada);

        assertThatThrownBy(() -> resultadoService.registrarResultado(dto))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("No se puede registrar resultado de una partida cancelada");

        verify(resultadosRepository, never()).save(any(Resultado.class));
    }

    @Test
    @DisplayName("shouldThrowExceptionWhenGanadorInvalido")
    public void shouldThrowExceptionWhenGanadorInvalido() {
        CrearResultadoDTO dto = new CrearResultadoDTO();
        dto.setPartidaId(10L);
        dto.setGanadorId(999L);
        dto.setPuntajeA(2);
        dto.setPuntajeB(1);
        dto.setEstadoValidacion("PENDIENTE");
        dto.setFechaRegistro(LocalDate.now());

        when(partidaClient.buscarPartida(10L)).thenReturn(partidaMock);

        assertThatThrownBy(() -> resultadoService.registrarResultado(dto))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("El ganador debe ser uno de los participantes de la partida");
    }

    @Test
    @DisplayName("shouldThrowExceptionWhenResultadoDuplicado")
    public void shouldThrowExceptionWhenResultadoDuplicado() {
        CrearResultadoDTO dto = new CrearResultadoDTO();
        dto.setPartidaId(10L);
        dto.setGanadorId(100L);
        dto.setPuntajeA(2);
        dto.setPuntajeB(1);
        dto.setEstadoValidacion("PENDIENTE");
        dto.setFechaRegistro(LocalDate.now());

        when(partidaClient.buscarPartida(10L)).thenReturn(partidaMock);
        when(resultadosRepository.existsByPartidaId(10L)).thenReturn(true);

        assertThatThrownBy(() -> resultadoService.registrarResultado(dto))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Ya existe un resultado registrado para esta partida");
    }

    @Test
    @DisplayName("shouldReturnAllResultados")
    public void shouldReturnAllResultados() {
        when(resultadosRepository.findAll()).thenReturn(List.of(resultadoMock));

        List<ResultadoResponseDTO> result = resultadoService.listarResultados();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getPartidaId()).isEqualTo(10L);
        verify(resultadosRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("shouldFindResultadoById")
    public void shouldFindResultadoById() {
        when(resultadosRepository.findById(1L)).thenReturn(Optional.of(resultadoMock));

        ResultadoResponseDTO result = resultadoService.buscarResultado(1L);

        assertThat(result.getResultadoId()).isEqualTo(1L);
        verify(resultadosRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("shouldThrowExceptionWhenResultadoNotFound")
    public void shouldThrowExceptionWhenResultadoNotFound() {
        when(resultadosRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> resultadoService.buscarResultado(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Resultado no encontrado");
    }

    @Test
    @DisplayName("shouldUpdateResultadoSuccessfully")
    public void shouldUpdateResultadoSuccessfully() {
        ActualizarResultadoDTO dto = new ActualizarResultadoDTO();
        dto.setPartidaId(10L);
        dto.setGanadorId(200L);
        dto.setPuntajeA(1);
        dto.setPuntajeB(3);
        dto.setEstadoValidacion("PENDIENTE");
        dto.setFechaRegistro(LocalDate.now());

        when(resultadosRepository.findById(1L)).thenReturn(Optional.of(resultadoMock));
        when(resultadosRepository.save(any(Resultado.class))).thenReturn(resultadoMock);

        ResultadoResponseDTO result = resultadoService.actualizarResultado(1L, dto);

        assertThat(result.getGanadorId()).isEqualTo(200L);
        assertThat(result.getPuntajeB()).isEqualTo(3);
        verify(resultadosRepository, times(1)).save(resultadoMock);
    }

    @Test
    @DisplayName("shouldValidarResultadoSuccessfully")
    public void shouldValidarResultadoSuccessfully() {
        when(resultadosRepository.findById(1L)).thenReturn(Optional.of(resultadoMock));
        when(resultadosRepository.save(any(Resultado.class))).thenReturn(resultadoMock);

        String result = resultadoService.validarResultado(1L);

        assertThat(result).isEqualTo("Resultado validado");
        assertThat(resultadoMock.getEstadoValidacion()).isEqualTo("VALIDADO");
        verify(resultadosRepository, times(1)).save(resultadoMock);
    }

    @Test
    @DisplayName("shouldAnularResultadoSuccessfully")
    public void shouldAnularResultadoSuccessfully() {
        when(resultadosRepository.findById(1L)).thenReturn(Optional.of(resultadoMock));
        when(resultadosRepository.save(any(Resultado.class))).thenReturn(resultadoMock);

        String result = resultadoService.anularResultado(1L);

        assertThat(result).isEqualTo("Resultado anulado");
        assertThat(resultadoMock.getEstadoValidacion()).isEqualTo("ANULADO");
        verify(resultadosRepository, times(1)).save(resultadoMock);
    }
}
