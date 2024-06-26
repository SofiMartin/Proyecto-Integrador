package com.ProyectoIntegradorFinal.dto;

import java.util.Set;

public class UsuarioDto {
    private String email;
    private String username;
    private String password;
    private Set<String> roles;

    public UsuarioDto() {
    }

    public UsuarioDto(String email, String username, String password, Set<String> roles) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
