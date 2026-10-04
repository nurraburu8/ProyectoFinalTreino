package com.example.demo.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LoginRequest;
import com.example.demo.entity.Admin;
import com.example.demo.entity.Persona;
import com.example.demo.entity.Profesor;
import com.example.demo.entity.Usuario;
import com.example.demo.mapper.Mapper;
import com.example.demo.model.MProfesor;
import com.example.demo.service.IAdminService;
import com.example.demo.service.IProfesorService;
import com.example.demo.service.IUsuarioService;

@Tag(name = "Autenticacion", description = "Login unificado para profesores, alumnos y administradores")
@RestController
@RequestMapping("/api")
public class LoginRestController {
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IAdminService adminService;
	
	@Autowired
	private IProfesorService profesorService;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Operation(summary = "Iniciar sesion",
	           description = "Verifica las credenciales contra profesores, alumnos y administradores, en ese orden. La contrasena se compara contra el hash BCrypt almacenado; la respuesta nunca incluye el campo password.")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Credenciales correctas. Devuelve la cuenta encontrada, con el campo 'type' indicando si es profesor, usuario o admin"),
	    @ApiResponse(responseCode = "400", description = "Email o contrasena ausentes, o email con formato invalido"),
	    @ApiResponse(responseCode = "404", description = "No existe esa cuenta o la contrasena no coincide")
	})
	@PostMapping("login")
	public ResponseEntity<?> loginUsuario(@Valid @RequestBody LoginRequest loginRequest){
	    String email = loginRequest.getEmail();
	    String password = loginRequest.getPassword();
	    
	    Profesor profesor = profesorService.findProfesorByEmail(email);
	    if (profesor != null && passwordEncoder.matches(password, profesor.getPassword())) {
	            return new ResponseEntity<>(profesor, HttpStatus.OK);        
	    }
	    Usuario usuario = usuarioService.findUsuarioByEmail(email);
	    if (usuario != null && passwordEncoder.matches(password, usuario.getPassword())) {
	            return new ResponseEntity<>(usuario, HttpStatus.OK);        
	    }
	    Admin admin = adminService.findAdminByEmail(email);
	    if (admin != null && passwordEncoder.matches(password, admin.getPassword())) {
	            return new ResponseEntity<>(admin, HttpStatus.OK);        
	    }
	    return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);

	}

	

}
