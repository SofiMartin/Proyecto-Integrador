package com.ProyectoIntegradorFinal.controller;

import com.ProyectoIntegradorFinal.dto.CaracteristicaDto;
import com.ProyectoIntegradorFinal.entity.Caracteristica;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/caracteristica")
public class CaracteristicaController {
    private static final Logger logger = LoggerFactory.getLogger(CaracteristicaController.class);
    private final com.ProyectoIntegradorFinal.service.ICaracteristicaService ICaracteristicaService;
    private final ObjectMapper objectMapper;
    @Autowired
    public CaracteristicaController(com.ProyectoIntegradorFinal.service.ICaracteristicaService iCaracteristicaService, ObjectMapper objectMapper) {
        ICaracteristicaService = iCaracteristicaService;
        this.objectMapper = objectMapper;
    }
    @GetMapping()
    public List<CaracteristicaDto> listarTodos(){
        return ICaracteristicaService.listarCaracteristica();
    }


    @PostMapping("/registrar")
    public ResponseEntity<CaracteristicaDto> registrar(@RequestParam("descripcion") String descripcion,
                                                       @RequestParam(value= "file" , required = false) MultipartFile file) {
        ResponseEntity<CaracteristicaDto> respuesta;
        try {
            Caracteristica caracteristica = new Caracteristica();
            caracteristica.setDescripcion(descripcion);
            if(file != null){
                Path directorioImagenes = Paths.get("src//main//resources//static//frontend//public/images");

                String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();

                byte [] bytesImg = file.getBytes();
                Path rutaCompleta= Paths.get(rutaAbsoluta + "//" + file.getOriginalFilename());
                Files.write(rutaCompleta, bytesImg);
                caracteristica.setFile(file.getOriginalFilename());
            }
            else {
                caracteristica.setFile(null);
            }
            CaracteristicaDto caracteristicaRegistrada = ICaracteristicaService.registrarCaracteristica(caracteristica, file);

            if (caracteristicaRegistrada != null) {
                logger.info("Caracteristica registrada exitosamente: {}", caracteristicaRegistrada);
                respuesta = new ResponseEntity<>(caracteristicaRegistrada, null, HttpStatus.CREATED);
            } else {
                logger.error("No se pudo registrar la Caracteristica");
                respuesta = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            logger.error("Error al registrar Caracteristica", e);
            respuesta = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return respuesta;
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<CaracteristicaDto> buscarPorId(@PathVariable Long id){
        ResponseEntity<CaracteristicaDto> respuesta;
        CaracteristicaDto caracteristicaDto = ICaracteristicaService.buscarCaracteristicaPorId(id);
        if(caracteristicaDto != null) respuesta = new ResponseEntity<>(caracteristicaDto, null, HttpStatus.OK);
        else respuesta = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return respuesta;
    }
    @DeleteMapping("/eliminar/{id}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        ICaracteristicaService.eliminarCaracteristica(id);
        return ResponseEntity.ok("Caracteristica Eliminada");
    }

    // AGREGO PUT PARA ACTUALIZAR CARACTERISTICA

    @PutMapping("/actualizar")
    public ResponseEntity<CaracteristicaDto> actualizarCaracteristica(@RequestParam("id") Long id, @RequestParam("descripcion") String descripcion,
                                                                      @RequestParam(value = "file", required = false) MultipartFile file) {
        ResponseEntity<CaracteristicaDto> respuesta;
        try {
            Caracteristica caracteristicaActualizar = new Caracteristica();
            caracteristicaActualizar.setId(id);
            caracteristicaActualizar.setDescripcion(descripcion);

            if(file != null){
                Path directorioImagenes = Paths.get("src//main//resources//static//frontend//public/images");

                String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
                byte [] bytesImg = file.getBytes();
                Path rutaCompleta= Paths.get(rutaAbsoluta + "//" + file.getOriginalFilename());
                Files.write(rutaCompleta, bytesImg);
                caracteristicaActualizar.setFile(file.getOriginalFilename());
            }
            else {
                caracteristicaActualizar.setFile(null);
            }
            // Llamar al servicio con la instancia de Caracteristica
            CaracteristicaDto caracteristicaActualizada = ICaracteristicaService.actualizarCaracteristica(caracteristicaActualizar, file);

            if (caracteristicaActualizada != null) {
                logger.info("Caracteristica actualizada correctamente: {}", caracteristicaActualizada);
                respuesta = new ResponseEntity<>(caracteristicaActualizada, null, HttpStatus.OK);
            } else {
                logger.error("No se pudo actualizar la Caracteristica");
                respuesta = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            logger.error("Error al actualizar la Caracteristica", e);
            respuesta = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return respuesta;
    }
}


