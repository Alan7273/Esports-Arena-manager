package com.Esports.Msvcs_sanciones.Controllers;

import com.Esports.Msvcs_sanciones.Services.SancionesService;
import com.Esports.Msvcs_sanciones.models.Sancion;
import com.Esports.Msvcs_sanciones.models.dtos.ActualizarSancionDTO;
import com.Esports.Msvcs_sanciones.models.dtos.CrearSancionDTO;
import com.Esports.Msvcs_sanciones.models.dtos.SancionResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sanciones")
public class SancionesController {

    @Autowired
    private SancionesService sancionesService;

    @PostMapping
    public ResponseEntity<SancionResponseDTO> crearSancion(@Valid @RequestBody CrearSancionDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sancionesService.crearSancion(dto));
    }

    @GetMapping
    public ResponseEntity<List<SancionResponseDTO>> listarSanciones() {
        return ResponseEntity.status(HttpStatus.OK).body(sancionesService.listarSanciones());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SancionResponseDTO> buscarSancion(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(sancionesService.buscarSancion(id));
    }

    @DeleteMapping("/cerrar/{id}")
    public ResponseEntity<String> cerrarSancion(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(sancionesService.cerrarSancion(id));
    }

    @GetMapping("/validar/{usuarioId}")
    public ResponseEntity<Boolean> validarSancionActiva(@PathVariable Long usuarioId) {
        return ResponseEntity.status(HttpStatus.OK).body(sancionesService.validarSancionActiva(usuarioId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SancionResponseDTO> actualizarSancion(@PathVariable Long id, @Valid @RequestBody ActualizarSancionDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(sancionesService.actualizarSancion(id, dto));
    }
}
