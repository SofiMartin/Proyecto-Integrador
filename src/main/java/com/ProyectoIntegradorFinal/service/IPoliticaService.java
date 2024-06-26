package com.ProyectoIntegradorFinal.service;

import com.ProyectoIntegradorFinal.dto.PoliticaDto;
import com.ProyectoIntegradorFinal.entity.Politica;

import java.util.List;

public interface IPoliticaService {

    List<PoliticaDto> listarPolitica();

    PoliticaDto buscarPoliticaPorId(Long id);

    Politica buscarPoliticaPorNombre(String nombre);

    PoliticaDto registrarPolitica(Politica politica);

    PoliticaDto actualizarPolitica(Politica politica);

    void eliminarPolitica(Long id);
}
