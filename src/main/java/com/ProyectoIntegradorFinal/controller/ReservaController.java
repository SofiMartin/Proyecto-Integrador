package com.ProyectoIntegradorFinal.controller;

import com.ProyectoIntegradorFinal.dto.ReservaDto;
import com.ProyectoIntegradorFinal.entity.Reserva;
import com.ProyectoIntegradorFinal.service.IReservaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/reserva")
public class ReservaController {

    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);
    private final IReservaService IReservaService;

    @Autowired
    public ReservaController(com.ProyectoIntegradorFinal.service.IReservaService IReservaService) {
        this.IReservaService = IReservaService;
    }


    @PostMapping("/registrar")
    public ResponseEntity<ReservaDto> registrarReserva(@RequestBody Reserva reserva){
        ResponseEntity<ReservaDto> respuesta;
        ReservaDto reservaDto = IReservaService.registrarReserva(reserva);
        if(reservaDto != null) respuesta = new ResponseEntity<>(reservaDto, null, HttpStatus.CREATED);
        else respuesta = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return respuesta;
    }


    @PutMapping("/actualizar")
    public ResponseEntity<ReservaDto> actualizarReserva(@RequestBody Reserva reserva){
        ResponseEntity<ReservaDto> respuesta;
        ReservaDto reservaDto = IReservaService.actualizarReserva(reserva);
        if(reservaDto != null) respuesta = new ResponseEntity<>(reservaDto, null, HttpStatus.OK);
        else respuesta = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return respuesta;
    }

    //GET
    @GetMapping
    public List<ReservaDto> listarTodos() {
        return IReservaService.listarReserva();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaDto> buscarPorId(@PathVariable Long id){
        ResponseEntity<ReservaDto> respuesta;
        ReservaDto reservaDto = IReservaService.buscarReservaPorId(id);
        if(reservaDto != null) respuesta = new ResponseEntity<>(reservaDto, null, HttpStatus.OK);
        else respuesta = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return respuesta;
    }


    //DELETE
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarReserva(@PathVariable Long id) {
        IReservaService.eliminarReserva(id);
        return ResponseEntity.ok("Reserva Eliminada");
    }

    @GetMapping("/buscarPorCategoriaYFechas")
    public ResponseEntity<List<Long>> buscarPorCategoriaYFechas(
            @RequestParam Long categoriaId,
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin
    ) {
        try {
            LocalDate fechaIni = LocalDate.parse(fechaInicio);
            LocalDate fechaFinal = LocalDate.parse(fechaFin);

            List<Long> productosDisponibles = IReservaService.buscarPorCategoriaYFechas(categoriaId, fechaIni, fechaFinal);
            return new ResponseEntity<>(productosDisponibles, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
//-----AGREGO FECHAS OCUPADAS
    @GetMapping("/fechasOcupadasPorProducto")
    public ResponseEntity<List<LocalDate>> buscarFechasOcupadasPorProducto(
            @RequestParam Long productoId,
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin
    ) {
        try {
            LocalDate fechaIni = LocalDate.parse(fechaInicio);
            LocalDate fechaFinal = LocalDate.parse(fechaFin);

            List<LocalDate> fechasOcupadas = IReservaService.buscarFechasOcupadasPorProductoYFechas(productoId, fechaIni, fechaFinal);
            return new ResponseEntity<>(fechasOcupadas, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/reservaPorProducto/{productoId}")
    public ResponseEntity<List<Reserva>> buscarReservaPorProducto(@PathVariable Long productoId) {
        try {

            List<Reserva> fechasOcupadas = IReservaService.buscarPorProductoId(productoId);
            return new ResponseEntity<>(fechasOcupadas, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}

