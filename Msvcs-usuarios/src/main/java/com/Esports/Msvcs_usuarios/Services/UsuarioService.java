package com.Esports.Msvcs_usuarios.Services;

import com.Esports.Msvcs_usuarios.models.Usuario;
import com.Esports.Msvcs_usuarios.models.dtos.ActualizarUsuarioDTO;
import com.Esports.Msvcs_usuarios.models.dtos.CrearUsuarioDTO;
import com.Esports.Msvcs_usuarios.models.dtos.UsuarioResponseDTO;

import java.util.List;

public interface UsuarioService {
    UsuarioResponseDTO crearUsuario(CrearUsuarioDTO dto);
    List<UsuarioResponseDTO> ListarUsuarios();
    UsuarioResponseDTO BuscarPorId(Long usuarioId);
    UsuarioResponseDTO actualizarUsuario(Long usuarioId, ActualizarUsuarioDTO dto);
    String DesactivarUsuario(Long usuarioId);
}
