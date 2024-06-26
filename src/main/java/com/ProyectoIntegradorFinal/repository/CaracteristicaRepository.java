package com.ProyectoIntegradorFinal.repository;

import com.ProyectoIntegradorFinal.entity.Caracteristica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaracteristicaRepository extends JpaRepository<Caracteristica, Long> {
    Caracteristica findByDescripcion(String descripcion);

}
