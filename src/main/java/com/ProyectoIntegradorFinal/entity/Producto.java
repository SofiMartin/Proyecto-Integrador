package com.ProyectoIntegradorFinal.entity;

import com.ProyectoIntegradorFinal.dto.ProductoDto;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String marca;
    private String modelo;
    @Column(name = "anioFabricacion")
    private int anioFabricacion;
    private String descripcion;
    private double precioAlquiler;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "ProductoCategoria",
            joinColumns = @JoinColumn(name = "producto_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Categoria> categorias ;

    //----------------AGREGO CARACTERISTICA-------------------------
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "ProductoCaracteristica",
            joinColumns = { @JoinColumn(name = "producto_id") },
            inverseJoinColumns = { @JoinColumn(name = "caracteristica_id") }
    )
    private List<Caracteristica> caracteristicas;

    //----------
//----------------AGREGO POLITICA-------------------------
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "ProductoPolitica",
            joinColumns = { @JoinColumn(name = "producto_id") },
            inverseJoinColumns = { @JoinColumn(name = "politica_id") }
    )
    private List<Politica> politicas;

    //----------
    //private String file;

    public Producto() {
    }

    public Producto(String nombre, String marca, String modelo, int anioFabricacion, String descripcion, double precioAlquiler, List<Categoria> categorias, List<Caracteristica> caracteristicas) {
        this.nombre = nombre;
        this.marca = marca;
        this.modelo = modelo;
        this.anioFabricacion = anioFabricacion;
        this.descripcion = descripcion;
        this.precioAlquiler = precioAlquiler;
        // Inicializar la lista de categorías si es null
        if (categorias == null) {
            this.categorias = new ArrayList<>();
        } else {
            this.categorias = categorias;
        }
        // Inicializar la lista de caracteristicas si es null
        if (caracteristicas == null) {
            this.caracteristicas = new ArrayList<>();
        } else {
            this.caracteristicas = caracteristicas;
        }
        // Inicializar la lista de politicas si es null
        if (politicas == null) {
            this.politicas = new ArrayList<>();
        } else {
            this.politicas = politicas;
        }
    }

    public Producto(ProductoDto productoDto) {
        this.id = productoDto.getId();
        this.nombre = productoDto.getNombre();
        this.marca = productoDto.getMarca();
        this.modelo = productoDto.getModelo();
        this.descripcion = productoDto.getDescripcion();
        this.anioFabricacion = productoDto.getAnioFabricacion();
        this.precioAlquiler = productoDto.getPrecioAlquiler();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMarca() {
        return marca;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnioFabricacion() {
        return anioFabricacion;
    }

    public void setAnioFabricacion(int anioFabricacion) {
        this.anioFabricacion = anioFabricacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioAlquiler() {
        return precioAlquiler;
    }

    public void setPrecioAlquiler(double precioAlquiler) {
        this.precioAlquiler = precioAlquiler;
    }


    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public List<Caracteristica> getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(List<Caracteristica> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public List<Politica> getPoliticas() {
        return politicas;
    }

    public void setPoliticas(List<Politica> politicas) {
        this.politicas = politicas;
    }
}
