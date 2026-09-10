package com.example.demo.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Profesor.class, name = "profesor"),
    @JsonSubTypes.Type(value = Admin.class, name = "admin"),
    @JsonSubTypes.Type(value = Usuario.class, name = "usuario")


})
@MappedSuperclass
public class Persona{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column (length=60, name="nombre", nullable = false)
	private String nombre;
	
	@Column(length=60, unique=true, nullable = false)
	private String email;
	
	// 100 chars: un hash BCrypt ocupa 60, con margen por si se cambia de algoritmo
	@Column(length=100, nullable = false)
	private String password;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/** WRITE_ONLY: Jackson acepta "password" al deserializar, pero nunca lo incluye en las respuestas JSON. */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
