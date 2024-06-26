package com.ProyectoIntegradorFinal.controller;

import com.ProyectoIntegradorFinal.dto.CategoriaDto;
import com.ProyectoIntegradorFinal.dto.ProductoDto;
import com.ProyectoIntegradorFinal.entity.Caracteristica;
import com.ProyectoIntegradorFinal.entity.Categoria;
import com.ProyectoIntegradorFinal.entity.Producto;
import com.ProyectoIntegradorFinal.exceptions.NombreProductoDuplicadoException;
import com.ProyectoIntegradorFinal.service.IProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/motorhome")
public class ProductoController {
    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    private final IProductoService IProductoService;

    @Autowired
    public ProductoController(IProductoService IProductoService) {
        this.IProductoService = IProductoService;
    }


    @GetMapping()
    public List<ProductoDto> listarTodos(){
        return IProductoService.listarMotorHome();
    }
    @GetMapping("/categoria/filtrar")
    public List<ProductoDto> filtrarPorCategorias(@RequestParam Long categoriaIds, @RequestParam String excludedProductIds) {
        // Convierte la cadena de IDs separada por comas en una lista de Long

        List<Long> excludedProductIdsList = Arrays.stream(excludedProductIds.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        return IProductoService.findProductsByCategoriaAndNotInIds(categoriaIds, excludedProductIdsList);
    }

    @GetMapping("/categoria/{categoryId}")
    public List<ProductoDto> obtenerPorCategoria(@PathVariable Long categoryId) {
        return IProductoService.listarProductoFiltro(categoryId);
    }
    @GetMapping("/caracteristica/{caracteristicaId}")
    public List<ProductoDto> obtenerPorCaracteristica(@PathVariable Long caracteristicaId) {
        return IProductoService.listarProductoFiltro(caracteristicaId);
    }
    @PostMapping("/registrar")
    public ResponseEntity<ProductoDto> registrar(@RequestBody Producto producto) {
        ResponseEntity<ProductoDto> respuesta;
        try {
            logger.info("Intentando registrar MotorHome: {}", producto);

            ProductoDto motorHomeRegistrado = IProductoService.registrarMotorHome(producto);

            if (motorHomeRegistrado != null) {
                logger.info("MotorHome registrado exitosamente: {}", motorHomeRegistrado);
                respuesta = new ResponseEntity<>(motorHomeRegistrado, null, HttpStatus.CREATED);
            } else {
                logger.error("No se pudo registrar el MotorHome");
                respuesta = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (NombreProductoDuplicadoException e) {
            logger.error("Error al registrar MotorHome - Nombre duplicado", e);
            respuesta = ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            logger.error("Error al registrar MotorHome", e);
            respuesta = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return respuesta;
    }

    @GetMapping("/detalle/id/{id}")
    public ResponseEntity<ProductoDto> buscarPorId(@PathVariable Long id){
        ResponseEntity<ProductoDto> respuesta;
        ProductoDto productoDto = IProductoService.buscarMotorHomePorId(id);
        if(productoDto != null) respuesta = new ResponseEntity<>(productoDto, null, HttpStatus.OK);
        else respuesta = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return respuesta;
    }
    @GetMapping("/detalle/{nombre}")
    public ResponseEntity<Producto> buscarPorProductoPorNombre(@PathVariable String nombre){
        ResponseEntity<Producto> respuesta;
        Producto producto = IProductoService.buscarProductoPorNombre(nombre);
        if(producto != null) respuesta = new ResponseEntity<>(producto, null, HttpStatus.OK);
        else respuesta = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return respuesta;
    }

    @GetMapping("/categoria/all")
    public List<ProductoDto> obtenerPorCategorias(@RequestParam List<Long> categoriaIds) {
        return IProductoService.listarProductoFiltroPorCategorias(categoriaIds);
    }

    @DeleteMapping("/eliminar/{id}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        IProductoService.eliminarMotorHome(id);
        return ResponseEntity.ok("MotorHome Eliminado");
    }


    @PutMapping(value = "/actualizar")
    public ResponseEntity<ProductoDto> actualizarProducto(@RequestBody Producto producto/*@RequestPart("nombre") String nombre,
                                                          @RequestPart("marca") String marca,
                                                          @RequestParam("descripcion") String descripcion,
                                                          @RequestParam("modelo") String modelo,
                                                          @RequestParam("anioFabricacion") int anioFabricacion,
                                                          @RequestParam("precioAlquiler") double precioAlquiler,
                                                          @RequestParam("categorias") List<Categoria> categoriasJson,
                                                          @RequestParam("caracteristicas") List<Caracteristica> caracteristicasJson*/
                                                         // @RequestParam("categoria") List<Categoria> categorias,
                                                          //@RequestParam("caracteristicas") List<Caracteristica> caracteristicas
                                                          ) {
        ResponseEntity<ProductoDto> respuesta;
        try {
            /*Producto producto = new Producto();
            producto.setNombre(nombre);
            producto.setMarca(marca);
            producto.setDescripcion(descripcion);
            producto.setModelo(modelo);
            producto.setAnioFabricacion(anioFabricacion);
            producto.setPrecioAlquiler(precioAlquiler);


            producto.setCategorias(categoriasJson);


            producto.setCaracteristicas(caracteristicasJson);*/

            // Aquí deberías manejar el archivo adjunto (MultipartFile 'file')
            // para guardar o actualizar el producto con el archivo adjunto

            ProductoDto productoActualizado = IProductoService.actualizarMotorHome(producto);

            if (productoActualizado != null) {
                respuesta = new ResponseEntity<>(productoActualizado, HttpStatus.OK);
            } else {
                respuesta = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            respuesta = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return respuesta;
    }
   /* @PutMapping(value = "/actualizar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductoDto> actualizarProducto(
            @RequestPart("producto") Producto producto,
            @RequestPart("file") MultipartFile file){
        ResponseEntity<ProductoDto> respuesta;
        try {
            Producto productoActualizar = new Producto();
            // Llamar al servicio con el objeto ProductoDto recibido
            ProductoDto productoActualizado = IProductoService.actualizarMotorHome(producto);

            if (productoActualizado != null) {
                logger.info("Producto actualizado correctamente: {}", productoActualizado);
                respuesta = new ResponseEntity<>(productoActualizado, null, HttpStatus.OK);
            } else {
                logger.error("No se pudo actualizar el Producto");
                respuesta = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            logger.error("Error al actualizar el Producto", e);
            respuesta = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return respuesta;
    }*/
   /* @PutMapping("/actualizar")
      public ResponseEntity<ProductoDto> actualizarProducto(@RequestParam("nombre") String nombre,
                                                          @RequestParam("descripcion") String descripcion,
                                                          @RequestParam("marca") String marca,
                                                          @RequestParam("modelo") String modelo,
                                                           @RequestParam("anioFabricacion") int anioFabricacion,
                                                           @RequestParam("precioAlquiler") double precioAlquiler
                                                          //@RequestParam(value = "file", required = false) MultipartFile file
    ) {
        ResponseEntity<ProductoDto> respuesta;
        try {
            Producto productoActualizar = new Producto();

            productoActualizar.setNombre(nombre);
            productoActualizar.setDescripcion(descripcion);
            productoActualizar.setMarca(marca);
            productoActualizar.setModelo(modelo);
            productoActualizar.setAnioFabricacion(anioFabricacion);
            productoActualizar.setPrecioAlquiler(precioAlquiler);*/

             /* if(file != null){
                Path directorioImagenes = Paths.get("src//main//resources//static//frontend//public/images");

                String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();


                byte [] bytesImg = file.getBytes();
                Path rutaCompleta= Paths.get(rutaAbsoluta + "//" + file.getOriginalFilename());
                Files.write(rutaCompleta, bytesImg);
                productoActualizar.setFile(file.getOriginalFilename());

            }
            else {
                productoActualizar.setFile(null);
            }*/


            // Llamar al servicio con la instancia de Producto
           /* ProductoDto productoActualizado = IProductoService.actualizarMotorHome(productoActualizar);

            if (productoActualizado != null) {
                logger.info("Producto actualizado correctamente: {}", productoActualizado);
                respuesta = new ResponseEntity<>(productoActualizado, null, HttpStatus.OK);
            } else {
                logger.error("No se pudo actualizar el Producto");
                respuesta = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            logger.error("Error al actualizar el Producto", e);
            respuesta = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return respuesta;
    }*/
}
