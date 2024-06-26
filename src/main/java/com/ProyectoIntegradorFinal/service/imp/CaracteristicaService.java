package com.ProyectoIntegradorFinal.service.imp;

import com.ProyectoIntegradorFinal.dto.CaracteristicaDto;
import com.ProyectoIntegradorFinal.entity.Caracteristica;
import com.ProyectoIntegradorFinal.repository.CaracteristicaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class CaracteristicaService implements com.ProyectoIntegradorFinal.service.ICaracteristicaService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CaracteristicaService.class);
    private final ObjectMapper objectMapper;
    private final CaracteristicaRepository caracteristicaRepository;

    @Autowired
    public CaracteristicaService(ObjectMapper objectMapper, CaracteristicaRepository caracteristicaRepository) {
        this.objectMapper = objectMapper;
        this.caracteristicaRepository = caracteristicaRepository;
    }

    @Override
    public List<CaracteristicaDto> listarCaracteristica() {
        List<Caracteristica> caracteristicas = caracteristicaRepository.findAll();
        List<CaracteristicaDto> caracteristicaDtos = caracteristicas.stream()
                .map(caracteristica -> objectMapper.convertValue(caracteristica, CaracteristicaDto.class)).toList();
        LOGGER.info("Listado de todas las caracteristica: {}",caracteristicaDtos);
        return caracteristicaDtos;
    }

    @Override
    public CaracteristicaDto buscarCaracteristicaPorId(Long id) {
        Caracteristica caracteristica = caracteristicaRepository.findById(id).orElse(null);
        CaracteristicaDto caracteristicaDto = null;
        if(caracteristica != null){
            caracteristicaDto = objectMapper.convertValue(caracteristica, CaracteristicaDto.class);
            LOGGER.info("Caracteristica encontrada: {}", caracteristicaDto);
        }else{
            LOGGER.info("El id no se encuentra registrado en la base de datos");
        }
        return caracteristicaDto;
    }
    @Override
    public Caracteristica buscarCaracteristicaPorDescripcion(String descripcion) {
        Caracteristica caracteristica = caracteristicaRepository.findByDescripcion(descripcion);
        if(caracteristica!= null){
            LOGGER.info("Caracteristica encontrada: {}", caracteristica);
        }else{
            LOGGER.info("La descripcion no se encuentra registrada en la base de datos");
        }
        return caracteristica;
    }
    @Override
    public CaracteristicaDto registrarCaracteristica(Caracteristica caracteristica, MultipartFile imagen) {

        Caracteristica caracteristicaReg = caracteristicaRepository.save(caracteristica);

        LOGGER.info("Caracteristica registrada: {}", caracteristicaReg);
        return objectMapper.convertValue(caracteristicaReg, CaracteristicaDto.class);
    }

    @Override
    public CaracteristicaDto actualizarCaracteristica(Caracteristica caracteristica, MultipartFile file) {
        //Caracteristica caracteristicaDesc = caracteristicaRepository.findByDescripcion(caracteristica.getDescripcion());
        Caracteristica caracteristicaEdit = caracteristicaRepository.findById(caracteristica.getId()).orElse(null);

        CaracteristicaDto caracteristicaDto = null;
        if(caracteristicaEdit != null){
            caracteristicaEdit.setDescripcion(caracteristica.getDescripcion());
            caracteristicaEdit.setFile(caracteristica.getFile());
            caracteristicaRepository.save(caracteristicaEdit);
            caracteristicaDto = objectMapper.convertValue(caracteristicaEdit, CaracteristicaDto.class);
            LOGGER.warn("Caracteristica actualizada: {}", caracteristicaDto);
        }else{
            LOGGER.error("No fue posible actualizar los datos ya que la Caracteristica no se encuentra registrada");
        }
        return caracteristicaDto;
    }

    @Override
    public void eliminarCaracteristica(Long id) {
        if(buscarCaracteristicaPorId(id) != null){
            CaracteristicaDto caracteristicaDto = buscarCaracteristicaPorId(id);
            caracteristicaRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado la Caracteristica con id: {}", caracteristicaDto);
        }else{
            LOGGER.error("No se ha encontrado la Caracteristica con id " + id);
        }
    }
}
