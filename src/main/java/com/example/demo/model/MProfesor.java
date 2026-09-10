package com.example.demo.model;


import com.example.demo.entity.Profesor;

public class MProfesor {
	
	public MProfesor() {
		
	}
	
	public MProfesor(Long id, String nombre, String email, byte[] foto, String telefono) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.foto = foto;
		this.telefono = telefono;
	}
	
    public MProfesor(Profesor profesor) {
		this.id = profesor.getId();
		this.nombre = profesor.getNombre();
		this.email = profesor.getEmail();
		this.telefono = profesor.getTelefono();
		this.foto = profesor.getFoto();
	}

	private Long id;
	private String nombre;
	private String email;
	private byte[] foto;
	private String telefono;

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
	public byte[] getFoto() {
		return foto;
	}
	public void setFoto(byte[] foto) {
		this.foto = foto;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

}
