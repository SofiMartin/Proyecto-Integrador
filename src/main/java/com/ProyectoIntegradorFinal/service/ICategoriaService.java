package com.ProyectoIntegradorFinal.service;

import com.ProyectoIntegradorFinal.dto.CategoriaDto;
import com.ProyectoIntegradorFinal.entity.Categoria;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICategoriaService {

    List<CategoriaDto> listarCategoria();

    CategoriaDto buscarCategoriaPorId(Long id);

    Categoria buscarCategoriaPorNombre(String nombre);

    CategoriaDto registrarCategoria(Categoria categoria, MultipartFile imagen);

    CategoriaDto actualizarCategoria(Categoria categoria, MultipartFile file);

    void eliminarCategoria(Long id);

    List<Categoria> obtenerCategoriasPorIds(List<Long> ids);
}
