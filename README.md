# eSports Arena Manager

Plataforma para gestionar torneos de eSports: juegos, torneos, equipos, inscripciones, partidas, resultados, rankings, premios, sanciones y notificaciones.

- **Backend:** 12 microservicios Spring Boot + Eureka + API Gateway + PostgreSQL (Docker).
- **Frontend:** React + Vite + React Bootstrap, conectado al backend **solo a través del gateway**.
- **Seguridad:** autenticación con JWT (HS256) y autorización por rol.

> **Integrantes:** Alan Cubillos · Jorge González · Martín Espinoza
> **Asignaturas:** Desarrollo FullStack I (DSY1103) y Desarrollo Fullstack II (003D) · DuocUC

---

## 1. Arquitectura

```
┌────────────────────┐   HTTP + JWT    ┌─────────────────────────┐
│  Frontend (React)  │ ──────────────► │  Msvcs-gateway  :8091   │
│  localhost:5173    │ ◄────────────── │  valida JWT + CORS      │
└────────────────────┘                 └───────────┬─────────────┘
                                                   │ lb://<servicio> (Eureka :8761)
        ┌──────────┬──────────┬──────────┬──────────┼──────────┬──────────┐
        ▼          ▼          ▼          ▼          ▼          ▼          ▼
     msvcs-user  usuarios   equipos   torneos   inscripciones  ...  (12 servicios)
     (login/JWT)                          │
                                          └── Feign entre servicios (reenvía el token)
                                          
                       PostgreSQL 17 · una base de datos por servicio
```

| Componente | Puerto | Función |
|---|---|---|
| Frontend (Vite) | 5173 | Interfaz web |
| `Msvcs-gateway` | **8091** | Único punto de entrada del frontend |
| `Msvcs-eureka` | 8761 | Registro y descubrimiento de servicios |
| `Msvcs-Users` | 8092 | Registro, login y emisión del JWT |
| 11 microservicios de negocio | ver `backend/.../README.md` | usuarios, equipos, juegos, torneos, inscripciones, partidas, resultados, rankings, premios, sanciones, notificaciones |

---

## 2. Qué está implementado

### Backend
- 11 microservicios de negocio con CRUD, validaciones, DTOs, manejo de excepciones, logs SLF4J, documentación Swagger y pruebas unitarias de servicio.
- Versiones **v1** (REST) y **v2** (HATEOAS) de los controladores.
- Comunicación entre servicios con **OpenFeign + circuit breaker + fallbacks** (ej.: inscripciones valida torneo, equipo y sanciones antes de inscribir).
- **`Msvcs-Users`**: registro y login con contraseñas cifradas con BCrypt; genera el JWT.
- **`Msvcs-gateway`**: enrutamiento, validación del token y CORS.
- **`Msvcs-eureka`**: descubrimiento de servicios.
- **Docker Compose**: PostgreSQL 17 con una base por servicio (`docker/postgres/init.sql`), Eureka, gateway y todos los microservicios.
- **DataLoaders** que siembran datos de demo (usuarios, juegos, equipos, torneos, inscripciones, partidas, etc.).

### Frontend
- SPA con rutas públicas, protegidas y por rol.
- Todas las pantallas leen y escriben datos reales del backend (ya no hay datos simulados en memoria).
- Pruebas unitarias con Jasmine + Karma sobre las utilidades de validación y formato.

---

## 3. Conexión Frontend ↔ Backend (lo nuevo)

### 3.1 Configuración

| Dónde | Variable / ajuste | Valor |
|---|---|---|
| `frontend/.env` | `VITE_API_URL` | `http://localhost:8091` (el gateway) |
| `backend/.env` | `POSTGRES_PASSWORD`, `JWT_SECRET` | usados por Docker Compose |
| Gateway (CORS) | origen permitido | `http://localhost:5173` |
| Gateway (CORS) | métodos / cabeceras | `GET, POST, PUT, PATCH, DELETE, OPTIONS` / `Authorization, Content-Type` |

> `JWT_SECRET` debe ser **idéntica** en `Msvcs-Users`, el gateway y los 11 microservicios (en Docker llega por variable de entorno).

