package com.ProyectoIntegradorFinal.controller;

import com.ProyectoIntegradorFinal.dto.ImagenesDto;
import com.ProyectoIntegradorFinal.dto.ProductoDto;
import com.ProyectoIntegradorFinal.entity.Imagenes;
import com.ProyectoIntegradorFinal.entity.Producto;
import com.ProyectoIntegradorFinal.service.IImagenService;
import com.ProyectoIntegradorFinal.service.IProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/imagenes")
public class ImagenController {
    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    private final IImagenService IImagenService;
    private final IProductoService IProductoService;

    @Autowired
    public ImagenController(IImagenService IImagenService, IProductoService IProductoService) {
        this.IImagenService = IImagenService;
        this.IProductoService = IProductoService;
    }

    @PostMapping("/guardar")
    public ResponseEntity<ImagenesDto> registrar(@RequestParam("nombre") String nombre, @RequestParam(value = "file", required = false) MultipartFile[] imagen) {
        ResponseEntity<ImagenesDto> respuesta;
        try {
            Producto productoNom = IProductoService.buscarProductoPorNombre(nombre);

            ProductoDto productoDto = IProductoService.buscarMotorHomePorId(productoNom.getId());

            if (productoDto != null) {
                Producto producto = new Producto(productoDto);

                Imagenes imagenes = new Imagenes();

                imagenes.setProducto(producto);

                /*if (imagen != null) {
                    Path directorioImagenes = Paths.get("src//main//resources//static//frontend//public/images");
                    String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();

                    byte[] bytesImg = imagen.getBytes();
                    Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
                    Files.write(rutaCompleta, bytesImg);
                    imagenes.setFile(imagen.getOriginalFilename());
                } else {
                    imagenes.setFile("");
                }*/


                if (imagen != null && imagen.length > 0) {
                    Path directorioImagenes = Paths.get("src//main//resources//static//frontend//public/images");
                    String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();

                    List<String> nombresArchivos = new ArrayList<>();

                    for (MultipartFile file : imagen) {

                        byte[] bytesImg = file.getBytes();
                        Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + file.getOriginalFilename());
                        Files.write(rutaCompleta, bytesImg);
                        nombresArchivos.add(file.getOriginalFilename());
                    }

                    String archivosConcatenados = String.join("-", nombresArchivos);
                    imagenes.setFile(archivosConcatenados);
                } else {
                    imagenes.setFile("");
                }

                ImagenesDto imagenesGuardadas = IImagenService.agregarImagen(imagenes);

                if (imagenesGuardadas != null) {
                    logger.info("Imagen guardada exitosamente: {}", imagenesGuardadas);
                    respuesta = new ResponseEntity<>(imagenesGuardadas, null, HttpStatus.CREATED);
                } else {
                    logger.error("No se pudo guardar la imagen");
                    respuesta = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }
            } else {
                logger.error("No se pudo encontrar el producto con el nombre proporcionado: " + nombre);
                respuesta = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            logger.error("Error al guardar la imagen", e);
            respuesta = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return respuesta;
    }

    @GetMapping("/buscarPorProducto")
    public ResponseEntity<List<ImagenesDto>> buscarPorProducto(@RequestParam("nombre") String nombre) {
        ResponseEntity<List<ImagenesDto>> respuesta;
        List<ImagenesDto> imagenesDtos = null;

        // Verifica si se proporciona un valor válido para el parámetro fiscalia
        if (nombre != null && !nombre.isEmpty()) {
            //internos = iInternoService.listarInternosFiltro(fiscalia);
            Producto productoNom = IProductoService.buscarProductoPorNombre(nombre);
            imagenesDtos = IImagenService.listarImagenes(productoNom);

        } else {
            // Si no se proporciona un valor válido para fiscalia o es vacío, devuelve todos los internos
            //imagenesDtos = iInternoService.listarInternos();
        }

        if (!imagenesDtos.isEmpty()) {
            respuesta = new ResponseEntity<>(imagenesDtos, HttpStatus.OK);
        } else {
            // En lugar de devolver HttpStatus.NOT_FOUND, puedes devolver una lista vacía
            respuesta = new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        }

        return respuesta;
    }
}
