package com.ProyectoIntegradorFinal.dto;

public class CaracteristicaDto {

    private Long id;
    private String descripcion;
    private String file;

    public CaracteristicaDto() {
    }

    public CaracteristicaDto(Long id, String descripcion, String file) {
        this.id = id;
        this.descripcion = descripcion;
        this.file = file;
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
}
