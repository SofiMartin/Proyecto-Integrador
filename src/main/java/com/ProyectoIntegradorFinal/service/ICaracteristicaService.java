package com.ProyectoIntegradorFinal.service;

import com.ProyectoIntegradorFinal.dto.CaracteristicaDto;
import com.ProyectoIntegradorFinal.entity.Caracteristica;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICaracteristicaService {

    List<CaracteristicaDto> listarCaracteristica();

    CaracteristicaDto buscarCaracteristicaPorId(Long id);

    Caracteristica buscarCaracteristicaPorDescripcion(String descripcion);

    CaracteristicaDto registrarCaracteristica(Caracteristica caracteristica, MultipartFile imagen);

    CaracteristicaDto actualizarCaracteristica(Caracteristica caracteristica, MultipartFile file);

    void eliminarCaracteristica(Long id);
}
