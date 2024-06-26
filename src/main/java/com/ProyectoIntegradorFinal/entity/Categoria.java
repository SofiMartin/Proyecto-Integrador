package com.ProyectoIntegradorFinal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @Column (length = 10000)
    private String descripcion;
    private String file;
    @ManyToMany(mappedBy = "categorias")
    @JsonIgnore
    private List<Producto> productos= new ArrayList<>();

    public Categoria() {
    }

    public Categoria(String nombre, String descripcion, String file, List<Producto> productos) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.file = file;
        this.productos = productos;
    }

    public Categoria(String nombre) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
        return "Categoria{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", file='" + file + '\'' +
                ", productos=" + productos +
                '}';
    }
}
