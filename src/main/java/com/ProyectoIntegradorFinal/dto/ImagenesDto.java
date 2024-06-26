package com.ProyectoIntegradorFinal.dto;

import com.ProyectoIntegradorFinal.entity.Producto;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class ImagenesDto {
    private String file;
    private Producto producto;

    public ImagenesDto() {
    }

    public ImagenesDto(String file, Producto producto) {
        this.file = file;
        this.producto = producto;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
