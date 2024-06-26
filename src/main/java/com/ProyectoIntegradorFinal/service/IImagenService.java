package com.ProyectoIntegradorFinal.service;

import com.ProyectoIntegradorFinal.dto.ImagenesDto;
import com.ProyectoIntegradorFinal.entity.Imagenes;
import com.ProyectoIntegradorFinal.entity.Producto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImagenService {
    ImagenesDto agregarImagen(Imagenes imagenes);
    List <ImagenesDto> listarImagenes(Producto producto);
}
