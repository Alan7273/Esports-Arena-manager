package com.Esports.Msvcs_juegos.Services;

import com.Esports.Msvcs_juegos.exceptions.ResourceNotFoundException;
import com.Esports.Msvcs_juegos.models.Juegos;
import com.Esports.Msvcs_juegos.models.dtos.ActualizarJuegoDTO;
import com.Esports.Msvcs_juegos.models.dtos.CrearJuegoDTO;
import com.Esports.Msvcs_juegos.models.dtos.JuegoResponseDTO;
import com.Esports.Msvcs_juegos.repositories.JuegoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class JuegosServiceImpl implements JuegosService {

    @Autowired
    private JuegoRepository juegoRepository;

    @Override
    public JuegoResponseDTO CrearJuego(CrearJuegoDTO dto) {
        Juegos juego = new Juegos();
        juego.setNombrejuegos(dto.getNombrejuegos());
        juego.setGenerojuego(dto.getGenerojuego());
        juego.setModalidadjuegos(dto.getModalidadjuegos());
        juego.setJugadores_por_equipo(dto.getJugadores_por_equipo());
        juego.setEstadojuego("ACTIVO");

        juegoRepository.save(juego);

        JuegoResponseDTO response = new JuegoResponseDTO();
        response.setJuegosId(juego.getJuegosId());
        response.setNombrejuegos(juego.getNombrejuegos());
        response.setGenerojuego(juego.getGenerojuego());
        response.setModalidadjuegos(juego.getModalidadjuegos());
        response.setJugadores_por_equipo(juego.getJugadores_por_equipo());
        response.setEstadojuego(juego.getEstadojuego());

        return response;
    }

    @Override
    public List<JuegoResponseDTO> ListarJuegos() {
        return juegoRepository.findAll().stream().map(juego -> {
            JuegoResponseDTO dto = new JuegoResponseDTO();
            dto.setJuegosId(juego.getJuegosId());
            dto.setNombrejuegos(juego.getNombrejuegos());
            dto.setGenerojuego(juego.getGenerojuego());
            dto.setModalidadjuegos(juego.getModalidadjuegos());
            dto.setJugadores_por_equipo(juego.getJugadores_por_equipo());
            dto.setEstadojuego(juego.getEstadojuego());
            return dto;
        }).toList();
    }

    @Override
    public JuegoResponseDTO BuscarJuego(Long juegosId) {
        Juegos juego = juegoRepository.findById(juegosId).orElseThrow(
                () -> new ResourceNotFoundException("Juego no encontrado"));

        JuegoResponseDTO dto = new JuegoResponseDTO();
        dto.setJuegosId(juego.getJuegosId());
        dto.setNombrejuegos(juego.getNombrejuegos());
        dto.setGenerojuego(juego.getGenerojuego());
        dto.setModalidadjuegos(juego.getModalidadjuegos());
        dto.setJugadores_por_equipo(juego.getJugadores_por_equipo());
        dto.setEstadojuego(juego.getEstadojuego());

        return dto;
    }

    @Override
    public JuegoResponseDTO actualizarJuego(Long juegosId, ActualizarJuegoDTO dto) {
        Juegos juego = juegoRepository.findById(juegosId).orElseThrow(
                () -> new ResourceNotFoundException("Juego no encontrado"));

        juego.setNombrejuegos(dto.getNombrejuegos());
        juego.setGenerojuego(dto.getGenerojuego());
        juego.setModalidadjuegos(dto.getModalidadjuegos());
        juego.setJugadores_por_equipo(dto.getJugadores_por_equipo());
        juego.setEstadojuego(dto.getEstadojuego());

        juegoRepository.save(juego);

        JuegoResponseDTO response = new JuegoResponseDTO();
        response.setJuegosId(juego.getJuegosId());
        response.setNombrejuegos(juego.getNombrejuegos());
        response.setGenerojuego(juego.getGenerojuego());
        response.setModalidadjuegos(juego.getModalidadjuegos());
        response.setJugadores_por_equipo(juego.getJugadores_por_equipo());
        response.setEstadojuego(juego.getEstadojuego());

        return response;
    }

    @Override
    public String desactivarJuego(Long juegosId) {
        Juegos juego = juegoRepository.findById(juegosId).orElseThrow(
                () -> new ResourceNotFoundException("Juego no encontrado"));

        juego.setEstadojuego("INACTIVO");
        juegoRepository.save(juego);

        return "Juego desactivado correctamente";
    }
}