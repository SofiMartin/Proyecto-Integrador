package com.ProyectoIntegradorFinal.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Imagenes")
public class Imagenes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String file;
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    public Imagenes() {
    }

    public Imagenes(String file, Producto producto) {
        this.file = file;
        this.producto = producto;
    }

    public Long getId() {
        return id;
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
