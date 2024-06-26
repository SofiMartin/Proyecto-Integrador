package com.ProyectoIntegradorFinal.controller;


import com.ProyectoIntegradorFinal.dto.MotorHomeDto;
import com.ProyectoIntegradorFinal.entity.MotorHome;
import com.ProyectoIntegradorFinal.service.IMotorHomeService;
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
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/motorhome2")
public class MotorHomeController {
    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    private final IMotorHomeService IMotorHomeService;

    @Autowired
    public MotorHomeController(com.ProyectoIntegradorFinal.service.IMotorHomeService IMotorHomeService) {
        this.IMotorHomeService = IMotorHomeService;
    }


    @PostMapping("/registrar")
    public ResponseEntity<MotorHomeDto> registrar(
            @RequestParam("marca") String marca,
            @RequestParam("modelo") String modelo,
            @RequestParam("anioFabricacion") int anioFabricacion,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("precioAlquiler") double precioAlquiler,
            @RequestParam(value = "files", required = false) MultipartFile[] files
    ) {
        ResponseEntity<MotorHomeDto> respuesta;
        try {
            MotorHome motorHome = new MotorHome();
            motorHome.setMarca(marca);
            motorHome.setModelo(modelo);
            motorHome.setAnioFabricacion(anioFabricacion);
            motorHome.setDescripcion(descripcion);
            motorHome.setPrecioAlquiler(precioAlquiler);

            if (files != null && files.length > 0) {
                Path directorioImagenes = Paths.get("src//main//resources//static//frontend//public/images");
                String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();

                List<String> nombresArchivos = new ArrayList<>();

                for (MultipartFile file : files) {
                    byte[] bytesImg = file.getBytes();
                    //String nombreArchivo = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                    Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + file.getOriginalFilename());
                    Files.write(rutaCompleta, bytesImg);
                    nombresArchivos.add(file.getOriginalFilename());
                }


                // Aquí puedes manejar la lógica para guardar múltiples nombres de archivos en tu entidad
                String archivosConcatenados = String.join("-", nombresArchivos);
                motorHome.setFile(archivosConcatenados);
            } else {
                motorHome.setFile("");
            }

            MotorHomeDto motorhomeRegistrada = IMotorHomeService.registrarMotorHome(motorHome, files);

            if (motorhomeRegistrada != null) {
                logger.info("Categoria registrada exitosamente: {}", motorhomeRegistrada);
                respuesta = new ResponseEntity<>(motorhomeRegistrada, null, HttpStatus.CREATED);
            } else {
                logger.error("No se pudo registrar la Categoria");
                respuesta = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            logger.error("Error al registrar Categoria", e);
            respuesta = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return respuesta;
    }

}
