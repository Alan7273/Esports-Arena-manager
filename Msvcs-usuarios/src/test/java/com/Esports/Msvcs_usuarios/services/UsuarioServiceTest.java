package com.Esports.Msvcs_usuarios.services;

import com.Esports.Msvcs_usuarios.Services.UsuarioServiceImpl;
import com.Esports.Msvcs_usuarios.exceptions.ResourceNotFoundException;
import com.Esports.Msvcs_usuarios.models.Usuario;
import com.Esports.Msvcs_usuarios.models.dtos.ActualizarUsuarioDTO;
import com.Esports.Msvcs_usuarios.models.dtos.CrearUsuarioDTO;
import com.Esports.Msvcs_usuarios.models.dtos.UsuarioResponseDTO;
import com.Esports.Msvcs_usuarios.repositories.UsuariosRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuariosRepository usuariosRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private Usuario usuarioPrueba;
    private List<Usuario> usuariosList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        this.usuarioPrueba = new Usuario();
        this.usuarioPrueba.setUsuarioId(1L);
        this.usuarioPrueba.setNombreusuario("Jorge");
        this.usuarioPrueba.setNickname("Jorge");
        this.usuarioPrueba.setRol("ADMIN");
        this.usuarioPrueba.setCorreo("jorge.s@gmail.com");
        this.usuarioPrueba.setEstadousuario("ACTIVO");
        this.usuarioPrueba.setFechaRegistro(LocalDate.of(2026, 6, 23));

        Faker faker = new Faker(Locale.of("es", "CL"));
        for (int i = 0; i < 5; i++) {
            Usuario usuario = new Usuario();
            usuario.setUsuarioId((long) (i + 2));
            usuario.setNombreusuario(faker.name().fullName());
            usuario.setNickname(faker.name().username());
            usuario.setCorreo(faker.internet().emailAddress());
            usuario.setRol("JUGADOR");
            usuario.setEstadousuario("ACTIVO");
            usuario.setFechaRegistro(LocalDate.now());
            usuariosList.add(usuario);
        }
    }

    @Test
    @DisplayName("shouldListAllUsuarios")
    public void listAllUsuarios() {
        List<Usuario> usuarios = new ArrayList<>(this.usuariosList);
        usuarios.add(this.usuarioPrueba);

        when(this.usuariosRepository.findAll()).thenReturn(usuarios);

        List<UsuarioResponseDTO> result = this.usuarioService.ListarUsuarios();

        assertThat(result).hasSize(usuarios.size());
        assertThat(result).anyMatch(u -> u.getNickname().equals("Jorge"));
        verify(this.usuariosRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("shouldFindUsuarioById")
    public void shouldFindUsuarioById() {
        when(this.usuariosRepository.findById(1L)).thenReturn(Optional.of(this.usuarioPrueba));

        UsuarioResponseDTO result = this.usuarioService.BuscarPorId(1L);

        assertThat(result.getUsuarioId()).isEqualTo(1L);
        assertThat(result.getNickname()).isEqualTo("Jorge");
        verify(this.usuariosRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("shouldNotFindUsuarioById")
    public void shouldNotFindUsuarioById() {
        Long id = 9999L;
        when(this.usuariosRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> this.usuarioService.BuscarPorId(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Usuario no encontrado");

        verify(this.usuariosRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("shouldCreateUsuarioSuccessfully")
    public void shouldCreateUsuarioSuccessfully() {
        CrearUsuarioDTO dto = new CrearUsuarioDTO();
        dto.setNombreusuario("Camila Rojas");
        dto.setNickname("camilarojas");
        dto.setCorreo("camila@gmail.com");
        dto.setRol("JUGADOR");
        dto.setEstadousuario("ACTIVO");
        dto.setFechaRegistro(LocalDate.of(2026, 1, 10));

        Usuario guardado = new Usuario();
        guardado.setUsuarioId(50L);
        guardado.setNombreusuario("Camila Rojas");
        guardado.setNickname("camilarojas");
        guardado.setCorreo("camila@gmail.com");
        guardado.setRol("JUGADOR");
        guardado.setEstadousuario("ACTIVO");
        guardado.setFechaRegistro(LocalDate.of(2026, 1, 10));

        when(this.usuariosRepository.save(any(Usuario.class))).thenReturn(guardado);

        UsuarioResponseDTO result = this.usuarioService.crearUsuario(dto);

        assertThat(result.getNickname()).isEqualTo("camilarojas");
        assertThat(result.getEstadousuario()).isEqualTo("ACTIVO");
        verify(this.usuariosRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("shouldUpdateUsuarioSuccessfully")
    public void shouldUpdateUsuarioSuccessfully() {
        ActualizarUsuarioDTO dto = new ActualizarUsuarioDTO();
        dto.setNombreusuario("Jorge Soto");
        dto.setNickname("jorgesoto");
        dto.setCorreo("jorge.soto@gmail.com");
        dto.setRol("ADMIN");
        dto.setEstadousuario("ACTIVO");
        dto.setFechaRegistro(LocalDate.of(2026, 6, 23));

        when(this.usuariosRepository.findById(1L)).thenReturn(Optional.of(this.usuarioPrueba));
        when(this.usuariosRepository.save(any(Usuario.class))).thenReturn(this.usuarioPrueba);

        UsuarioResponseDTO result = this.usuarioService.actualizarUsuario(1L, dto);

        assertThat(result.getNickname()).isEqualTo("jorgesoto");
        assertThat(result.getCorreo()).isEqualTo("jorge.soto@gmail.com");
        verify(this.usuariosRepository, times(1)).save(this.usuarioPrueba);
    }

    @Test
    @DisplayName("shouldDeactivateUsuarioSuccessfully")
    public void shouldDeactivateUsuarioSuccessfully() {
        when(this.usuariosRepository.findById(1L)).thenReturn(Optional.of(this.usuarioPrueba));
        when(this.usuariosRepository.save(any(Usuario.class))).thenReturn(this.usuarioPrueba);

        String result = this.usuarioService.DesactivarUsuario(1L);

        assertThat(result).isEqualTo("Usuario Desactivado correctamente");
        assertThat(this.usuarioPrueba.getEstadousuario()).isEqualTo("INACTIVO");
        verify(this.usuariosRepository, times(1)).save(this.usuarioPrueba);
    }
}
