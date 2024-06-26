package com.ProyectoIntegradorFinal.service.imp;

import com.ProyectoIntegradorFinal.dto.CategoriaDto;
import com.ProyectoIntegradorFinal.entity.Categoria;
import com.ProyectoIntegradorFinal.repository.CategoriaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

    @Service
    public class CategoriaService implements com.ProyectoIntegradorFinal.service.ICategoriaService {
        private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaService.class);
        private final ObjectMapper objectMapper;
        private final CategoriaRepository categoriaRepository;

        @Autowired
        public CategoriaService(ObjectMapper objectMapper, CategoriaRepository categoriaRepository) {
            this.objectMapper = objectMapper;
            this.categoriaRepository = categoriaRepository;
        }


        @Override
        public List<CategoriaDto> listarCategoria() {
            List<Categoria> categorias = categoriaRepository.findAll();
            List<CategoriaDto> categoriaDtos = categorias.stream()
                    .map(categoria -> objectMapper.convertValue(categoria, CategoriaDto.class)).toList();
            LOGGER.info("Listado de todas las categorias: {}",categoriaDtos);
            return categoriaDtos;
        }

        @Override
        public CategoriaDto buscarCategoriaPorId(Long id) {
            Categoria categoria = categoriaRepository.findById(id).orElse(null);
            CategoriaDto categoriaDto = null;
            if(categoria != null){
                categoriaDto = objectMapper.convertValue(categoria, CategoriaDto.class);
                LOGGER.info("Categoria encontrada: {}", categoriaDto);
            }else{
                LOGGER.info("El id no se encuentra registrado en la base de datos");
            }
            return categoriaDto;
        }

        @Override
        public Categoria buscarCategoriaPorNombre(String nombre) {
            Categoria categoria = categoriaRepository.findByNombre(nombre);
            if(categoria!= null){
                LOGGER.info("Categoria encontrada: {}", categoria);
            }else{
                LOGGER.info("El nombre no se encuentra registrado en la base de datos");
            }
            return categoria;
        }

        @Override
        public CategoriaDto registrarCategoria(Categoria categoria, MultipartFile imagen) {

            Categoria categoriaReg = categoriaRepository.save(categoria);

            LOGGER.info("Categoria registrada: {}", categoriaReg);
            return objectMapper.convertValue(categoriaReg, CategoriaDto.class);
        }

        @Override
        public CategoriaDto actualizarCategoria(Categoria categoria, MultipartFile file) {
            //Categoria categoriaNom = categoriaRepository.findByNombre(categoria.getNombre());
            Categoria categoriaEdit = categoriaRepository.findById(categoria.getId()).orElse(null);

            CategoriaDto categoriaDto = null;
            if(categoriaEdit != null){
                categoriaEdit.setNombre(categoria.getNombre());
                categoriaEdit.setDescripcion(categoria.getDescripcion());
                categoriaEdit.setFile(categoria.getFile());
                categoriaRepository.save(categoriaEdit);
                categoriaDto = objectMapper.convertValue(categoriaEdit, CategoriaDto.class);
                LOGGER.warn("Categoria actualizada: {}", categoriaDto);
            }else{
                LOGGER.error("No fue posible actualizar los datos ya que la categoria no se encuentra registrada");
            }
            return categoriaDto;
        }

        @Override
        public void eliminarCategoria(Long id) {
            if(buscarCategoriaPorId(id) != null){
                CategoriaDto categoriaDto = buscarCategoriaPorId(id);
                categoriaRepository.deleteById(id);
                LOGGER.warn("Se ha eliminado la categoria con id: {}", categoriaDto);
            }else{
                LOGGER.error("No se ha encontrado la Categoria con id " + id);
            }
        }
        public List<Categoria> obtenerCategoriasPorIds(List<Long> ids) {
            return categoriaRepository.findAllById(ids);
        }
    }




