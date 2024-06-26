package com.ProyectoIntegradorFinal.service.imp;

import com.ProyectoIntegradorFinal.dto.MotorHomeDto;
import com.ProyectoIntegradorFinal.entity.MotorHome;
import com.ProyectoIntegradorFinal.repository.MotorHomeRepository;
import com.ProyectoIntegradorFinal.service.IMotorHomeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class MotorHomeService implements IMotorHomeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MotorHomeService.class);
    private final ObjectMapper objectMapper;
    private final MotorHomeRepository motorHomeRepository;

    @Autowired
    public MotorHomeService(ObjectMapper objectMapper, MotorHomeRepository motorHomeRepository) {
        this.objectMapper = objectMapper;
        this.motorHomeRepository = motorHomeRepository;
    }


    @Override
    public List<MotorHomeDto> listarMotorHome() {
        List<MotorHome> motorHomes = motorHomeRepository.findAll();
        List<MotorHomeDto> motorHomesDtos = motorHomes.stream()
                .map(interno -> objectMapper.convertValue(motorHomes, MotorHomeDto.class)).toList();
        LOGGER.info("Listado de todos los motorhomes: {}", motorHomesDtos);
        return motorHomesDtos;
    }

    @Override
    public MotorHomeDto buscarMotorHomePorId(Long id) {
        MotorHome motorHome = motorHomeRepository.findById(id).orElse(null);
        MotorHomeDto motorHomeDto= null;
        if(motorHome!= null){
            motorHomeDto = objectMapper.convertValue(motorHome , MotorHomeDto.class);
            LOGGER.info("MotorHome encontrado: {}", motorHomeDto);
        }else{
            LOGGER.info("El id no se encuentra registrado en la base de datos");
        }
        return motorHomeDto;
    }

    @Override
    public MotorHomeDto registrarMotorHome(MotorHome motorHome, MultipartFile[] file) {
        MotorHome motorHomeReg= motorHomeRepository.save(motorHome);
        LOGGER.info("MotorHome registrado: {}",motorHomeReg);
        return objectMapper.convertValue(motorHomeReg, MotorHomeDto.class);
    }

    @Override
    public MotorHomeDto actualizarMotorHome(MotorHome motorHome) {
        MotorHome motorHomeEdit = motorHomeRepository.findById(motorHome.getId()).orElse(null);
        MotorHomeDto motorHomeDto = null;
        if(motorHomeEdit != null){
            motorHomeEdit = motorHome;
            motorHomeRepository.save(motorHomeEdit);
            motorHomeDto = objectMapper.convertValue(motorHomeEdit, MotorHomeDto.class);
            LOGGER.warn("MotorHome actualizado: {}",motorHomeDto);
        }else{
            LOGGER.error("No fue posible actualizar los datos ya que el MotorHome no se encuentra registrado");
        }
        return motorHomeDto;
    }

    @Override
    public void eliminarMotorHome(Long id) {
        if(buscarMotorHomePorId(id) != null){
            MotorHomeDto motorHomeDto = buscarMotorHomePorId(id);
            motorHomeRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el MotorHome con id: {}", motorHomeDto);
        }else{
            LOGGER.error("No se ha encontrado el MotorHome con id " + id);
        }
    }
}
