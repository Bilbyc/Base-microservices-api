package com.base.users.dto;

import jakarta.validation.constraints.NotBlank;

public class RegisterRequestDto {

    @NotBlank(message = "Campo username é obrigatório")
    private String username;
    @NotBlank(message = "Campo email é obrigatório")
    private String email;
    @NotBlank(message = "Campo password é obrigatório")
    private String password;

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


