package com.Esports.Msvcs_equipos.services;

import com.Esports.Msvcs_equipos.Services.EquipoServiceImpl;
import com.Esports.Msvcs_equipos.clients.JuegoClient;
import com.Esports.Msvcs_equipos.clients.UsuarioClient;
import com.Esports.Msvcs_equipos.exceptions.ResourceNotFoundException;
import com.Esports.Msvcs_equipos.models.Equipos;
import com.Esports.Msvcs_equipos.models.Miembro_equipo;
import com.Esports.Msvcs_equipos.models.dtos.AgregarMiembroDTO;
import com.Esports.Msvcs_equipos.models.dtos.CrearEquipoDTO;
import com.Esports.Msvcs_equipos.models.dtos.EquipoResponseDTO;
import com.Esports.Msvcs_equipos.models.dtos.MiembroEquipoResponseDTO;
import com.Esports.Msvcs_equipos.repositories.EquiposRepository;
import com.Esports.Msvcs_equipos.repositories.MiembroEquipoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EquipoServiceTest {

    @Mock
    private EquiposRepository equiposRepository;

    @Mock
    private MiembroEquipoRepository miembroEquipoRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @Mock
    private JuegoClient juegoClient;

    @InjectMocks
    private EquipoServiceImpl equipoService;

    private Equipos equipoMock;

    @BeforeEach
    public void setUp() {
        equipoMock = new Equipos();
        equipoMock.setEquipoId(1L);
        equipoMock.setNombreequipo("Los Invencibles");
        equipoMock.setCapitanId(10L);
        equipoMock.setJuegoprincipalId(100L);
        equipoMock.setEstadoequipo("ACTIVO");
    }

    @Test
    @DisplayName("shouldCreateEquipoSuccessfully")
    public void shouldCreateEquipoSuccessfully() {
        CrearEquipoDTO dto = new CrearEquipoDTO();
        dto.setNombreequipo("Los Invencibles");
        dto.setCapitanId(10L);
        dto.setJuegoprincipalId(100L);
        dto.setEstadoequipo("ACTIVO");

        when(usuarioClient.existeUsuario(10L)).thenReturn(true);
        when(juegoClient.existeJuego(100L)).thenReturn(true);
        when(equiposRepository.save(any(Equipos.class))).thenReturn(equipoMock);

        EquipoResponseDTO result = equipoService.crearEquipo(dto);

        assertThat(result.getNombreequipo()).isEqualTo("Los Invencibles");
        assertThat(result.getEstadoequipo()).isEqualTo("ACTIVO");
        verify(equiposRepository, times(1)).save(any(Equipos.class));
    }

    @Test
    @DisplayName("shouldThrowExceptionWhenCapitanDoesNotExist")
    public void shouldThrowExceptionWhenCapitanDoesNotExist() {
        CrearEquipoDTO dto = new CrearEquipoDTO();
        dto.setNombreequipo("Los Invencibles");
        dto.setCapitanId(99L);
        dto.setJuegoprincipalId(100L);
        dto.setEstadoequipo("ACTIVO");

        when(usuarioClient.existeUsuario(99L)).thenReturn(false);

        assertThatThrownBy(() -> equipoService.crearEquipo(dto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("El usuario capitan no existe");

        verify(equiposRepository, never()).save(any(Equipos.class));
    }

    @Test
    @DisplayName("shouldReturnAllEquipos")
    public void shouldReturnAllEquipos() {
        when(equiposRepository.findAll()).thenReturn(List.of(equipoMock));

        var result = equipoService.listarEquipos();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNombreequipo()).isEqualTo("Los Invencibles");
        verify(equiposRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("shouldFindEquipoById")
    public void shouldFindEquipoById() {
        when(equiposRepository.findById(1L)).thenReturn(Optional.of(equipoMock));

        EquipoResponseDTO result = equipoService.buscarEquipo(1L);

        assertThat(result.getEquipoId()).isEqualTo(1L);
        verify(equiposRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("shouldThrowExceptionWhenEquipoNotFound")
    public void shouldThrowExceptionWhenEquipoNotFound() {
        when(equiposRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> equipoService.buscarEquipo(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Equipo no encontrado");
    }

    @Test
    @DisplayName("shouldAddMiembroSuccessfully")
    public void shouldAddMiembroSuccessfully() {
        AgregarMiembroDTO dto = new AgregarMiembroDTO();
        dto.setUsuarioId(20L);
        dto.setRolDentroEquipo("Soporte");

        when(usuarioClient.existeUsuario(20L)).thenReturn(true);
        when(miembroEquipoRepository.existsByEquipoIdAndUsuarioId(1L, 20L)).thenReturn(false);

        Miembro_equipo guardado = new Miembro_equipo();
        guardado.setMiembroId(5L);
        guardado.setEquipoId(1L);
        guardado.setUsuarioId(20L);
        guardado.setRolDentroEquipo("Soporte");
        guardado.setFechaIngreso(LocalDateTime.now());

        when(miembroEquipoRepository.save(any(Miembro_equipo.class))).thenReturn(guardado);

        MiembroEquipoResponseDTO result = equipoService.agregarMiembro(1L, dto);

        assertThat(result.getUsuarioId()).isEqualTo(20L);
        assertThat(result.getRolDentroEquipo()).isEqualTo("Soporte");
        verify(miembroEquipoRepository, times(1)).save(any(Miembro_equipo.class));
    }

    @Test
    @DisplayName("shouldThrowExceptionWhenMiembroAlreadyExists")
    public void shouldThrowExceptionWhenMiembroAlreadyExists() {
        AgregarMiembroDTO dto = new AgregarMiembroDTO();
        dto.setUsuarioId(20L);
        dto.setRolDentroEquipo("Soporte");

        when(usuarioClient.existeUsuario(20L)).thenReturn(true);
        when(miembroEquipoRepository.existsByEquipoIdAndUsuarioId(1L, 20L)).thenReturn(true);

        assertThatThrownBy(() -> equipoService.agregarMiembro(1L, dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("El usuario ya pertenece al equipo");
    }

    @Test
    @DisplayName("shouldRemoveMiembroSuccessfully")
    public void shouldRemoveMiembroSuccessfully() {
        Miembro_equipo miembro = new Miembro_equipo();
        miembro.setMiembroId(5L);
        miembro.setEquipoId(1L);
        miembro.setUsuarioId(20L);

        when(miembroEquipoRepository.findByEquipoIdAndUsuarioId(1L, 20L)).thenReturn(Optional.of(miembro));

        String result = equipoService.eliminarMiembro(1L, 20L);

        assertThat(result).isEqualTo("Miembro eliminado");
        verify(miembroEquipoRepository, times(1)).delete(miembro);
    }

    @Test
    @DisplayName("shouldUpdateCapitanSuccessfully")
    public void shouldUpdateCapitanSuccessfully() {
        when(equiposRepository.findById(1L)).thenReturn(Optional.of(equipoMock));
        when(equiposRepository.save(any(Equipos.class))).thenReturn(equipoMock);

        EquipoResponseDTO result = equipoService.actualizarCapitan(1L, 50L);

        assertThat(result.getCapitanId()).isEqualTo(50L);
        verify(equiposRepository, times(1)).save(equipoMock);
    }

    @Test
    @DisplayName("shouldDeactivateEquipoSuccessfully")
    public void shouldDeactivateEquipoSuccessfully() {
        when(equiposRepository.findById(1L)).thenReturn(Optional.of(equipoMock));
        when(equiposRepository.save(any(Equipos.class))).thenReturn(equipoMock);

        String result = equipoService.desactivarEquipo(1L);

        assertThat(result).isEqualTo("Equipo desactivado correctamente");
        assertThat(equipoMock.getEstadoequipo()).isEqualTo("INACTIVO");
        verify(equiposRepository, times(1)).save(equipoMock);
    }
}
