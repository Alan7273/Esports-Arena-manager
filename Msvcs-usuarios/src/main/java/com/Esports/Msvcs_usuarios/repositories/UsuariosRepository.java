package com.Esports.Msvcs_usuarios.repositories;

import com.Esports.Msvcs_usuarios.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuario, Long> {
    // Metodo que me permite buscar por el nickname
    Optional<Usuario> findByNickname(String nickname);

    // Metodo que me permite buscar por el rol
    Optional<Usuario> findByRol(String rol);

    // Metodo que me permite buscar por estado del usuario
    List<Usuario> findByEstadousuario(String estadousuario);

    // Metodo que me permite buscar si el nickname existe
    Boolean existsByNickname(String nickname);

    // Metodo que me permite buscar si el correo existe
    Boolean existsByCorreo(String correo);
}
