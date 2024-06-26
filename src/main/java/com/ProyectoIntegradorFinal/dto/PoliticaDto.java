package com.ProyectoIntegradorFinal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class PoliticaDto {

    private Long id;
    private String nombre;
    private String descripcion;


    public PoliticaDto() {
    }

    public PoliticaDto(Long id, String nombre, String descripcion) {
        this.id = id;
        this.nombre= nombre;
        this.descripcion = descripcion;

    }
}