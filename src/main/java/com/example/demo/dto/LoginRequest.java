package com.example.demo.dto;

public class LoginRequest {
    private String email;
    private String password;

    // Constructor vacío (necesario para deserialización)
    public LoginRequest() {
    }

    // Constructor con parámetros (opcional)
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
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

