package com.ProyectoIntegradorFinal.repository;


import com.ProyectoIntegradorFinal.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    @Query("SELECT DISTINCT r.producto.id FROM Reserva r " +
            "WHERE (r.fechaIni BETWEEN :fechaInicio AND :fechaFin) OR (r.fechaFin BETWEEN :fechaInicio AND :fechaFin) " +
            "AND r.producto.id IN (SELECT p.id FROM Producto p JOIN p.categorias c WHERE c.id = :categoriaId)")
    List<Long> findByCategoriaAndFechasDisponibles(
            @Param("categoriaId") Long categoriaId,
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin
    );
    //------AGREGO PARA BUSCAR LAS FECHAS OCUPADAS
    @Query("SELECT DISTINCT r.fechaIni FROM Reserva r " +
            "WHERE (r.fechaIni BETWEEN :fechaInicio AND :fechaFin OR r.fechaFin BETWEEN :fechaInicio AND :fechaFin) " +
            "AND r.producto.id = :productoId")
    List<LocalDate> findFechasOcupadasByProductoAndFechasDisponibles(
            @Param("productoId") Long productoId,
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin
    );

    @Query("SELECT r FROM Reserva r WHERE r.producto.id = :productoId")
    List<Reserva> findByProductoId(Long productoId);

}
