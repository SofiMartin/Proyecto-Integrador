package com.ProyectoIntegradorFinal.repository;

import com.ProyectoIntegradorFinal.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Categoria findByNombre(String nombre);
}
