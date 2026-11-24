package com.Esports.Msvcs_usuarios.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;
import com.Esports.Msvcs_usuarios.models.Usuario;
import com.Esports.Msvcs_usuarios.repositories.UsuariosRepository;

/**
 * Siembra los jugadores de datos.js (j1..j81 => IDs 1..81) y 3 jugadores para las cuentas de login.
 * Solo siembra si la tabla está vacía, así que es seguro reiniciar el servicio.
 * Los IDs se asignan en orden (1, 2, 3...) y los demás microservicios
 * referencian a estos mismos números (j1 = 1, e1 = 1, t1 = 1, p1 = 1...).
 * Si ya tienes datos viejos en Postgres, borra la base para que los IDs coincidan.
 */
@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Override
    public void run(String... args) {
        if (usuariosRepository.count() > 0) {
            return;
        }
        usuariosRepository.saveAll(List.of(
            usuario("Diego Rojas", "shadow99", "diego@mail.com", "JUGADOR"),
            usuario("Camila Soto", "nova_x", "camila@mail.com", "JUGADOR"),
            usuario("Bastián Muñoz", "kriz", "bastian@mail.com", "JUGADOR"),
            usuario("Valentina Rivas", "vexa", "valentina@mail.com", "JUGADOR"),
            usuario("Matías Fuentes", "ryuzen", "matias@mail.com", "JUGADOR"),
            usuario("Javiera Pinto", "lunaris", "javiera@mail.com", "JUGADOR"),
            usuario("Tomás Araya", "wolfgang", "tomas@mail.com", "JUGADOR"),
            usuario("Francisca Lagos", "zeta", "francisca@mail.com", "JUGADOR"),
            usuario("Nicolás Vega", "kairo", "nicolas@mail.com", "JUGADOR"),
            usuario("Constanza Mora", "pixel", "constanza@mail.com", "JUGADOR"),
            usuario("Felipe Núñez", "drako", "felipe@mail.com", "JUGADOR"),
            usuario("Ignacio Reyes", "viper01", "ignacio@mail.com", "JUGADOR"),
            usuario("Sofía Cárdenas", "sofi_x", "sofia@mail.com", "JUGADOR"),
            usuario("Benjamín Toro", "benja", "benjamin@mail.com", "JUGADOR"),
            usuario("Antonia Fuentes", "anto", "antonia@mail.com", "JUGADOR"),
            usuario("Joaquín Parra", "joaco", "joaquin@mail.com", "JUGADOR"),
            usuario("Martina Salas", "raven", "martina@mail.com", "JUGADOR"),
            usuario("Sebastián Díaz", "seba", "sebastian@mail.com", "JUGADOR"),
            usuario("Isidora Vera", "isi", "isidora@mail.com", "JUGADOR"),
            usuario("Cristóbal León", "cris", "cristobal@mail.com", "JUGADOR"),
            usuario("Fernanda Ibarra", "fer", "fernanda@mail.com", "JUGADOR"),
            usuario("Agustín Castro", "titan", "agustin@mail.com", "JUGADOR"),
            usuario("Catalina Bravo", "cata", "catalina@mail.com", "JUGADOR"),
            usuario("Vicente Rojas", "vicho", "vicente@mail.com", "JUGADOR"),
            usuario("Emilia Tapia", "emi", "emilia@mail.com", "JUGADOR"),
            usuario("Rodrigo Silva", "rod", "rodrigo@mail.com", "JUGADOR"),
            usuario("Maite Campos", "pirata", "maite@mail.com", "JUGADOR"),
            usuario("Lucas Herrera", "lucas", "lucas@mail.com", "JUGADOR"),
            usuario("Paula Navarro", "paula", "paula@mail.com", "JUGADOR"),
            usuario("Gabriel Soto", "gabo", "gabriel@mail.com", "JUGADOR"),
            usuario("Daniela Pizarro", "dani", "daniela@mail.com", "JUGADOR"),
            usuario("Esteban Molina", "dragon", "esteban@mail.com", "JUGADOR"),
            usuario("Carolina Ortiz", "caro", "carolina@mail.com", "JUGADOR"),
            usuario("Andrés Cortés", "andy", "andres@mail.com", "JUGADOR"),
            usuario("Trinidad Ramos", "trini", "trinidad@mail.com", "JUGADOR"),
            usuario("Pablo Guzmán", "pablo", "pablo@mail.com", "JUGADOR"),
            usuario("Renata Vidal", "nexus", "renata@mail.com", "JUGADOR"),
            usuario("Marcelo Espinoza", "marce", "marcelo@mail.com", "JUGADOR"),
            usuario("Florencia Reyes", "flo", "florencia@mail.com", "JUGADOR"),
            usuario("Hugo Carrasco", "hugo", "hugo@mail.com", "JUGADOR"),
            usuario("Belén Sandoval", "bele", "belen@mail.com", "JUGADOR"),
            usuario("Matías Lara", "baron", "matias.lara@mail.com", "JUGADOR"),
            usuario("Javier Peña", "javi", "javier@mail.com", "JUGADOR"),
            usuario("Amanda Silva", "ami", "amanda@mail.com", "JUGADOR"),
            usuario("Diego Contreras", "dcon", "diego.c@mail.com", "JUGADOR"),
            usuario("Ámbar Figueroa", "ambar", "ambar@mail.com", "JUGADOR"),
            usuario("Gonzalo Ibáñez", "voidx", "gonzalo@mail.com", "JUGADOR"),
            usuario("Micaela Fernández", "mica", "micaela@mail.com", "JUGADOR"),
            usuario("Óscar Tapia", "oscar", "oscar@mail.com", "JUGADOR"),
            usuario("Laura Mena", "lau", "laura@mail.com", "JUGADOR"),
            usuario("Ignacio Bustos", "nacho", "ignacio.b@mail.com", "JUGADOR"),
            usuario("Raúl Cifuentes", "storm", "raul@mail.com", "JUGADOR"),
            usuario("Valeria Henríquez", "vale_r", "valeria@mail.com", "JUGADOR"),
            usuario("Kevin Aravena", "kevo", "kevin@mail.com", "JUGADOR"),
            usuario("Pamela Godoy", "pame", "pamela@mail.com", "JUGADOR"),
            usuario("Mauricio Leiva", "mauro", "mauricio@mail.com", "JUGADOR"),
            usuario("Álvaro Jara", "arcano", "alvaro@mail.com", "JUGADOR"),
            usuario("Nicole Salinas", "nico_s", "nicole@mail.com", "JUGADOR"),
            usuario("Eduardo Rubio", "lalo", "eduardo@mail.com", "JUGADOR"),
            usuario("Jazmín Olivares", "jaz", "jazmin@mail.com", "JUGADOR"),
            usuario("Cristian Moya", "tato", "cristian@mail.com", "JUGADOR"),
            usuario("Patricio Vargas", "owl", "patricio@mail.com", "JUGADOR"),
            usuario("Macarena Soto", "male", "macarena@mail.com", "JUGADOR"),
            usuario("Rafael Cornejo", "rafa", "rafael@mail.com", "JUGADOR"),
            usuario("Daniel Meza", "dani_m", "daniel@mail.com", "JUGADOR"),
            usuario("Karina Ávila", "kari", "karina@mail.com", "JUGADOR"),
            usuario("Hernán Quiroz", "phoenix", "hernan@mail.com", "JUGADOR"),
            usuario("Yenifer Bahamondes", "yeni", "yenifer@mail.com", "JUGADOR"),
            usuario("Alejandro Pardo", "ale", "alejandro@mail.com", "JUGADOR"),
            usuario("Giuliana Franco", "giuli", "giuliana@mail.com", "JUGADOR"),
            usuario("Matías Bórquez", "mati_b", "matias.b@mail.com", "JUGADOR"),
            usuario("Sergio Paredes", "frost", "sergio@mail.com", "JUGADOR"),
            usuario("Natalia Cabrera", "nati", "natalia@mail.com", "JUGADOR"),
            usuario("Iván Cáceres", "ivan", "ivan@mail.com", "JUGADOR"),
            usuario("Romina Escobar", "romi", "romina@mail.com", "JUGADOR"),
            usuario("Christopher Muñoz", "chris", "christopher@mail.com", "JUGADOR"),
            usuario("Leonardo Acuña", "rey", "leonardo@mail.com", "JUGADOR"),
            usuario("Francisco Ortega", "fran_o", "francisco@mail.com", "JUGADOR"),
            usuario("Ana Maldonado", "anita", "ana@mail.com", "JUGADOR"),
            usuario("José Salazar", "pepe", "jose@mail.com", "JUGADOR"),
            usuario("Verónica Lagos", "vero", "veronica@mail.com", "JUGADOR"),
            // Jugadores asociados a las cuentas de login (admin / jugador1 / arbitro1) para que "Mi perfil" funcione
            usuario("Administrador", "admin", "admin@mail.com", "ADMIN"),
            usuario("Jugador Demo", "jugador1", "jugador1@mail.com", "JUGADOR"),
            usuario("Árbitro Demo", "arbitro1", "arbitro1@mail.com", "ARBITRO")
        ));
        log.info("DataLoader: {} usuarios cargados", usuariosRepository.count());
    }

    private Usuario usuario(String nombre, String nickname, String correo, String rol) {
        Usuario u = new Usuario();
        u.setNombreusuario(nombre);
        u.setNickname(nickname);
        u.setCorreo(correo);
        u.setRol(rol);
        u.setEstadousuario("ACTIVO");
        u.setFechaRegistro(LocalDate.now());
        return u;
    }
}
