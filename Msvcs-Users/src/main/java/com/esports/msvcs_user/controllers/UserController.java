package com.esports.msvcs_user.controllers;

import com.esports.msvcs_user.dtos.UserDTO;
import com.esports.msvcs_user.models.Rol;
import com.esports.msvcs_user.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

// Endpoint PROTEGIDO: solo un ADMIN puede listar los usuarios. Demuestra el control por rol.
@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuarios", description = "Gestion de usuarios (requiere token)")
@SecurityRequirement(name = "bearer-jwt")
public class UserController {

    private final UserRepository usuarioRepository;

    public UserController(UserRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    @Operation(summary = "Listar usuarios", description = "Solo ADMIN. Devuelve usuarios sin la contrasena.")
    // @PreAuthorize evalua la expresion ANTES de ejecutar el metodo. hasRole('ADMIN') exige el authority ROLE_ADMIN.
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> findAll() {
        List<UserDTO> usuarios = this.usuarioRepository.findAll().stream()
                .map(u -> new UserDTO(
                        u.getUsuarioId(),
                        u.getUsername(),
                        u.getRoles().stream().map(Rol::getNombre).collect(Collectors.toSet())))
                .toList();
        return ResponseEntity.ok(usuarios);
    }
}
