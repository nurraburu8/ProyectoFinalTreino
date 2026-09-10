package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonTypeName;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;

@Entity
@Table(name="administradores")
@JsonTypeName("admin")
public class Admin extends Persona{
	   public Admin() {
	    }

		
	
	/*
	 * @Id
	 * 
	 * @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
	 * 
	 * @Column (length=60) private String nombre;
	 * 
	 * @Column(length=60, unique=true, nullable = false) private String email;
	 * 
	 * @Column(nullable = false) private String password;
	 */
	
    @Enumerated(EnumType.STRING)
    private Rol rol = Rol.ADMIN;
	
	/*
	 * public String getEmail() { return email; }
	 * 
	 * public void setEmail(String email) { this.email = email; }
	 * 
	 * public String getPassword() { return password; }
	 * 
	 * public void setPassword(String password) {
	 * 
	 * this.password = password; }
	 * 
	 * 
	 * public String getNombre() { return nombre;
	 * 
	 * }
	 * 
	 * 
	 * public void setNombre(String nombre) { this.nombre = nombre; }
	 * 
	 * public Long getId() { return id; }
	 * 
	 * public void setId(Long id) { this.id = id; }
	 */

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	
	
}