### 3.2 Flujo de autenticación

1. El usuario envía usuario y clave desde `Login.jsx` → `POST /api/v1/auth/login`.
2. El gateway enruta `/api/v1/auth/**` a `Msvcs-Users` (ruta pública, sin token).
3. `Msvcs-Users` valida con BCrypt y responde `{ token, tokenType, username, roles }`.
   El JWT (HS256, vigencia **60 min**) lleva `sub` = username y `roles` = lista `ROLE_*`.
4. El frontend guarda el token en `localStorage` (clave `token`).
5. Cada petición posterior envía `Authorization: Bearer <token>`.
6. El **gateway** valida firma y vencimiento; si no es válido responde 401 y no enruta.
7. Cada **microservicio** vuelve a validar el token y aplica la autorización por rol (`@PreAuthorize`).
8. Cuando un servicio llama a otro por Feign, **reenvía la cabecera `Authorization`** (`FeignConfig`).

### 3.3 Capa de conexión en el frontend (`src/`)

| Archivo | Responsabilidad |
|---|---|
| `services/api.js` | Cliente `solicitar(ruta, opciones)` sobre `fetch`: agrega la URL base y el Bearer token, y traduce errores (ver 3.4) |
| `services/auth.js` | `iniciarSesion`, `registrarUsuario` |
| `services/torneos.js` | torneos, partidas, ranking, premios; `conCupos()` calcula cupos ocupados y nombre del juego cruzando torneos, juegos e inscripciones |
| `services/equipos.js` | listar y crear equipos, agregar miembros |
| `services/inscripciones.js` | listar y crear inscripciones |
| `services/juegos.js`, `usuarios.js`, `sanciones.js` | consultas y actualización de perfil |
| `services/mapeadores.js` | Convierte los DTOs del backend al modelo del frontend (ej.: `torneoId → id`, `nombretorneo → nombre`, `CERRADO/CANCELADO → finalizado`) |
| `hooks/useApi.js` | Hook genérico: ejecuta una función async y entrega `{ datos, cargando, error, recargar }` |
| `hooks/useAuth.js` + `context/AuthProvider.jsx` | Sesión global: `usuario`, `token`, `login`, `logout`, `tieneRol(...)` |
| `components/RutaProtegida.jsx` | Guarda rutas por sesión y por rol |
| `components/EstadoCarga.jsx` | Spinner / mensaje de error de las llamadas |

### 3.4 Manejo de sesión y errores

| Situación | Comportamiento del frontend |
|---|---|
| Recarga de página (F5) | La sesión se restaura desde `localStorage` si el token no ha vencido |
| Token vencido | Se cierra la sesión automáticamente (temporizador según `exp`) |
| **401** con token | "Sesión expirada": se hace logout |
| **401** sin token | Mensaje "Inicia sesión para ver esta información." |
| **403** | "No tienes permisos para realizar esta acción." |
| Otro error | Muestra el `mensaje` / `message` que devuelve el backend (cupo lleno, torneo cerrado, sanción activa, etc.) |
| `204` | Devuelve `null` |

### 3.5 Pantallas y los servicios que consumen

| Página | Ruta | Acceso | Servicios consultados |
|---|---|---|---|
| Inicio | `/` | Pública (los datos requieren sesión) | torneos, juegos, inscripciones, premios, rankings |
| Login | `/login` | Pública | auth |
| Registro | `/registro` | Pública | auth (el rol por defecto es `ROLE_JUGADOR`) |
| Torneos | `/torneos` | Pública (datos con sesión) | torneos, juegos, inscripciones |
| Detalle de torneo | `/torneo/:id` | Pública (datos con sesión) | torneos, partidas, rankings, premios, juegos, inscripciones (en paralelo) |
| Perfil | `/perfil` | Logueado | usuarios (consulta y edición), equipos, sanciones |
| Inscripción | `/inscripcion` | `JUGADOR`, `ADMIN` | torneos, equipos, inscripciones (crea) |
| Mi equipo | `/equipo` | `JUGADOR`, `ADMIN` | juegos, usuarios, equipos (crea equipo y agrega miembros) |

