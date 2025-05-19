package com.back.ticketflow.dto;

import jakarta.validation.constraints.NotNull;

public class UserDTO {

    @NotNull(message = "El email es obligatorio")
    private String email;
    @NotNull(message = "La contraseña es obligatoria")
    private String password;

    public UserDTO() {
    }

    public UserDTO(String email, String password) {
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
