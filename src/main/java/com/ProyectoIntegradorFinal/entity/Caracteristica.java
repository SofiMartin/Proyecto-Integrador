package com.ProyectoIntegradorFinal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Caracteristica")
public class Caracteristica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String descripcion;
    private String file;
    @ManyToMany(mappedBy = "caracteristicas")
    @JsonIgnore
    private List<Producto> productos= new ArrayList<>();

    public Caracteristica() {
    }

    public Caracteristica(Long id, String descripcion, String file, List<Producto> productos) {
        this.id = id;
        this.descripcion = descripcion;
        this.file = file;
        this.productos = productos;
    }

    public Caracteristica(String descripcion) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    @Override
    public String toString() {
        return "Caracteristica{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", file='" + file + '\'' +
                ", productos=" + productos +
                '}';
    }
}
