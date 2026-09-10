# Treino Backend

API REST para **Treino**, una app de gestión de entrenadores personales: registro de profesores y alumnos, perfiles públicos y sistema de reviews.

Proyecto final de carrera (Analista Programador, Universidad ORT), reacondicionado para uso como API de prueba.

## Stack

- Java 21 · Spring Boot 3.3
- Spring Data JPA / Hibernate
- MySQL (producción) · H2 en memoria (desarrollo local)
- BCrypt para el hash de contraseñas
- Maven

## Arquitectura

Separación en capas clásica:

```
controller/   → endpoints REST, validación de entrada y códigos HTTP
service/      → lógica de negocio (interfaz + implementación)
dao/          → repositorios Spring Data
entity/       → entidades JPA (Persona → Profesor / Usuario / Admin)
dto/          → objetos de entrada (LoginRequest, SignUpRequest)
model/        → proyecciones para respuestas (MProfesor, MUsuario)
config/       → CORS y beans de configuración
```

`Persona` es una `@MappedSuperclass` de la que heredan `Profesor`, `Usuario` y `Admin`, con polimorfismo en el JSON vía `@JsonTypeInfo`.

## Correr en local

No necesitás instalar MySQL: el perfil `local` levanta una base H2 en memoria.

```bash
mvn spring-boot:run "-Dspring-boot.run.profiles=local"
```

La API queda en `http://localhost:8080`. Consola de la base en `http://localhost:8080/h2` (JDBC URL `jdbc:h2:mem:treino`, usuario `sa`, sin contraseña).

## Correr contra MySQL

Las credenciales se leen de variables de entorno — no hay ninguna en el código:

```bash
export DB_URL="jdbc:mysql://host:3306/treino"
export DB_USER="usuario"
export DB_PASS="contraseña"
mvn spring-boot:run
```

Variables opcionales: `PORT` (default 8080) y `CORS_ORIGINS` (lista separada por comas).

## Endpoints

Todos cuelgan de `/api`.

### Auth
| Método | Ruta | Descripción |
|---|---|---|
| POST | `/login` | Login unificado (profesor, usuario o admin) |
| POST | `/sign_up` | Alta de profesor |
| POST | `/crear_usuario` | Alta de alumno |
| POST | `/crear_admin` | Alta de administrador |

### Profesores
| Método | Ruta | Descripción |
|---|---|---|
| GET | `/profesores` | Listar todos |
| GET | `/profesores_publicados` | Solo perfiles públicos |
| GET | `/find_profesor/{id}` · `/find_profesor?email=` | Buscar |
| PUT | `/update/{id}` | Actualizar datos |
| PUT | `/update_foto/{id}` | Subir foto (multipart) |
| DELETE | `/delete/{id}` · `/delete` | Eliminar |

### Usuarios
| Método | Ruta | Descripción |
|---|---|---|
| GET | `/usuarios` | Listar todos |
| GET | `/usuarios_profesor/{profesor_id}` | Alumnos de un profesor |
| GET | `/find_usuario/{id}` · `/find_usuario?email=` | Buscar |
| PUT | `/update_usuario/{id}` · `/update_foto_usuario/{id}` | Actualizar |
| DELETE | `/delete_usuario/{id}` · `/delete_usuarios` | Eliminar |

### Reviews
| Método | Ruta | Descripción |
|---|---|---|
| GET | `/reviews` | Listar todas |
| GET | `/reviews_profesor/{profesor_id}` · `/reviews_usuario/{usuario_id}` | Por autor o destinatario |
| GET | `/find_review/{id}` · `/find_review?...` | Buscar |
| POST | `/crear_review` | Crear |
| PUT | `/update_review/{id}` | Actualizar |
| DELETE | `/delete_review/{id}` | Eliminar |

## Estado y limitaciones conocidas

Este es un proyecto académico reacondicionado. Lo que falta para considerarlo listo para producción:

- **Sin autenticación por token.** El login valida credenciales pero no emite JWT ni sesión: los endpoints quedan abiertos.
- **Sin autorización por rol.** El enum `Rol` existe pero no se usa para restringir accesos.
- **Fotos como BLOB en la base.** Deberían ir a un bucket u object storage.
- **Cobertura de tests mínima.**

## Ejemplos

```bash
# Registrar un profesor
curl -X POST http://localhost:8080/api/sign_up \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Ana Torres","email":"ana@treino.com","password":"secreto123"}'

# Login
curl -X POST http://localhost:8080/api/login \
  -H "Content-Type: application/json" \
  -d '{"email":"ana@treino.com","password":"secreto123"}'

# Listar profesores
curl http://localhost:8080/api/profesores
```

## Licencia

MIT
