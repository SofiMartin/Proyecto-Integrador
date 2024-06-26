package com.ProyectoIntegradorFinal.dto;

public class CategoriaDto {

    private Long id;
    private String nombre;
    private String descripcion;
    private String file;

    public CategoriaDto() {
    }

    public CategoriaDto(Long id, String nombre, String descripcion, String file) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.file = file;
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


}
