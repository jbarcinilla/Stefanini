package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;
import com.example.demo.entity.Product;
import com.example.demo.services.ServicesProducto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.InjectMocks;


import java.util.List;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Optional;


public class ProductServiceTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private ServicesProducto productService; // Suponiendo que este método está en ProductService



    private List<Product> productos;

    @BeforeEach
    void setUp() {


        // Simulamos datos de prueba
        productos = List.of(
                new Product(1, "Producto A", 25.50),
                new Product(2, "Producto B", 30.00)
        );

    }

    @Test
    void testLeerJsonJackson() throws IOException {
        // Cargar el archivo JSON de prueba desde src/test/resources
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data.json");
        assertNotNull(inputStream, "El archivo data.json no se encontró en resources.");

        // Deserializar el JSON a una lista de objetos Product
        List<Product> productos = Arrays.asList(objectMapper.readValue(inputStream, Product[].class));



        // Verificar que se cargaron correctamente los datos
        assertTrue(!productos.isEmpty());
        assertEquals(1, productos.get(0).getId());
        assertTrue(!productos.get(0).getNombre().isBlank());
        assertNotNull(productos.get(0).getPrecio());

    }

    @Test
    void testObtenerProductoPorId_Encontrado() {
        // Act: Llamamos al método con un ID válido
        Optional<Product> productoOpt = productService.obtenerProductoPorId(1);

        // Assert: Verificamos que encontró el producto
        assertTrue(productoOpt.isPresent());
        assertEquals(1, productoOpt.get().getId());
        assertEquals("Producto A", productoOpt.get().getNombre());
        assertEquals(25.50, productoOpt.get().getPrecio());
    }

    @Test
    void testObtenerProductoPorId_NoEncontrado() {
        // Act: Buscamos un ID que no existe
        Optional<Product> productoOpt = productService.obtenerProductoPorId(99);

        // Assert: No debe encontrar el producto
        assertFalse(productoOpt.isPresent());
    }

    @Test
    void testEliminarProductoPorId_Existente() {
        // Act: Llamamos al método con un ID que existe (1)
        List<Product> productosRestantes = productService.eliminarProductoPorId(1);

        // Assert: Debe eliminar el producto con ID 1 y quedar solo 1 producto
        assertEquals(1, productosRestantes.size());
        assertEquals(2, productosRestantes.get(0).getId()); // Solo queda el producto con ID 2
    }

    @Test
    void testEliminarProductoPorId_NoExistente() {
        // Act: Intentamos eliminar un ID que no existe (99)
        List<Product> productosRestantes = productService.eliminarProductoPorId(99);

        // Assert: La lista no debe cambiar
        assertEquals(2, productosRestantes.size());
    }

    @Test
    void testActualizarProducto_Existente() throws IOException {
        // Arrange: Creamos un producto con el mismo ID pero con cambios
        Product productoActualizado = new Product(1, "Producto Modificado", 40.00);

        // Act: Llamamos al método
        boolean resultado = productService.actualizarProducto(productoActualizado);

        // Assert: Debe actualizarse correctamente
        assertTrue(resultado);
    }

    @Test
    void testActualizarProducto_NoExistente() throws IOException {
        // Arrange: Intentamos actualizar un producto que no existe
        Product productoNoExistente = new Product(99, "Producto Inexistente", 50.00);

        // Act: Llamamos al método
        boolean resultado = productService.actualizarProducto(productoNoExistente);

        // Assert: Debe devolver false
        assertFalse(resultado);
    }

    @Test
    void testGuardarProducto() throws IOException {
        // Arrange: Creamos un nuevo producto
        Product nuevoProducto = new Product(3, "Producto C", 45.00);

        // Act: Llamamos al método
        productService.guardarProducto(nuevoProducto);

        // Assert: Verificamos que la lista contiene el nuevo producto
        assertEquals(3, productos.size());
        assertTrue(productos.contains(nuevoProducto));


    }
}
