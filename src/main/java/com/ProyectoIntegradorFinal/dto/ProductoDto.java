package com.ProyectoIntegradorFinal.dto;

import java.util.List;

public class ProductoDto {
    private Long id;
    private String nombre;
    private String marca;
    private String modelo;
    private int anioFabricacion;
    private String descripcion;
    private double precioAlquiler;
    private List<CategoriaDto> categorias;

    //-----------AGREGO CARACTERISTICAS------------------
    private List<CaracteristicaDto> caracteristicas;

    //-----------AGREGO POLITICAS------------------
    private List<PoliticaDto> politicas;
    //------------------------------------------------

    public ProductoDto() {
    }

    public ProductoDto(Long id, String nombre, String marca, String modelo, int anioFabricacion, String descripcion, double precioAlquiler, List<CategoriaDto> categorias, List<CaracteristicaDto> caracteristicas, List<PoliticaDto> politicas) {
        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.modelo = modelo;
        this.anioFabricacion = anioFabricacion;
        this.descripcion = descripcion;
        this.precioAlquiler = precioAlquiler;
        this.categorias = categorias;
        this.caracteristicas = caracteristicas;
        this.politicas = politicas;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
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


    public List<CategoriaDto> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<CategoriaDto> categorias) {
        this.categorias = categorias;
    }

    public List<CaracteristicaDto> getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(List<CaracteristicaDto> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public List<PoliticaDto> getPoliticas() {
        return politicas;
    }

    public void setPoliticas(List<PoliticaDto> politicas) {
        this.politicas = politicas;
    }
}

