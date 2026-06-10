package com.Esports.Msvcs_equipos.Controllers;
import com.Esports.Msvcs_equipos.Services.EquipoService;
import com.Esports.Msvcs_equipos.models.dtos.AgregarMiembroDTO;
import com.Esports.Msvcs_equipos.models.dtos.CrearEquipoDTO;
import com.Esports.Msvcs_equipos.models.dtos.EquipoResponseDTO;
import com.Esports.Msvcs_equipos.models.dtos.MiembroEquipoResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/equipos")
@Validated
public class EquiposControllerV2 {

    @Autowired
    private EquipoService equipoService;

    @PostMapping
    public ResponseEntity<EquipoResponseDTO> crearEquipo(@Valid @RequestBody CrearEquipoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(equipoService.crearEquipo(dto));
    }

    @GetMapping
    public ResponseEntity<List<EquipoResponseDTO>> listarEquipos() {
        return ResponseEntity.status(HttpStatus.OK).body(equipoService.listarEquipos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipoResponseDTO> buscarEquipo(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(equipoService.buscarEquipo(id));
    }

    @PostMapping("/{id}/miembros")
    public ResponseEntity<MiembroEquipoResponseDTO> agregarMiembro(@PathVariable Long id, @Valid @RequestBody AgregarMiembroDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(equipoService.agregarMiembro(id, dto));
    }

    @DeleteMapping("/{equipoId}/miembros/{usuarioId}")
    public ResponseEntity<Void> eliminarMiembro(@PathVariable Long equipoId, @PathVariable Long usuarioId) {
        equipoService.eliminarMiembro(equipoId, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/capitan/{id}")
    public ResponseEntity<EquipoResponseDTO> actualizarCapitan(@PathVariable Long id, @RequestParam Long capitanId) {
        return ResponseEntity.status(HttpStatus.OK).body(equipoService.actualizarCapitan(id, capitanId));
    }

    @DeleteMapping("/desactivar/{id}")
    public ResponseEntity<String> desactivarEquipo(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(equipoService.desactivarEquipo(id));
    }

    @GetMapping("/{id}/existe")
    public ResponseEntity<Boolean> existeEquipo(@PathVariable Long id) {
        try {
            equipoService.buscarEquipo(id);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(false);
        }
    }
}
