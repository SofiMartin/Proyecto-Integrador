package com.ProyectoIntegradorFinal.repository;

import com.ProyectoIntegradorFinal.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Query("SELECT p FROM Producto p JOIN p.categorias c WHERE c.id = :categoryId")
    List<Producto> findByCategoria(@Param("categoryId") Long categoryId);
    Producto findByNombre(String nombre);
    @Query("select u from Producto u where u.nombre = ?1")
    Optional<Producto> getName(String nombre);

    @Query("SELECT pr FROM Producto pr JOIN pr.caracteristicas ca WHERE ca.id = :caracteristicaId")
    List<Producto> findByCaracteristica(@Param("caracteristicaId") Long caracteristicaId);
    Producto findByDescripcion(String descripcion);
    @Query("select d from Producto d where d.descripcion = ?1")
    Optional<Producto> getDescripcion(String descripcion);

    @Query("SELECT p FROM Producto p JOIN p.categorias c WHERE c.id IN :categoriaIds")
    List<Producto> findByCategorias_IdIn(@Param("categoriaIds") List<Long> categoriaIds);

    @Query("SELECT pr FROM Producto pr JOIN pr.politicas po WHERE po.id = :politicaId")
    List<Producto> findByPolitica(@Param("politicaId") Long politicaId);
    @Query("select pro from Producto pro where pro.nombre = ?1")
    Optional<Producto> getNombre(String nombre);

    @Query("SELECT p FROM Producto p JOIN p.categorias c WHERE c.id = :categoryId AND p.id NOT IN :excludedProductIds")
    List<Producto> findProductsByCategoriaAndNotInIds(
            @Param("categoryId") Long categoryId,
            @Param("excludedProductIds") List<Long> excludedProductIds
    );






}
