package com.ProyectoIntegradorFinal.service.imp;

import com.ProyectoIntegradorFinal.dto.MotorHomeDto;
import com.ProyectoIntegradorFinal.dto.ReservaDto;
import com.ProyectoIntegradorFinal.entity.MotorHome;
import com.ProyectoIntegradorFinal.entity.Reserva;
import com.ProyectoIntegradorFinal.repository.ReservaRepository;
import com.ProyectoIntegradorFinal.service.IReservaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaService implements IReservaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservaService.class);
    private final ObjectMapper objectMapper;
    private final ReservaRepository reservaRepository;

    @Autowired
    public ReservaService(ObjectMapper objectMapper, ReservaRepository reservaRepository) {
        this.objectMapper = objectMapper;
        this.reservaRepository = reservaRepository;
    }

    @Override
    public List<ReservaDto> listarReserva() {
        List<Reserva> reservas = reservaRepository.findAll();
        List<ReservaDto> reservaDtos = reservas.stream()
                .map(reserva -> objectMapper.convertValue(reserva, ReservaDto.class)).toList();
        LOGGER.info("Listado de todas las reservas: {}", reservaDtos);
        return reservaDtos;
    }

    @Override
    public ReservaDto buscarReservaPorId(Long id) {
        Reserva reserva = reservaRepository.findById(id).orElse(null);
        ReservaDto reservaDto= null;
        if(reserva!= null){
            reservaDto = objectMapper.convertValue(reserva , ReservaDto.class);
            LOGGER.info("Reserva encontrada: {}", reservaDto);
        }else{
            LOGGER.info("El id no se encuentra registrado en la base de datos");
        }
        return reservaDto;
    }

    @Override
    public ReservaDto registrarReserva(Reserva reserva) {
        Reserva reservaReg= reservaRepository.save(reserva);
        LOGGER.info("Reserva registrada: {}",reservaReg);
        return objectMapper.convertValue(reservaReg, ReservaDto.class);
    }

    @Override
    public ReservaDto actualizarReserva(Reserva reserva) {
        Reserva reservaEdit = reservaRepository.findById(reserva.getId()).orElse(null);
        ReservaDto reservaDto = null;
        if(reservaEdit != null){
            reservaEdit = reserva;
            reservaRepository.save(reservaEdit);
            reservaDto = objectMapper.convertValue(reservaEdit, ReservaDto.class);
            LOGGER.warn("Reserva actualizada: {}",reservaDto);
        }else{
            LOGGER.error("No fue posible actualizar los datos ya que la Reserva no se encuentra registrado");
        }
        return reservaDto;
    }

    @Override
    public void eliminarReserva(Long id) {
        if(buscarReservaPorId(id) != null){
            ReservaDto reservaDto = buscarReservaPorId(id);
            reservaRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado la Reserva con id: {}", reservaDto);
        }else{
            LOGGER.error("No se ha encontrado la Reserva con id " + id);
        }
    }

    @Override
    public List<Long> buscarPorCategoriaYFechas(Long categoriaId, LocalDate fechaInicio, LocalDate fechaFin) {
        return reservaRepository.findByCategoriaAndFechasDisponibles(categoriaId, fechaInicio, fechaFin);
    }

    @Override
    public List<LocalDate> buscarFechasOcupadasPorProductoYFechas(Long productoId, LocalDate fechaInicio, LocalDate fechaFin) {
        return reservaRepository.findFechasOcupadasByProductoAndFechasDisponibles(productoId, fechaInicio, fechaFin);
    }

    @Override
    public List<Reserva> buscarPorProductoId(Long productoId) {
        LOGGER.info("Listado de todas las reservas por Producto");
        return reservaRepository.findByProductoId(productoId);
    }
}
