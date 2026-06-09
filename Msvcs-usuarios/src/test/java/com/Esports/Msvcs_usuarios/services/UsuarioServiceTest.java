package com.Esports.Msvcs_usuarios.services;

import com.Esports.Msvcs_usuarios.Services.UsuarioService;
import com.Esports.Msvcs_usuarios.Services.UsuarioServiceImpl;
import com.Esports.Msvcs_usuarios.exceptions.ResourceNotFoundException;
import com.Esports.Msvcs_usuarios.models.Usuario;
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
        this.usuarioPrueba.setNombreusuario("Jorge");
        this.usuarioPrueba.setNickname("Jorge");
        this.usuarioPrueba.setRol("ADMIN");
        this.usuarioPrueba.setCorreo("jorge.s@Gmail.com");
        this.usuarioPrueba.setEstadousuario("ACTIVO");
        this.usuarioPrueba.setFechaRegistro(LocalDate.ofEpochDay(23/06/2026));

        Faker faker = new Faker(Locale.of("es", "CL"));
        for(int i = 0; i <100; i++) {
            Usuario usuario = new Usuario();
            String numeroStr = faker.idNumber().valid().replace("-", "");
            String ultimo = numeroStr.substring(numeroStr.length() - 1);
            String restante = numeroStr.substring(0, numeroStr.length() - 1);

            usuario.setNombreusuario(restante + "-" + ultimo);
            usuario.setNombreusuario(faker.name().fullName());

            usuariosList.add(usuario);
        }
    }
    @Test
    @DisplayName("should be list all usuarios")
    public void listAllUsuarios() {
        List<Usuario> usuarios = this.usuariosList;
        usuarios.add(this.usuarioPrueba);

        when(this.usuariosRepository.findAll()).thenReturn(usuarios);

        List<Usuario> result = this.usuariosList;

        //ASSERT
        assertThat(result).hasSize(usuariosList.size());
        assertThat(result).contains(this.usuarioPrueba);
        verify(this.usuariosRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe buscar un medico con un id inexistente")
    public void shouldNotFindUsuarioById() {
        Long id = 9999L;
        when(this.usuariosRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            this.usuarioService.BuscarPorId(id);
        }).isInstanceOf(ResourceNotFoundException.class)
                        .hasMessage("Medico no encontrado");
        verify(this.usuariosRepository, times(1)).findById(id);
    }
}
