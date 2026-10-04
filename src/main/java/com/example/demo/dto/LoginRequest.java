package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Credenciales de acceso")
public class LoginRequest {

    @Schema(example = "ana@treino.com")
    @NotBlank(message = "el email es obligatorio")
    @Email(message = "el email no tiene un formato valido")
    private String email;

    @Schema(example = "secreto123")
    @NotBlank(message = "la contrasena es obligatoria")
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
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
