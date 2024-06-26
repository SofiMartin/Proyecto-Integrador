package com.ProyectoIntegradorFinal.service.imp;

import com.ProyectoIntegradorFinal.dto.ImagenesDto;
import com.ProyectoIntegradorFinal.entity.Imagenes;
import com.ProyectoIntegradorFinal.entity.Producto;
import com.ProyectoIntegradorFinal.repository.ImagenesRepository;
import com.ProyectoIntegradorFinal.service.IImagenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ImagenService implements IImagenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductoService.class);
    private final ObjectMapper objectMapper;
    private final ImagenesRepository imagenesRepository;

    @Autowired
    public ImagenService(ObjectMapper objectMapper, ImagenesRepository imagenesRepository) {
        this.objectMapper = objectMapper;
        this.imagenesRepository = imagenesRepository;
    }

    @Override
    public ImagenesDto agregarImagen(Imagenes imagenes) {
        Imagenes imagenesReg = imagenesRepository.save(imagenes);


        LOGGER.info("MotorHome registrado: {}", imagenesReg);
        return objectMapper.convertValue(imagenesReg, ImagenesDto.class);
    }

    @Override
    public List <ImagenesDto> listarImagenes(Producto producto) {
        // Verificar si la fiscalía es nula o vacía
        if (producto == null ) {
            LOGGER.warn("Nombre no válido. No se realizará la busqueda.");
        }

        // Realizar la búsqueda de internos por fiscalía en el repositorio
        List<Imagenes> imagenes = imagenesRepository.findByProducto(producto);

        // Convertir la lista de entidades en una lista de DTO
        List<ImagenesDto> imagenesDtos = imagenes.stream()
                .map(imagen -> objectMapper.convertValue(imagen, ImagenesDto.class))
                .toList();

        LOGGER.info("Listado de imagenes filtrados por producto '{}'", imagenesDtos);

        return imagenesDtos;
    }


}
