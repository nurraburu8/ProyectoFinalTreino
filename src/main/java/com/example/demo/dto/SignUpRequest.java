package com.example.demo.dto;

public class SignUpRequest {

	private String nombre;
    private String email;
    private String password;

    public SignUpRequest() {
    }

    public SignUpRequest(String email, String password, String nombre) {
        this.email = email;
        this.password = password;
        this.nombre = nombre;
    }
    

    public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	// Getter para email
    public String getEmail() {
        return email;
    }

    // Setter para email
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter para password
    public String getPassword() {
        return password;
    }

    // Setter para password
    public void setPassword(String password) {
        this.password = password;
    }
}
