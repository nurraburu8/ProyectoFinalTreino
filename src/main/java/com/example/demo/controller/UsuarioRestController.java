package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.SignUpRequest;
import com.example.demo.entity.Profesor;
import com.example.demo.entity.Rol;
import com.example.demo.entity.Usuario;
import com.example.demo.mapper.Mapper;
import com.example.demo.model.MProfesor;
import com.example.demo.service.IUsuarioService;

@Tag(name = "Alumnos", description = "Alta, consulta, edicion y baja de alumnos")
@RestController
@RequestMapping("/api")
public class UsuarioRestController {
	
	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Operation(summary = "Listar todos los alumnos")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Lista de alumnos"),
	    @ApiResponse(responseCode = "404", description = "No hay alumnos registrados")
	})
	@GetMapping("/usuarios")
	public ResponseEntity<?> listaUsuarios(){
		List<Usuario> listaUsuarios = usuarioService.findAll();
		if(listaUsuarios!=null) {
		if(listaUsuarios.size()!=0) {
			return new ResponseEntity<>(listaUsuarios, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} 
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@Operation(summary = "Registrar un alumno",
	           description = "Crea una cuenta con rol USER. La contrasena se almacena cifrada con BCrypt.")
	@ApiResponses({
	    @ApiResponse(responseCode = "201", description = "Alumno creado"),
	    @ApiResponse(responseCode = "400", description = "Datos invalidos (nombre vacio, email mal formado o contrasena menor a 8 caracteres)"),
	    @ApiResponse(responseCode = "409", description = "Ya existe una cuenta con ese email")
	})
	@PostMapping("/crear_usuario")
	public ResponseEntity<?> agregarUsuario(@Valid @RequestBody SignUpRequest signUpRequest){
		String email = signUpRequest.getEmail();
		
		if(usuarioService.findUsuarioByEmail(email) != null) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}else {
	        Usuario nuevoUsuario = new Usuario();
	        nuevoUsuario.setNombre(signUpRequest.getNombre());
	        nuevoUsuario.setEmail(signUpRequest.getEmail());
	        nuevoUsuario.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
	        nuevoUsuario.setRol(Rol.USER);
	        
	        usuarioService.saveUsuario(nuevoUsuario);
			return new ResponseEntity<Usuario>(nuevoUsuario, HttpStatus.CREATED);
		}

	}
	
	/*
	 * @PostMapping("login_usuario") public ResponseEntity<?>
	 * loginUsuario(@RequestBody Usuario usuario){ Usuario usuarioDb =
	 * usuarioService.checkUsuarioLogin(usuario); if(usuarioDb!=null) { return new
	 * ResponseEntity<>(usuarioDb, HttpStatus.OK); }else { return new
	 * ResponseEntity<Void>(HttpStatus.NOT_FOUND); } }
	 */
	
	@Operation(summary = "Listar los alumnos de un entrenador")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Lista de alumnos"),
	    @ApiResponse(responseCode = "404", description = "El entrenador no tiene alumnos asignados")
	})
	@GetMapping("/usuarios_profesor/{profesor_id}")
	public ResponseEntity<?> verUsuariosProfesor(@PathVariable (value = "profesor_id") Long idProfesor){
		List<Usuario> listaUsuarios = usuarioService.getUsuarioProfesor(idProfesor);
		if(listaUsuarios!=null) {
		if(listaUsuarios.size()!=0) {
			return new ResponseEntity<>(listaUsuarios, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} 
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}	}
	
	@Operation(summary = "Subir la foto de perfil de un alumno",
	           description = "Recibe el archivo como multipart/form-data en el campo 'foto'. Maximo 10 MB.")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Foto actualizada"),
	    @ApiResponse(responseCode = "404", description = "No existe un alumno con ese id"),
	    @ApiResponse(responseCode = "500", description = "No se pudo leer el archivo recibido")
	})
	@PutMapping("/update_foto_usuario/{id}")
	public ResponseEntity<?> updateUsuarioFoto(
	        @PathVariable Long id,
	        @RequestParam("foto") MultipartFile foto) {
	    Usuario usuarioDb = usuarioService.findById(id);
	    if (usuarioDb != null) {
	        try {
	            // Actualiza solo la foto
	            usuarioDb.setFoto(foto.getBytes());
	            usuarioService.updateUsuario(usuarioDb);
	            return new ResponseEntity<>(usuarioDb, HttpStatus.OK);
	        } catch (IOException e) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}

	
	@Operation(summary = "Actualizar el perfil de un alumno",
	           description = "Los campos que lleguen nulos conservan su valor anterior.")
	@ApiResponses({
	    @ApiResponse(responseCode = "201", description = "Perfil actualizado"),
	    @ApiResponse(responseCode = "404", description = "No existe un alumno con ese id")
	})
	@PutMapping("/update_usuario/{id}")
	public ResponseEntity<?> updateUsuario(@PathVariable (value = "id") Long id, @RequestBody Usuario usuario){
		Usuario usuarioDb = usuarioService.findById(id);
		if(usuarioDb != null) {
			usuario.setId(id);
			usuarioDb.updateUsuario(usuario);
			usuarioService.updateUsuario(usuarioDb);
			return new ResponseEntity<>(usuarioDb, HttpStatus.CREATED);
		}else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		}
	
	@Operation(summary = "Eliminar un alumno")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Alumno eliminado"),
	    @ApiResponse(responseCode = "404", description = "No existe un alumno con ese id")
	})
	@DeleteMapping("/delete_usuario/{id}")
	public ResponseEntity<Void> deleteUsuario(@PathVariable(value="id")Long id){
		Usuario usuarioDb = null;
		usuarioDb = usuarioService.findById(id);
		if(usuarioDb != null) {
			usuarioService.deleteUsuario(id);
		    return new ResponseEntity<Void>(HttpStatus.OK);
		}else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}
	
	@Operation(summary = "Eliminar todos los alumnos",
	           description = "Operacion destructiva, pensada para reiniciar datos de prueba.")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Alumnos eliminados")
	})
	@DeleteMapping("/delete_usuarios")
	public ResponseEntity<Void> deleteAllUsuarios(){
		usuarioService.deleteAllUsuarios();
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@Operation(summary = "Buscar un alumno por id")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Alumno encontrado"),
	    @ApiResponse(responseCode = "404", description = "No existe un alumno con ese id")
	})
	@GetMapping("/find_usuario/{id}")
	public ResponseEntity<?> findUsuario(@PathVariable(value="id")Long id){
		Usuario usuarioDb = usuarioService.findById(id);
		if(usuarioDb==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<Usuario>(usuarioDb, HttpStatus.OK);
		}
	}
	
	@Operation(summary = "Buscar un alumno por email",
	           description = "El email se pasa como parametro de consulta: /find_usuario?email=bruno@treino.com")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Alumno encontrado"),
	    @ApiResponse(responseCode = "404", description = "No existe un alumno con ese email")
	})
	@GetMapping("/find_usuario")
	public ResponseEntity<?> findUsuario(@RequestParam String email){
		Usuario usuarioDb = usuarioService.findUsuarioByEmail(email);
		if(usuarioDb!=null) {
			return new ResponseEntity<>(usuarioDb, HttpStatus.OK);
		}else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	

}
