package com.example.demo.controller;

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

import com.example.demo.dto.SignUpRequest;
import com.example.demo.entity.Admin;
import com.example.demo.entity.Rol;
import com.example.demo.entity.Usuario;
import com.example.demo.service.IAdminService;

@Tag(name = "Administradores", description = "Alta de cuentas de administrador")
@RestController
@RequestMapping("/api")
public class AdminRestController {
	
	@Autowired
	private IAdminService adminService;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Operation(summary = "Registrar un administrador",
	           description = "Crea una cuenta con rol ADMIN. La contrasena se almacena cifrada con BCrypt.")
	@ApiResponses({
	    @ApiResponse(responseCode = "201", description = "Administrador creado"),
	    @ApiResponse(responseCode = "400", description = "Datos invalidos (nombre vacio, email mal formado o contrasena menor a 8 caracteres)"),
	    @ApiResponse(responseCode = "409", description = "Ya existe una cuenta con ese email")
	})
	@PostMapping("/crear_admin")
	public ResponseEntity<?> agregarAdmin(@Valid @RequestBody SignUpRequest signUpRequest){
		String email = signUpRequest.getEmail();
		
		if(adminService.findAdminByEmail(email) != null) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}else {
	        Admin nuevoAdmin = new Admin();
	        nuevoAdmin.setNombre(signUpRequest.getNombre());
	        nuevoAdmin.setEmail(signUpRequest.getEmail());
	        nuevoAdmin.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
	        nuevoAdmin.setRol(Rol.ADMIN);

	        adminService.save(nuevoAdmin);
			return new ResponseEntity<Admin>(nuevoAdmin, HttpStatus.CREATED);
		}
	}
	
	/*
	 * @PostMapping("login_admin") public ResponseEntity<?> loginAdmin(@RequestBody
	 * Admin admin){ Admin adminDb = adminService.checkAdminLogin(admin);
	 * if(adminDb!=null) { return new ResponseEntity<>(adminDb, HttpStatus.OK);
	 * }else { return new ResponseEntity<Void>(HttpStatus.NOT_FOUND); } }
	 */
	
	

}
