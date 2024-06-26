package com.ProyectoIntegradorFinal.controller;

import com.ProyectoIntegradorFinal.dto.PoliticaDto;
import com.ProyectoIntegradorFinal.entity.Politica;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/politica")
public class PoliticaController {
    private static final Logger logger = LoggerFactory.getLogger(PoliticaController.class);
    private final com.ProyectoIntegradorFinal.service.IPoliticaService IPoliticaService;
    private final ObjectMapper objectMapper;
    @Autowired
    public PoliticaController(com.ProyectoIntegradorFinal.service.IPoliticaService iPoliticaService, ObjectMapper objectMapper) {
        IPoliticaService = iPoliticaService;
        this.objectMapper = objectMapper;
    }
    @GetMapping()
    public List<PoliticaDto> listarTodos(){
        return IPoliticaService.listarPolitica();
    }


    @PostMapping("/registrar")
    public ResponseEntity<PoliticaDto> registrar(@RequestParam("nombre") String nombre,
                                                 @RequestParam("descripcion") String descripcion) {
        ResponseEntity<PoliticaDto> respuesta;
        try {
            Politica politica = new Politica();
            politica.setNombre(nombre);
            politica.setDescripcion(descripcion);

            PoliticaDto politicaRegistrada = IPoliticaService.registrarPolitica(politica);

            if (politicaRegistrada != null) {
                logger.info("Politica registrada exitosamente: {}", politicaRegistrada);
                respuesta = new ResponseEntity<>(politicaRegistrada, null, HttpStatus.CREATED);
            } else {
                logger.error("No se pudo registrar la Politica");
                respuesta = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            logger.error("Error al registrar Politica", e);
            respuesta = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return respuesta;
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<PoliticaDto> buscarPorId(@PathVariable Long id){
        ResponseEntity<PoliticaDto> respuesta;
        PoliticaDto politicaDto = IPoliticaService.buscarPoliticaPorId(id);
        if(politicaDto != null) respuesta = new ResponseEntity<>(politicaDto, null, HttpStatus.OK);
        else respuesta = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return respuesta;
    }
    @DeleteMapping("/eliminar/{id}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        IPoliticaService.eliminarPolitica(id);
        return ResponseEntity.ok("Politica Eliminada");
    }

    // AGREGO PUT PARA ACTUALIZAR Politica

    @PutMapping("/actualizar")
    public ResponseEntity<PoliticaDto> actualizarPolitica(@RequestParam("id") Long id, @RequestParam("nombre") String nombre,
                                                          @RequestParam("descripcion") String descripcion) {
        ResponseEntity<PoliticaDto> respuesta;
        try {
            Politica politicaActualizar = new Politica();
            politicaActualizar.setId(id);
            politicaActualizar.setNombre(nombre);
            politicaActualizar.setDescripcion(descripcion);

           // Llamar al servicio con la instancia de  politica
            PoliticaDto politicaActualizada = IPoliticaService.actualizarPolitica(politicaActualizar);

            if (politicaActualizada != null) {
                logger.info("Politica actualizada correctamente: {}", politicaActualizada);
                respuesta = new ResponseEntity<>(politicaActualizada, null, HttpStatus.OK);
            } else {
                logger.error("No se pudo actualizar la Politica");
                respuesta = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            logger.error("Error al actualizar la Politica", e);
            respuesta = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return respuesta;
    }
}
