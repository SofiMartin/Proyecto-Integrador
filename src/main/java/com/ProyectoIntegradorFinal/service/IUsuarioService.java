package com.ProyectoIntegradorFinal.service;

import com.ProyectoIntegradorFinal.dto.ProductoDto;
import com.ProyectoIntegradorFinal.dto.UsuarioDto;

import java.util.List;

public interface IUsuarioService {
    List<UsuarioDto> listarUsuario();
}
