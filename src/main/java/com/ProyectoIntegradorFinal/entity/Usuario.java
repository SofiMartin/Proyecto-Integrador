package com.ProyectoIntegradorFinal.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "Usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = RolUsuario.class, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RolUsuario> roles;

    public Usuario() {
    }

    public Usuario(String email, String username, String password, Set<RolUsuario> roles) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
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

    public Set<RolUsuario> getRoles() {
        return roles;
    }

    public void setRoles(Set<RolUsuario> roles) {
        this.roles = roles;
    }
}
