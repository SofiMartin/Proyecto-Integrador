package com.ProyectoIntegradorFinal.service;

import com.ProyectoIntegradorFinal.dto.ProductoDto;
import com.ProyectoIntegradorFinal.entity.Producto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductoService {

    List<ProductoDto> listarMotorHome();
    List<ProductoDto> listarProductoFiltro(Long id);

    List<ProductoDto> listarProductoFiltroPorCategorias(List<Long> categoriaIds); // Nuevo método
    List<ProductoDto> findProductsByCategoriaAndNotInIds(Long categoryId, List<Long> excludedProductIds);

    ProductoDto buscarMotorHomePorId(Long id);

    Producto buscarProductoPorNombre(String nombre);

    ProductoDto registrarMotorHome(Producto producto);

    ProductoDto actualizarMotorHome(Producto producto);

    void eliminarMotorHome(Long id);

}
