package com.ProyectoIntegradorFinal.dto;

public class MotorHomeDto {
    private String marca;
    private String modelo;
    private int añoFabricacion;
    private String descripcion;
    private double precioAlquiler;
    private byte [] imagen;

    public MotorHomeDto() {
    }

    public MotorHomeDto(String marca, String modelo, int añoFabricacion, String descripcion, double precioAlquiler, byte[] imagen) {
        this.marca = marca;
        this.modelo = modelo;
        this.añoFabricacion = añoFabricacion;
        this.descripcion = descripcion;
        this.precioAlquiler = precioAlquiler;
        this.imagen = imagen;
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

    public int getAñoFabricacion() {
        return añoFabricacion;
    }

    public void setAñoFabricacion(int añoFabricacion) {
        this.añoFabricacion = añoFabricacion;
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

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
}
