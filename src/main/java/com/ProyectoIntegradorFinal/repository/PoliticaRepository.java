package com.ProyectoIntegradorFinal.repository;

import com.ProyectoIntegradorFinal.entity.Politica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoliticaRepository extends JpaRepository<Politica, Long> {
    Politica findByNombre(String nombre);

}
