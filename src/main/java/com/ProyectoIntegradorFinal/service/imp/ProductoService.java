package com.ProyectoIntegradorFinal.service.imp;

import com.ProyectoIntegradorFinal.dto.CaracteristicaDto;
import com.ProyectoIntegradorFinal.dto.CategoriaDto;
import com.ProyectoIntegradorFinal.dto.ProductoDto;
import com.ProyectoIntegradorFinal.entity.Caracteristica;
import com.ProyectoIntegradorFinal.entity.Categoria;
import com.ProyectoIntegradorFinal.entity.Producto;
import com.ProyectoIntegradorFinal.exceptions.NombreProductoDuplicadoException;
import com.ProyectoIntegradorFinal.repository.CaracteristicaRepository;
import com.ProyectoIntegradorFinal.repository.CategoriaRepository;
import com.ProyectoIntegradorFinal.repository.ProductoRepository;
import com.ProyectoIntegradorFinal.service.IProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductoService implements IProductoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductoService.class);
    private final ObjectMapper objectMapper;
    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository; // Asumiendo que tienes un repositorio para las categorías

    private final CaracteristicaRepository caracteristicaRepository;
    @Autowired
    public ProductoService(ObjectMapper objectMapper, ProductoRepository productoRepository, CategoriaRepository categoriaRepository, CaracteristicaRepository caracteristicaRepository) {
        this.objectMapper = objectMapper;
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.caracteristicaRepository = caracteristicaRepository;
    }

    @Override
    public List<ProductoDto> listarMotorHome() {
        List<Producto> productos = productoRepository.findAll();
        List<ProductoDto> motorHomesDtos = productos.stream()
                .map(producto -> objectMapper.convertValue(producto, ProductoDto.class)).toList();
        LOGGER.info("Listado de todos los motorhomes: {}", motorHomesDtos);
        return motorHomesDtos;
    }

    @Override
    public List<ProductoDto> listarProductoFiltro(Long id) {
        List<Producto> productos = productoRepository.findByCategoria(id);
        List<ProductoDto> motorHomesDtos = productos.stream()
                .map(producto -> objectMapper.convertValue(producto, ProductoDto.class)).toList();
        LOGGER.info("Listado de todos los motorhomes: {}", motorHomesDtos);
        return motorHomesDtos;
    }

    @Override
    public ProductoDto buscarMotorHomePorId(Long id) {
        Producto producto = productoRepository.findById(id).orElse(null);
        ProductoDto productoDto = null;
        if(producto != null){
            productoDto = objectMapper.convertValue(producto, ProductoDto.class);
            LOGGER.info("MotorHome encontrado: {}", productoDto);
        }else{
            LOGGER.info("El id no se encuentra registrado en la base de datos");
        }
        return productoDto;
    }
    @Override
    public Producto buscarProductoPorNombre(String nombre) {
        Producto producto = productoRepository.findByNombre(nombre);
        ProductoDto productoDto = null;
        if(producto!= null){
            productoDto = objectMapper.convertValue(producto , ProductoDto.class);
            LOGGER.info("Producto encontrado: {}", productoDto);
        }else{
            LOGGER.info("El nombre no se encuentra registrado en la base de datos");
        }
        return producto;
    }

    @Override
    public List<ProductoDto> findProductsByCategoriaAndNotInIds(Long categoryId, List<Long> excludedProductIds) {
        List<Producto> productos = productoRepository.findProductsByCategoriaAndNotInIds(categoryId, excludedProductIds);
        return productos.stream()
                .map(producto -> objectMapper.convertValue(producto, ProductoDto.class))
                .collect(Collectors.toList());
    }
    @Override
    public ProductoDto registrarMotorHome(Producto producto) {
        // Verificar si ya existe un producto con el mismo nombre
        Producto productoExistente = buscarProductoPorNombre(producto.getNombre());
        if (productoExistente != null) {
            // Si ya existe, lanzar una excepción o manejar de alguna manera
            throw new NombreProductoDuplicadoException("Ya existe un producto con el mismo nombre");
            //throw new DataIntegrityViolationException("Ya existe un producto con el mismo nombre");
        }

        if (producto.getCategorias() == null) {
            producto.setCategorias(new ArrayList<>());
        }

        // Asociar el producto a las categorías existentes
        List<Categoria> categoriasPersisted = new ArrayList<>();
        for (Categoria categoria : producto.getCategorias()) {
            if (categoria.getId() != null) {
                // Si la categoría tiene un ID, asumimos que ya está persistida
                Categoria persistedCategoria = categoriaRepository.getById(categoria.getId());
                categoriasPersisted.add(persistedCategoria);
                persistedCategoria.getProductos().add(producto);
            } else {
                // Si la categoría no tiene un ID, asumimos que es nueva y la persistimos
                Categoria nuevaCategoria = categoriaRepository.save(categoria);
                categoriasPersisted.add(nuevaCategoria);
                nuevaCategoria.getProductos().add(producto);
            }
        }
        producto.setCategorias(categoriasPersisted);

        // Asociar características al producto
        List<Caracteristica> caracteristicasPersisted = new ArrayList<>();
        for (Caracteristica caracteristica : producto.getCaracteristicas()) {
            if (caracteristica.getId() != null) {
                Caracteristica persistedCaracteristica = caracteristicaRepository.getById(caracteristica.getId());
                caracteristicasPersisted.add(persistedCaracteristica);
                // Aquí podrías asociar la característica al producto si es necesario
                persistedCaracteristica.getProductos().add(producto);
            } else {
                Caracteristica nuevaCaracteristica = caracteristicaRepository.save(caracteristica);
                caracteristicasPersisted.add(nuevaCaracteristica);
                // Aquí podrías asociar la característica al producto si es necesario
                nuevaCaracteristica.getProductos().add(producto);
            }
        }

        // Asignar las características al producto
        producto.setCaracteristicas(caracteristicasPersisted);



        // --------------------------------------------
        Producto productoReg = productoRepository.save(producto);

        List<CategoriaDto> categoriaDto = productoReg.getCategorias()
                .stream()
                .map(cat -> objectMapper.convertValue(cat, CategoriaDto.class))
                .collect(Collectors.toList());

        // Obtener DTOs para las características del producto registrado
        List<CaracteristicaDto> caracteristicaDto = productoReg.getCaracteristicas()
                .stream()
                .map(car -> objectMapper.convertValue(car, CaracteristicaDto.class))
                .collect(Collectors.toList());

      // Asignar los DTOs de las características al DTO del producto

        ProductoDto productoDtoNuevo = objectMapper.convertValue(productoReg, ProductoDto.class);
        productoDtoNuevo.setCategorias(categoriaDto);
        productoDtoNuevo.setCaracteristicas(caracteristicaDto);
        LOGGER.info("MotorHome registrado: {}", productoReg);

        return productoDtoNuevo;
    }

    /*@Override
    public ProductoDto actualizarMotorHome(Producto producto) {
        Producto productoEdit = productoRepository.findById(producto.getId()).orElse(null);
        ProductoDto productoDto = null;
        if(productoEdit != null){
            productoEdit = producto;
            productoRepository.save(productoEdit);
            productoDto = objectMapper.convertValue(productoEdit, ProductoDto.class);
            LOGGER.warn("MotorHome actualizado: {}", productoDto);
        }else{
            LOGGER.error("No fue posible actualizar los datos ya que el MotorHome no se encuentra registrado");
        }
        return productoDto;
    }*/

    @Override
    public ProductoDto actualizarMotorHome(Producto producto) {
        Producto productoNom = productoRepository.findByNombre(producto.getNombre());

        Producto productoEdit = productoRepository.findById(producto.getId()).orElse(null);

        ProductoDto productoDto = null;
        if(productoEdit != null){
            /*productoEdit.setNombre(producto.getNombre());
            productoEdit.setMarca(producto.getMarca());
            productoEdit.setModelo(producto.getModelo());
            productoEdit.setPrecioAlquiler(producto.getPrecioAlquiler());
            productoEdit.setAnioFabricacion(producto.getAnioFabricacion());
            productoEdit.setDescripcion(producto.getDescripcion());
            if(producto.getCategorias() != null){
            productoEdit.setCategorias(producto.getCategorias());}
            if(producto.getCaracteristicas()!=null){
            productoEdit.setCaracteristicas(producto.getCaracteristicas());}*/
            productoEdit = producto;

           //productoEditEdit.setFile(productoEdit.getFile());
            productoRepository.save(productoEdit);
            productoDto = objectMapper.convertValue(productoEdit, ProductoDto.class);
            LOGGER.warn("producto actualizado: {}", productoDto);
        }else{
            LOGGER.error("No fue posible actualizar los datos ya que el producto no se encuentra registrado");
        }
        return productoDto;
    }

    @Override
    public void eliminarMotorHome(Long id) {
        if(buscarMotorHomePorId(id) != null){
            ProductoDto productoDto = buscarMotorHomePorId(id);
            productoRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el MotorHome con id: {}", productoDto);
        }else{
            LOGGER.error("No se ha encontrado el MotorHome con id " + id);
        }
    }

    @Override
    public List<ProductoDto> listarProductoFiltroPorCategorias(List<Long> categoriaIds) {
        List<Producto> productos = productoRepository.findByCategorias_IdIn(categoriaIds);
        List<ProductoDto> motorHomesDtos = productos.stream()
                .map(producto -> objectMapper.convertValue(producto, ProductoDto.class))
                .collect(Collectors.toList());
        LOGGER.info("Listado de productos filtrados por categorías: {}", motorHomesDtos);
        return motorHomesDtos;
    }
}