### 3.6 Roles y permisos

Roles sembrados: `ROLE_ADMIN`, `ROLE_JUGADOR`, `ROLE_ARBITRO`.

| Recurso | Lectura | Escritura |
|---|---|---|
| Juegos, torneos, partidas, premios, sanciones | Cualquier usuario autenticado | `ADMIN` |
| Usuarios | Autenticado | Crear/eliminar: `ADMIN` · Actualizar: `JUGADOR`, `ADMIN` |
| Equipos | `JUGADOR`, `ADMIN` | `JUGADOR`, `ADMIN` |
| Inscripciones | Autenticado | Crear/cancelar: `JUGADOR`, `ADMIN` · Cambiar estado: `ADMIN` |
| Resultados | Autenticado | `ARBITRO`, `ADMIN` |
| Rankings | Autenticado | Recalcular: `ARBITRO`, `ADMIN` · Reiniciar: `ADMIN` |
| Notificaciones | Autenticado | Crear/eliminar: `ADMIN` · Marcar leída: autenticado |

**Cuentas de demo** (creadas por el `DataLoader` de `Msvcs-Users`):

| Usuario | Clave | Rol |
|---|---|---|
| `admin` | `admin123` | ADMIN |
| `jugador1` | `jugador123` | JUGADOR |
| `arbitro1` | `arbitro123` | ARBITRO |

### 3.7 Rutas del gateway

Todas las rutas `/api/v1/**` y `/api/v2/**` de cada recurso se envían al servicio correspondiente vía Eureka (`lb://`):

`/auth` → msvcs-user · `/equipos` · `/inscripciones` · `/juegos` · `/notificaciones` · `/partidas` · `/premios` · `/rankings` · `/resultados` · `/sanciones` · `/torneos` · `/usuarios`

**Swagger agregado:** `http://localhost:8091/docs/swagger-ui.html` (desplegable con los 11 servicios de negocio).

---

## 4. Cómo ejecutar

### Backend (Docker)

```bash
cd backend/Esports-Arena-manager-master
# Crear .env con POSTGRES_PASSWORD y JWT_SECRET
docker compose up --build
```

Verificación: Eureka en `http://localhost:8761` (todos los servicios registrados) y Swagger en `http://localhost:8091/docs/swagger-ui.html`.

### Frontend

```bash
cd frontend/Esports-Arena-ManagerEv2-React-main
npm install
npm run dev        # http://localhost:5173
npm test           # Jasmine + Karma (ChromeHeadless)
```

Requisitos: Docker, Node.js 18+ y Google Chrome (para Karma).

---

## 5. Notas y pendientes conocidos

- **Perfil ↔ cuenta de login:** la pantalla Perfil busca el jugador cuyo `nickname` coincide con el `username` de la sesión. Las cuentas de demo ya tienen su jugador asociado; una cuenta creada desde `/registro` no crea automáticamente un jugador en `usuarios`, por lo que Perfil mostrará el aviso de que falta asociarlo.
- **Fecha de cierre de inscripción:** el backend no la maneja; el frontend usa `fechaInicio` del torneo como referencia.
- **Cupos:** el backend no entrega cupos ocupados; se calculan en el frontend contando inscripciones no canceladas/rechazadas.
- **Pruebas del frontend:** `validaciones.spec.js` importa `src/data/datos.js`, archivo que ya no existe tras conectar con el backend; hay que ajustar esas pruebas con datos de ejemplo propios para que `npm test` vuelva a pasar.
- **Seguridad:** el token se guarda en `localStorage`; el secreto JWT tiene un valor por defecto en los `application.properties` solo para desarrollo, en despliegues debe venir por variable de entorno.
- El README interno del frontend (`Esports-Arena-ManagerEv2-React-main/README.md`) aún describe la versión anterior con datos simulados y Context API; este documento reemplaza esa parte.

---

## 6. Documentación adicional

- Endpoints por microservicio, diagrama de clases y flujo integrador: `backend/Esports-Arena-manager-master/README.md`.
- Estructura de componentes, pruebas y reglas de negocio del frontend: `frontend/Esports-Arena-ManagerEv2-React-main/README.md`.
