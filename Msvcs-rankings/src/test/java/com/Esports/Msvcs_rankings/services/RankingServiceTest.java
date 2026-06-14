package com.Esports.Msvcs_rankings.services;

import com.Esports.Msvcs_rankings.Services.RankingServiceImpl;
import com.Esports.Msvcs_rankings.exceptions.ResourceNotFoundException;
import com.Esports.Msvcs_rankings.models.Ranking;
import com.Esports.Msvcs_rankings.models.dtos.RankingResponseDTO;
import com.Esports.Msvcs_rankings.repositories.RankingsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RankingServiceTest {

    @Mock
    private RankingsRepository rankingRepository;

    @InjectMocks
    private RankingServiceImpl rankingService;

    private Ranking rankingMock;

    @BeforeEach
    public void setUp() {
        rankingMock = new Ranking();
        rankingMock.setRankingId(1L);
        rankingMock.setTorneoId(10L);
        rankingMock.setParticipanteId(100L);
        rankingMock.setPuntos(0);
        rankingMock.setVictorias(0);
        rankingMock.setDerrotas(0);
        rankingMock.setDiferencia(0);
        rankingMock.setPosicion(0);
    }

    @Test
    @DisplayName("shouldCreateRankingParticipanteSuccessfully")
    public void shouldCreateRankingParticipanteSuccessfully() {
        when(rankingRepository.save(any(Ranking.class))).thenReturn(rankingMock);

        String result = rankingService.crearRankingParticipante(10L, 100L);

        assertThat(result).isEqualTo("Ranking creado para participante 100");
        verify(rankingRepository, times(1)).save(any(Ranking.class));
    }

    @Test
    @DisplayName("shouldReturnTablaDeRanking")
    public void shouldReturnTablaDeRanking() {
        when(rankingRepository.findByTorneoId(10L)).thenReturn(List.of(rankingMock));

        List<RankingResponseDTO> result = rankingService.obtenerTabla(10L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getParticipanteId()).isEqualTo(100L);
        verify(rankingRepository, times(1)).findByTorneoId(10L);
    }

    @Test
    @DisplayName("shouldFindRankingById")
    public void shouldFindRankingById() {
        when(rankingRepository.findById(1L)).thenReturn(Optional.of(rankingMock));

        RankingResponseDTO result = rankingService.buscarRanking(1L);

        assertThat(result.getRankingId()).isEqualTo(1L);
        verify(rankingRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("shouldThrowExceptionWhenRankingNotFound")
    public void shouldThrowExceptionWhenRankingNotFound() {
        when(rankingRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> rankingService.buscarRanking(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Ranking no encontrado");
    }

    @Test
    @DisplayName("shouldUpdateRankingPositionsByPuntos")
    public void shouldUpdateRankingPositionsByPuntos() {
        Ranking r1 = new Ranking();
        r1.setRankingId(1L);
        r1.setTorneoId(10L);
        r1.setParticipanteId(100L);
        r1.setPuntos(3);
        r1.setVictorias(1);
        r1.setDerrotas(0);
        r1.setDiferencia(2);
        r1.setPosicion(0);

        Ranking r2 = new Ranking();
        r2.setRankingId(2L);
        r2.setTorneoId(10L);
        r2.setParticipanteId(200L);
        r2.setPuntos(6);
        r2.setVictorias(2);
        r2.setDerrotas(0);
        r2.setDiferencia(4);
        r2.setPosicion(0);

        List<Ranking> rankings = new ArrayList<>(List.of(r1, r2));

        when(rankingRepository.findByTorneoId(10L)).thenReturn(rankings);
        when(rankingRepository.saveAll(anyList())).thenReturn(rankings);

        String result = rankingService.actualizarRanking(10L);

        assertThat(result).isEqualTo("Ranking actualizado correctamente");
        assertThat(r2.getPosicion()).isEqualTo(1); // mayor puntaje -> posicion 1
        assertThat(r1.getPosicion()).isEqualTo(2);
        verify(rankingRepository, times(1)).saveAll(rankings);
    }

    @Test
    @DisplayName("shouldThrowExceptionWhenUpdatingEmptyRankingTable")
    public void shouldThrowExceptionWhenUpdatingEmptyRankingTable() {
        when(rankingRepository.findByTorneoId(99L)).thenReturn(List.of());

        assertThatThrownBy(() -> rankingService.actualizarRanking(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No se encontraron rankings para el torneo");

        verify(rankingRepository, never()).saveAll(anyList());
    }

    @Test
    @DisplayName("shouldResetRankingSuccessfully")
    public void shouldResetRankingSuccessfully() {
        rankingMock.setPuntos(10);
        rankingMock.setVictorias(3);
        rankingMock.setDerrotas(1);
        rankingMock.setDiferencia(5);
        rankingMock.setPosicion(1);

        List<Ranking> rankings = new ArrayList<>(List.of(rankingMock));

        when(rankingRepository.findByTorneoId(10L)).thenReturn(rankings);
        when(rankingRepository.saveAll(anyList())).thenReturn(rankings);

        String result = rankingService.reiniciarRanking(10L);

        assertThat(result).isEqualTo("Ranking reiniciado correctamente");
        assertThat(rankingMock.getPuntos()).isEqualTo(0);
        assertThat(rankingMock.getVictorias()).isEqualTo(0);
        assertThat(rankingMock.getDerrotas()).isEqualTo(0);
        assertThat(rankingMock.getDiferencia()).isEqualTo(0);
        assertThat(rankingMock.getPosicion()).isEqualTo(0);
        verify(rankingRepository, times(1)).saveAll(rankings);
    }

    @Test
    @DisplayName("shouldThrowExceptionWhenResettingEmptyRankingTable")
    public void shouldThrowExceptionWhenResettingEmptyRankingTable() {
        when(rankingRepository.findByTorneoId(99L)).thenReturn(List.of());

        assertThatThrownBy(() -> rankingService.reiniciarRanking(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No se encontraron rankings para el torneo");
    }
}
