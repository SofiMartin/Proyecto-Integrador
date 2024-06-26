package com.ProyectoIntegradorFinal.service;

import com.ProyectoIntegradorFinal.dto.ReservaDto;
import com.ProyectoIntegradorFinal.entity.Reserva;

import java.time.LocalDate;
import java.util.List;

public interface IReservaService {
    List<ReservaDto> listarReserva();

    ReservaDto buscarReservaPorId(Long id);

    ReservaDto registrarReserva(Reserva reserva);

    ReservaDto actualizarReserva(Reserva reserva);

    void eliminarReserva(Long id);

    List<Reserva> buscarPorProductoId(Long productoId);

    List<Long> buscarPorCategoriaYFechas(Long categoriaId, LocalDate fechaInicio, LocalDate fechaFin);

//-----AGREGO PARA BUSCAR FECHAS OCUPADAS POR ID
    List<LocalDate> buscarFechasOcupadasPorProductoYFechas(Long productoId, LocalDate fechaInicio, LocalDate fechaFin);
}
