package com.ProyectoIntegradorFinal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Politica")
public class Politica {

           @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column
        private String nombre;
        @Column(length = 10000)
        private String descripcion;

        @ManyToMany(mappedBy = "politicas")
        @JsonIgnore
        private List<Producto> productos= new ArrayList<>();

        public Politica() {
        }

        public Politica(Long id, String nombre, String descripcion, List<Producto> productos) {
            this.id = id;
            this.nombre = nombre;
            this.descripcion = descripcion;
            this.productos = productos;
        }

        public Politica (String nombre) {
        }

    @Override
    public String toString() {
        return "Politica{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", productos=" + productos +
                '}';
    }
}

