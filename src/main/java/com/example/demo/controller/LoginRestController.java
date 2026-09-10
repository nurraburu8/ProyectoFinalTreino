package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

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
	
	@PostMapping("login")
	public ResponseEntity<?> loginUsuario(@RequestBody LoginRequest loginRequest){
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
