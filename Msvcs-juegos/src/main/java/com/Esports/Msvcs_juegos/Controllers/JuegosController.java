package com.Esports.Msvcs_juegos.Controllers;

import com.Esports.Msvcs_juegos.Services.JuegosService;
import com.Esports.Msvcs_juegos.models.Juegos;
import com.Esports.Msvcs_juegos.models.dtos.ActualizarJuegoDTO;
import com.Esports.Msvcs_juegos.models.dtos.CrearJuegoDTO;
import com.Esports.Msvcs_juegos.models.dtos.JuegoResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/juegos")
@Validated
public class JuegosController {

    @Autowired
    private JuegosService juegosService;

    @PostMapping
    public ResponseEntity<JuegoResponseDTO> crearJuego(@Valid @RequestBody CrearJuegoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(juegosService.CrearJuego(dto));
    }

    @GetMapping
    public ResponseEntity<List<JuegoResponseDTO>> listarJuegos() {
        return ResponseEntity.status(HttpStatus.OK).body(juegosService.ListarJuegos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JuegoResponseDTO> buscarJuego(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(juegosService.BuscarJuego(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JuegoResponseDTO> actualizarJuego(@PathVariable Long id, @Valid @RequestBody ActualizarJuegoDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(juegosService.actualizarJuego(id, dto));
    }

    @DeleteMapping("/desactivar/{id}")
    public ResponseEntity<String> desactivarJuego(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(juegosService.desactivarJuego(id));
    }

    @GetMapping("/{id}/existe")
    public ResponseEntity<Boolean> existeJuego(@PathVariable Long id) {
        try {
            juegosService.BuscarJuego(id);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }
}
