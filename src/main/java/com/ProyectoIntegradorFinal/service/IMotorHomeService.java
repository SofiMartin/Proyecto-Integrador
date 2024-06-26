package com.ProyectoIntegradorFinal.service;

import com.ProyectoIntegradorFinal.dto.MotorHomeDto;
import com.ProyectoIntegradorFinal.entity.MotorHome;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IMotorHomeService {

    List<MotorHomeDto> listarMotorHome();
    //List<MotorHomeDto> listarInternosFiltro(String modelo);

    MotorHomeDto buscarMotorHomePorId(Long id);

    MotorHomeDto registrarMotorHome(MotorHome motorHome, MultipartFile[] file);

    MotorHomeDto actualizarMotorHome(MotorHome motorHome);

    void eliminarMotorHome(Long id);
}
