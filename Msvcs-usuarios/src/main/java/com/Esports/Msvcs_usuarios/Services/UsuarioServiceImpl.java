package com.Esports.Msvcs_usuarios.Services;

import com.Esports.Msvcs_usuarios.exceptions.ResourceNotFoundException;
import com.Esports.Msvcs_usuarios.models.Usuario;
import com.Esports.Msvcs_usuarios.models.dtos.ActualizarUsuarioDTO;
import com.Esports.Msvcs_usuarios.models.dtos.CrearUsuarioDTO;
import com.Esports.Msvcs_usuarios.models.dtos.UsuarioResponseDTO;
import com.Esports.Msvcs_usuarios.repositories.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UsuarioServiceImpl.class);

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Override
    public UsuarioResponseDTO crearUsuario(CrearUsuarioDTO dto) {
        log.info("Creando usuario con correo: {}", dto.getCorreo());
        Usuario usuario = new Usuario();
        usuario.setNombreusuario(dto.getNombreusuario());
        usuario.setNickname(dto.getNickname());
        usuario.setCorreo(dto.getCorreo());
        usuario.setRol(dto.getRol());
        usuario.setEstadousuario(dto.getEstadousuario());
        usuario.setFechaRegistro(LocalDate.now());

        usuariosRepository.save(usuario);

        UsuarioResponseDTO response = new UsuarioResponseDTO();

        response.setUsuarioId(usuario.getUsuarioId());
        response.setNombreusuario(usuario.getNombreusuario());
        response.setNickname(usuario.getNickname());
        response.setCorreo(usuario.getCorreo());
        response.setRol(usuario.getRol());
        response.setEstadousuario(usuario.getEstadousuario());
        response.setFechaRegistro(usuario.getFechaRegistro());

        log.info("Usuario creado exitosamente con ID: {}", usuario.getUsuarioId());
        return response;
    }

    @Override
    public List<UsuarioResponseDTO> ListarUsuarios() {
        log.info("Listando todos los usuarios");
        List<Usuario> usuarios = usuariosRepository.findAll();
        return usuarios.stream().map(usuario -> {
            UsuarioResponseDTO dto = new UsuarioResponseDTO();

            dto.setUsuarioId(usuario.getUsuarioId());
            dto.setNombreusuario(usuario.getNombreusuario());
            dto.setNickname(usuario.getNickname());
            dto.setCorreo(usuario.getCorreo());
            dto.setRol(usuario.getRol());
            dto.setEstadousuario(usuario.getEstadousuario());
            dto.setFechaRegistro(usuario.getFechaRegistro());

            return dto;
        }).toList();
    }

    @Override
    public UsuarioResponseDTO BuscarPorId(Long usuarioId) {
        log.info("Buscando usuario con ID: {}", usuarioId);
        Usuario usuario = usuariosRepository.findById(usuarioId).orElseThrow(() -> {
            log.warn("Usuario no encontrado con ID: {}", usuarioId);
            return new ResourceNotFoundException("Usuario no encontrado");
        });
        log.info("Usuario encontrado: {}", usuario.getNickname());

        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setUsuarioId(usuario.getUsuarioId());
        dto.setNombreusuario(usuario.getNombreusuario());
        dto.setNickname(usuario.getNickname());
        dto.setCorreo(usuario.getCorreo());
        dto.setRol(usuario.getRol());
        dto.setEstadousuario(usuario.getEstadousuario());
        dto.setFechaRegistro(usuario.getFechaRegistro());

        return dto;
    }

    @Override
    public UsuarioResponseDTO actualizarUsuario(Long usuarioId, ActualizarUsuarioDTO dto) {

        log.info("Actualizando usuario con ID: {}", usuarioId);
        Usuario usuario = usuariosRepository.findById(usuarioId).orElseThrow(() -> {
            log.warn("Usuario no encontrado con ID: {}", usuarioId);
            return new ResourceNotFoundException("Usuario no encontrado");
        });

        usuario.setNombreusuario(dto.getNombreusuario());
        usuario.setNickname(dto.getNickname());
        usuario.setCorreo(dto.getCorreo());
        usuario.setRol(dto.getRol());
        usuario.setEstadousuario(dto.getEstadousuario());
        usuario.setFechaRegistro(dto.getFechaRegistro());

        usuariosRepository.save(usuario);

        UsuarioResponseDTO response = new UsuarioResponseDTO();

        response.setUsuarioId(usuario.getUsuarioId());
        response.setNombreusuario(usuario.getNombreusuario());
        response.setNickname(usuario.getNickname());
        response.setCorreo(usuario.getCorreo());
        response.setRol(usuario.getRol());
        response.setEstadousuario(usuario.getEstadousuario());
        response.setFechaRegistro(usuario.getFechaRegistro());

        log.info("Usuario actualizado exitosamente con ID: {}", usuarioId);
        return response;
    }

    @Override
    public String DesactivarUsuario(Long usuarioId) {

        log.info("Desactivando usuario con ID: {}", usuarioId);
        Usuario usuario = usuariosRepository.findById(usuarioId).orElseThrow(() -> {
            log.warn("Usuario no encontrado con ID: {}", usuarioId);
            return new ResourceNotFoundException("Usuario no encontrado");
        });

        usuario.setEstadousuario("INACTIVO");

        usuariosRepository.save(usuario);

        log.info("Usuario desactivado exitosamente con ID: {}", usuarioId);
        return "Usuario Desactivado correctamente";
    }
}
