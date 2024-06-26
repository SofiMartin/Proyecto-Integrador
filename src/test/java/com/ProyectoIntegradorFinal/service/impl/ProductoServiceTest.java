package com.ProyectoIntegradorFinal.service.impl;

import com.ProyectoIntegradorFinal.dto.ProductoDto;
import com.ProyectoIntegradorFinal.entity.Caracteristica;
import com.ProyectoIntegradorFinal.entity.Categoria;
import com.ProyectoIntegradorFinal.entity.Producto;
import com.ProyectoIntegradorFinal.repository.CaracteristicaRepository;
import com.ProyectoIntegradorFinal.repository.CategoriaRepository;
import com.ProyectoIntegradorFinal.repository.ProductoRepository;
import com.ProyectoIntegradorFinal.service.imp.ProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

        import java.util.ArrayList;
        import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private CaracteristicaRepository caracteristicaRepository;

    @InjectMocks
    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarMotorHome_ProductoNuevo_RegistroExitoso() {
        // Configuración del test
        ObjectMapper objectMapper = new ObjectMapper(); // Crear un ObjectMapper

        Producto producto = new Producto();
        producto.setNombre("MotorHome");
        producto.setMarca("Mercedes");
        producto.setModelo("Sprinter");
        producto.setAnioFabricacion(2020);
        producto.setPrecioAlquiler(100);

        Categoria categoriaClasica = new Categoria("clásica");
        Categoria categoriaFamiliar = new Categoria("familiar");

        List<Categoria> categorias = new ArrayList<>();
        categorias.add(categoriaClasica);
        categorias.add(categoriaFamiliar);

        producto.setCategorias(categorias);

        Caracteristica caracteristicaTele = new Caracteristica("TV");
        Caracteristica caracteristicaWifi = new Caracteristica("Wifi");

        List<Caracteristica> caracteristicas = new ArrayList<>();
        caracteristicas.add(caracteristicaTele);
        caracteristicas.add(caracteristicaWifi);

        producto.setCaracteristicas(caracteristicas);

        // Mock de los repositorios y del servicio
        ProductoRepository productoRepository = mock(ProductoRepository.class);
        CategoriaRepository categoriaRepository = mock(CategoriaRepository.class);
        CaracteristicaRepository caracteristicaRepository = mock(CaracteristicaRepository.class);

        // Crear una instancia del ProductoService con los mocks
        ProductoService productoService = new ProductoService(objectMapper, productoRepository, categoriaRepository, caracteristicaRepository);

        // Simular comportamiento de los repositorios para obtener categorías y características
        when(categoriaRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(caracteristicaRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Simular comportamiento de los repositorios
        when(productoRepository.save(any())).thenReturn(producto);

        // Ejecutar el método a probar
        ProductoDto productoDto = productoService.registrarMotorHome(producto);

        // Verificar que se guarden correctamente las categorías y características
        verify(categoriaRepository, times(1)).save(argThat(categoria -> categoria.equals(categoriaClasica)));
        verify(categoriaRepository, times(1)).save(argThat(categoria -> categoria.equals(categoriaFamiliar)));
        verify(caracteristicaRepository, times(1)).save(argThat(caracteristica -> caracteristica.equals(caracteristicaTele)));
        verify(caracteristicaRepository, times(1)).save(argThat(caracteristica -> caracteristica.equals(caracteristicaWifi)));

// Verificar que se guarde el producto en el repositorio de productos
        verify(productoRepository, times(1)).save(argThat(productoGuardado -> productoGuardado.equals(producto)));

    }

    @Test
    void listarMotorHome_DeberiaRetornarListaProductos() {
        ObjectMapper objectMapper = new ObjectMapper(); // Crear un ObjectMapper
        // Datos de prueba
        Producto producto1 = new Producto(/* ... */);
        Producto producto2 = new Producto(/* ... */);
        List<Producto> listaProductos = List.of(producto1, producto2);

        // Mock del repositorio
        ProductoRepository productoRepository = mock(ProductoRepository.class);
        when(productoRepository.findAll()).thenReturn(listaProductos);

        // Instancia del servicio con el mock del repositorio
        ProductoService productoService = new ProductoService(objectMapper, productoRepository, categoriaRepository, caracteristicaRepository);

        // Ejecutar el método a probar
        List<ProductoDto> resultado = productoService.listarMotorHome();

        // Verificaciones
        assertEquals(listaProductos.size(), resultado.size()); // Verificar que el tamaño sea el mismo

        // Verificar que se llamó al método findAll del repositorio
        verify(productoRepository, times(1)).findAll();

        // Podrías hacer más verificaciones según el escenario específico de tu aplicación
    }

    @Test
    void buscarMotorHomePorId_ProductoEncontrado() {
        ObjectMapper objectMapper = new ObjectMapper(); // Crear un ObjectMapper
        // Datos de prueba
        Long idBuscado = 1L;
        Producto producto = new Producto();
        producto.setNombre("MotorHome");
        producto.setMarca("Mercedes");
        producto.setModelo("Sprinter");
        producto.setAnioFabricacion(2020);
        producto.setPrecioAlquiler(100);

        Categoria categoriaClasica = new Categoria("clásica");
        Categoria categoriaFamiliar = new Categoria("familiar");

        List<Categoria> categorias = new ArrayList<>();
        categorias.add(categoriaClasica);
        categorias.add(categoriaFamiliar);

        producto.setCategorias(categorias);

        Caracteristica caracteristicaTele = new Caracteristica("TV");
        Caracteristica caracteristicaWifi = new Caracteristica("Wifi");

        List<Caracteristica> caracteristicas = new ArrayList<>();
        caracteristicas.add(caracteristicaTele);
        caracteristicas.add(caracteristicaWifi);

        producto.setCaracteristicas(caracteristicas);

        // Mock del repositorio
        ProductoRepository productoRepository = mock(ProductoRepository.class);
        when(productoRepository.findById(idBuscado)).thenReturn(Optional.of(producto));

        // Instancia del servicio con el mock del repositorio
        ProductoService productoService = new ProductoService(objectMapper, productoRepository, categoriaRepository, caracteristicaRepository);

        // Ejecutar el método a probar
        ProductoDto productoDto = productoService.buscarMotorHomePorId(idBuscado);

        // Verificaciones
        assertNotNull(productoDto); // Verificar que el productoDto no sea nulo
        // Agregar más verificaciones según el comportamiento esperado al encontrar el producto en la base de datos
        // Por ejemplo, podrías verificar atributos específicos del productoDto
        verify(productoRepository, times(1)).findById(idBuscado); // Verificar que se llamó al método findById del repositorio
        // Podrías verificar el log en función de cómo manejas los logs en tu aplicación
    }

    @Test
    void buscarMotorHomePorId_ProductoNoEncontrado() {
        ObjectMapper objectMapper = new ObjectMapper(); // Crear un ObjectMapper
        // Datos de prueba
        Long idBuscado = 2L;

        // Mock del repositorio que devuelve un Optional vacío
        ProductoRepository productoRepository = mock(ProductoRepository.class);
        when(productoRepository.findById(idBuscado)).thenReturn(Optional.empty());

        // Instancia del servicio con el mock del repositorio
        ProductoService productoService = new ProductoService(objectMapper, productoRepository,categoriaRepository, caracteristicaRepository);

        // Ejecutar el método a probar
        ProductoDto productoDto = productoService.buscarMotorHomePorId(idBuscado);

        // Verificaciones
        assertNull(productoDto); // Verificar que el productoDto sea nulo
        // Agregar más verificaciones según el comportamiento esperado cuando el producto no se encuentra en la base de datos
        verify(productoRepository, times(1)).findById(idBuscado); // Verificar que se llamó al método findById del repositorio
        // Podrías verificar el log en función de cómo manejas los logs en tu aplicación
    }

    // Otros tests para casos adicionales...
}