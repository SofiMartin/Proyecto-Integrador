package com.ProyectoIntegradorFinal.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Roles")
public class RolUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private ERole name;

    public RolUsuario() {
    }

    public RolUsuario(ERole name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }
}
