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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.SignUpRequest;
import com.example.demo.entity.Profesor;
import com.example.demo.entity.Rol;
import com.example.demo.entity.Usuario;
import com.example.demo.mapper.Mapper;
import com.example.demo.model.MProfesor;
import com.example.demo.service.IProfesorService;

@Tag(name = "Profesores", description = "Alta, consulta, edicion y baja de entrenadores")
@RestController
@RequestMapping("/api")
public class ProfesorRestController {
	
	@Autowired
	private IProfesorService profesorService;

	@Autowired
	private PasswordEncoder passwordEncoder; 
	
	@Operation(summary = "Listar todos los entrenadores",
	           description = "Devuelve todos los entrenadores registrados, publicados o no.")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Lista de entrenadores (puede venir vacia)")
	})
	@GetMapping("/profesores")
	@ResponseStatus(HttpStatus.OK)
	public List<Profesor> getProfesores(){
		return profesorService.findAll();
	}

	@Operation(summary = "Registrar un entrenador",
	           description = "Crea una cuenta con rol TRAINER. La contrasena se almacena cifrada con BCrypt.")
	@ApiResponses({
	    @ApiResponse(responseCode = "201", description = "Entrenador creado"),
	    @ApiResponse(responseCode = "400", description = "Datos invalidos (nombre vacio, email mal formado o contrasena menor a 8 caracteres)"),
	    @ApiResponse(responseCode = "409", description = "Ya existe una cuenta con ese email")
	})
	@PostMapping("/sign_up")
	public ResponseEntity<?> addProfesor(@Valid @RequestBody SignUpRequest signUpRequest){
		String email = signUpRequest.getEmail();
		
		if(profesorService.findProfesorByEmail(email) != null) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}else {
	        Profesor nuevoProfesor = new Profesor();
	        nuevoProfesor.setNombre(signUpRequest.getNombre());
	        nuevoProfesor.setEmail(signUpRequest.getEmail());
	        nuevoProfesor.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
	        nuevoProfesor.setRol(Rol.TRAINER);
	        
	        profesorService.save(nuevoProfesor);
			return new ResponseEntity<Profesor>(nuevoProfesor, HttpStatus.CREATED);
		}
	}
	@Operation(summary = "Actualizar el perfil de un entrenador",
	           description = "Modifica los campos del perfil. Los campos que lleguen nulos conservan su valor anterior.")
	@ApiResponses({
	    @ApiResponse(responseCode = "201", description = "Perfil actualizado"),
	    @ApiResponse(responseCode = "404", description = "No existe un entrenador con ese id")
	})
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateProfesor(@PathVariable (value = "id") Long id,
            @RequestBody Profesor profesor){
		Profesor profesorDb = profesorService.findById(id);
		if(profesorDb != null) {
			profesor.setId(id);
			profesorDb.updateProfesor(profesor);
			profesorService.updateProfesor(profesorDb);
			return new ResponseEntity<>(profesorDb, HttpStatus.CREATED);
		}else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		}
	
    @Operation(summary = "Subir la foto de perfil de un entrenador",
               description = "Recibe el archivo como multipart/form-data en el campo 'foto'. Maximo 10 MB.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Foto actualizada"),
        @ApiResponse(responseCode = "404", description = "No existe un entrenador con ese id"),
        @ApiResponse(responseCode = "500", description = "No se pudo leer el archivo recibido")
    })
    @PutMapping("/update_foto/{id}")
    public ResponseEntity<?> updateProfesorFoto(
            @PathVariable Long id,
            @RequestParam("foto") MultipartFile foto) {

        Profesor profesorDb = profesorService.findById(id);
        if (profesorDb != null) {
            try {
                // Actualiza solo la foto
                profesorDb.setFoto(foto.getBytes());
                profesorService.updateProfesor(profesorDb);
                return new ResponseEntity<>(profesorDb, HttpStatus.OK);
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
	@Operation(summary = "Eliminar un entrenador",
	           description = "Borra la cuenta del entrenador indicado.")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Entrenador eliminado"),
	    @ApiResponse(responseCode = "404", description = "No existe un entrenador con ese id")
	})
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteProfesor(@PathVariable(value="id")Long id){
		Profesor profesorDb = null;
		profesorDb = profesorService.findById(id);
		if(profesorDb != null) {
		profesorService.deleteProfesor(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
		}else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}
	
	@Operation(summary = "Eliminar todos los entrenadores",
	           description = "Borra todos los entrenadores. Operacion destructiva, pensada para reiniciar datos de prueba.")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Entrenadores eliminados")
	})
	@DeleteMapping("/delete")
	public ResponseEntity<Void> deleteAllProfesores(){
		profesorService.deleteAllProfesores();
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@Operation(summary = "Buscar un entrenador por id")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Entrenador encontrado"),
	    @ApiResponse(responseCode = "404", description = "No existe un entrenador con ese id")
	})
	@GetMapping("/find_profesor/{id}")
	public ResponseEntity<?> findProfesor(@PathVariable(value="id")Long id){
		Profesor profesorDb = profesorService.findById(id);
		if(profesorDb==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<Profesor>(profesorDb, HttpStatus.OK);
		}
	}
	
	@Operation(summary = "Listar entrenadores publicados",
	           description = "Solo los entrenadores con el perfil visible publicamente. Es el listado que consume el frontend.")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Lista de entrenadores publicados"),
	    @ApiResponse(responseCode = "404", description = "No hay entrenadores publicados")
	})
	@GetMapping("/profesores_publicados")
	public ResponseEntity<List<Profesor>> profesoresPublicados(){
	        List<Profesor> profesores = profesorService.findProfesoresPublicados();
	        if (profesores.isEmpty()) {
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        }
	        return new ResponseEntity<>(profesores, HttpStatus.OK);
	    
	}
	
	
	@Operation(summary = "Buscar un entrenador por email",
	           description = "El email se pasa como parametro de consulta: /find_profesor?email=ana@treino.com")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Entrenador encontrado"),
	    @ApiResponse(responseCode = "404", description = "No existe un entrenador con ese email")
	})
	@GetMapping("/find_profesor")
	public ResponseEntity<?> findProfesor(@RequestParam String email){
		Profesor profesorDb = profesorService.findProfesorByEmail(email);
		if(profesorDb!=null) {
			return new ResponseEntity<>(profesorDb, HttpStatus.OK);
		}else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}
	
	/*
	 * @PostMapping("login") public ResponseEntity<?> loginProfesor(@RequestBody
	 * Profesor profesor){ Profesor profesorDb =
	 * profesorService.checkProfesorLogin(profesor); if(profesorDb!=null) {
	 * List<Profesor> profesores = new ArrayList<>(); profesores.add(profesorDb);
	 * List<MProfesor> mProfesores = new ArrayList<>(); mProfesores =
	 * Mapper.convertirLista(profesores); return new ResponseEntity<>(mProfesores,
	 * HttpStatus.OK); }else { return new
	 * ResponseEntity<Void>(HttpStatus.NOT_FOUND); } }
	 */
	
	}



