package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

@RestController
@RequestMapping("/api")
public class ProfesorRestController {
	
	@Autowired
	private IProfesorService profesorService;

	@Autowired
	private PasswordEncoder passwordEncoder; 
	
	@GetMapping("/profesores")
	@ResponseStatus(HttpStatus.OK)
	public List<Profesor> getProfesores(){
		return profesorService.findAll();
	}

	@PostMapping("/sign_up")
	public ResponseEntity<?> addProfesor(@RequestBody SignUpRequest signUpRequest){
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
	
	@DeleteMapping("/delete")
	public ResponseEntity<Void> deleteAllProfesores(){
		profesorService.deleteAllProfesores();
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping("/find_profesor/{id}")
	public ResponseEntity<?> findProfesor(@PathVariable(value="id")Long id){
		Profesor profesorDb = profesorService.findById(id);
		if(profesorDb==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<Profesor>(profesorDb, HttpStatus.OK);
		}
	}
	
	@GetMapping("/profesores_publicados")
	public ResponseEntity<List<Profesor>> profesoresPublicados(){
	        List<Profesor> profesores = profesorService.findProfesoresPublicados();
	        if (profesores.isEmpty()) {
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        }
	        return new ResponseEntity<>(profesores, HttpStatus.OK);
	    
	}
	
	
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



