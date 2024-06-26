package com.ProyectoIntegradorFinal.dto;

import java.time.LocalDate;
import java.util.Date;

public class ReservaDto {

    private Long id;
    private String nombre;
    private LocalDate fechaIni;
    private LocalDate fechaFin;

    public ReservaDto() {
    }

    public ReservaDto(Long id, String nombre, LocalDate fechaIni, LocalDate fechaFin) {
        this.id = id;
        this.nombre = nombre;
        this.fechaIni = fechaIni;
        this.fechaFin = fechaFin;
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

    public LocalDate getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(LocalDate fechaIni) {
        this.fechaIni = fechaIni;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
}
