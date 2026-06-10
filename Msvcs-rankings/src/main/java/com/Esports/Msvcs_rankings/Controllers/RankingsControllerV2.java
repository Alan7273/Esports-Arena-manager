package com.Esports.Msvcs_rankings.Controllers;

import com.Esports.Msvcs_rankings.Services.RankingService;
import com.Esports.Msvcs_rankings.models.dtos.RankingResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rankings")
@Validated
public class RankingsControllerV2 {

    @Autowired
    private RankingService rankingService;

    @PutMapping("/torneo/{torneoId}/participante/{participanteId}")
    public ResponseEntity<String> crearRanking(@PathVariable Long torneoId, @PathVariable Long participanteId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rankingService.crearRankingParticipante(torneoId, participanteId));
    }

    @GetMapping("/{torneoId}")
    public ResponseEntity<List<RankingResponseDTO>> obtenerTabla(@PathVariable Long torneoId) {
        return ResponseEntity.status(HttpStatus.OK).body(rankingService.obtenerTabla(torneoId));
    }

    @GetMapping("/participante/{id}")
    public ResponseEntity<RankingResponseDTO> buscarRanking(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(rankingService.buscarRanking(id));
    }

    @PutMapping("/recalcular/{torneoId}")
    public ResponseEntity<String> actualizarRanking(@PathVariable Long torneoId) {
        return ResponseEntity.status(HttpStatus.OK).body(rankingService.actualizarRanking(torneoId));
    }

    @DeleteMapping("/reiniciar/{torneoId}")
    public ResponseEntity<String> reiniciarRanking(@PathVariable Long torneoId) {
        return ResponseEntity.status(HttpStatus.OK).body(rankingService.reiniciarRanking(torneoId));
    }
}
