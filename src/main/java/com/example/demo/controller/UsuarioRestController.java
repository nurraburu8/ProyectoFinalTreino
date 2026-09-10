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

@RestController
@RequestMapping("/api")
public class UsuarioRestController {
	
	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
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
	
	@PostMapping("/crear_usuario")
	public ResponseEntity<?> agregarUsuario(@RequestBody SignUpRequest signUpRequest){
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
	
	@DeleteMapping("/delete_usuarios")
	public ResponseEntity<Void> deleteAllUsuarios(){
		usuarioService.deleteAllUsuarios();
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping("/find_usuario/{id}")
	public ResponseEntity<?> findUsuario(@PathVariable(value="id")Long id){
		Usuario usuarioDb = usuarioService.findById(id);
		if(usuarioDb==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<Usuario>(usuarioDb, HttpStatus.OK);
		}
	}
	
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
