package com.Esports.Msvcs_juegos.Controllers;

import com.Esports.Msvcs_juegos.Services.JuegosService;
import com.Esports.Msvcs_juegos.models.Juegos;
import com.Esports.Msvcs_juegos.models.dtos.ActualizarJuegoDTO;
import com.Esports.Msvcs_juegos.models.dtos.CrearJuegoDTO;
import com.Esports.Msvcs_juegos.models.dtos.JuegoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Juegos", description = "Operaciones del microservicio de Juegos")
public class JuegosController {

    @Autowired
    private JuegosService juegosService;

    @Operation(summary = "Crear un juego", description = "Crea un nuevo juego en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Juego creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos")
    })
    @PostMapping
    public ResponseEntity<JuegoResponseDTO> crearJuego(@Valid @RequestBody CrearJuegoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(juegosService.CrearJuego(dto));
    }

    @Operation(summary = "Listar juegos", description = "Devuelve todos los jeugos registrados")
    @GetMapping
    public ResponseEntity<List<JuegoResponseDTO>> listarJuegos() {
        return ResponseEntity.status(HttpStatus.OK).body(juegosService.ListarJuegos());
    }

    @Operation(summary = "Buscar juego por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Juego encontrado"),
            @ApiResponse(responseCode = "404", description = "Juego no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<JuegoResponseDTO> buscarJuego(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(juegosService.BuscarJuego(id));
    }

    @Operation(summary = "Actualizar un juego existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Juego actualizado"),
            @ApiResponse(responseCode = "404", description = "Juego no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<JuegoResponseDTO> actualizarJuego(@PathVariable Long id, @Valid @RequestBody ActualizarJuegoDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(juegosService.actualizarJuego(id, dto));
    }

    @Operation(summary = "Desactivar un juego (borrado lógico)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Juego desactivado"),
            @ApiResponse(responseCode = "404", description = "Juego no encontrado")
    })
    @DeleteMapping("/desactivar/{id}")
    public ResponseEntity<String> desactivarJuego(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(juegosService.desactivarJuego(id));
    }

    @Operation(summary = "Verificar si un juego existe", description = "Usado por otros microservicios vía Feign")
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
