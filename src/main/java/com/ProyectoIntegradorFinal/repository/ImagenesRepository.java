package com.ProyectoIntegradorFinal.repository;


import com.ProyectoIntegradorFinal.entity.Imagenes;
import com.ProyectoIntegradorFinal.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImagenesRepository extends JpaRepository<Imagenes, Long> {

    List<Imagenes> findByProducto(Producto producto);

}
