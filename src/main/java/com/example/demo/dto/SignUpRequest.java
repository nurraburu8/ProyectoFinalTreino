package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Datos para registrar una cuenta nueva")
public class SignUpRequest {

    @Schema(example = "Ana Torres")
    @NotBlank(message = "el nombre es obligatorio")
    @Size(max = 60, message = "el nombre no puede superar los 60 caracteres")
    private String nombre;

    @Schema(example = "ana@treino.com")
    @NotBlank(message = "el email es obligatorio")
    @Email(message = "el email no tiene un formato valido")
    @Size(max = 60, message = "el email no puede superar los 60 caracteres")
    private String email;

    @Schema(example = "secreto123", minLength = 8)
    @NotBlank(message = "la contrasena es obligatoria")
    @Size(min = 8, message = "la contrasena debe tener al menos 8 caracteres")
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
