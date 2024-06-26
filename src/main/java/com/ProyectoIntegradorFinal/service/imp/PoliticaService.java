package com.ProyectoIntegradorFinal.service.imp;

import com.ProyectoIntegradorFinal.dto.PoliticaDto;
import com.ProyectoIntegradorFinal.entity.Politica;
import com.ProyectoIntegradorFinal.repository.PoliticaRepository;
import com.ProyectoIntegradorFinal.service.IPoliticaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoliticaService implements com.ProyectoIntegradorFinal.service.IPoliticaService {
        private static final Logger LOGGER = LoggerFactory.getLogger(PoliticaService.class);
        private final ObjectMapper objectMapper;
        private final PoliticaRepository politicaRepository;

        @Autowired
        public PoliticaService(ObjectMapper objectMapper, PoliticaRepository politicaRepository) {
            this.objectMapper = objectMapper;
            this.politicaRepository = politicaRepository;
        }

        @Override
        public List<PoliticaDto> listarPolitica() {
            List<Politica> politicas = politicaRepository.findAll();
            List<PoliticaDto> politicaDtos = politicas.stream()
                    .map(politica -> objectMapper.convertValue(politica, PoliticaDto.class)).toList();
            LOGGER.info("Listado de todas las Politicas: {}",politicaDtos);
            return politicaDtos;
        }

        @Override
        public PoliticaDto buscarPoliticaPorId(Long id) {
            Politica politica = politicaRepository.findById(id).orElse(null);
            PoliticaDto politicaDto = null;
            if(politica != null){
                politicaDto = objectMapper.convertValue(politica, PoliticaDto.class);
                LOGGER.info("Politica encontrada: {}", politicaDto);
            }else{
                LOGGER.info("El id no se encuentra registrado en la base de datos");
            }
            return politicaDto;
        }
        @Override
        public Politica buscarPoliticaPorNombre(String nombre) {
            Politica politica = politicaRepository.findByNombre(nombre);
            if(politica!= null){
                LOGGER.info("Política encontrada: {}", politica);
            }else{
                LOGGER.info("El nombre no se encuentra registrado en la base de datos");
            }
            return politica;
        }
        @Override
        public PoliticaDto registrarPolitica(Politica politica) {

            Politica politicaReg = politicaRepository.save(politica);

            LOGGER.info("Politica registrada: {}", politicaReg);
            return objectMapper.convertValue(politicaReg, PoliticaDto.class);
        }
        @Override
        public PoliticaDto actualizarPolitica(Politica politica) {
            //Politica politicaNom = politicaRepository.findByNombre(politica.getNombre());
            Politica politicaEdit = politicaRepository.findById(politica.getId()).orElse(null);

            PoliticaDto politicaDto = null;
            if(politicaEdit != null){
               politicaEdit.setNombre(politica.getNombre());
               politicaEdit.setDescripcion(politica.getDescripcion());
               politicaRepository.save(politicaEdit);
               politicaDto = objectMapper.convertValue(politicaEdit, PoliticaDto.class);
               LOGGER.warn("Politica actualizada: {}", politicaDto);
            }else{
                LOGGER.error("No fue posible actualizar los datos ya que la Politica no se encuentra registrada");
            }
            return politicaDto;
        }

        @Override
        public void eliminarPolitica(Long id) {
            if(buscarPoliticaPorId(id) != null){
                PoliticaDto politicaDto = buscarPoliticaPorId(id);
                politicaRepository.deleteById(id);
                LOGGER.warn("Se ha eliminado la Politica con id: {}", politicaDto);
            }else{
                LOGGER.error("No se ha encontrado la Politica con id " + id);
            }
        }
    }


