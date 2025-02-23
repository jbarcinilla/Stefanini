package com.example.demo.controlles;

import com.example.demo.entity.Product;
import com.example.demo.services.ServicesProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "http://localhost:4200") // Permitir Angular
public class ProductoController {

    @Autowired
    private ServicesProducto productoService;

    @PostMapping("/productos")
    public ResponseEntity<Void> guardarProducto(@RequestBody Product nuevoProducto) {
        productoService.guardarProducto(nuevoProducto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarProducto(@PathVariable int id, @RequestBody Product producto) {
        producto.setId(id); // Asegúrate de que el ID del producto a actualizar sea correcto
        boolean actualizado = productoService.actualizarProducto(producto);
        if (actualizado) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping
    public List<Product> list() {
        return productoService.leerJsonJackson();

    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> obtenerProductoPorId(@PathVariable int id) {
        return productoService.obtenerProductoPorId(id)
                .map(producto -> new ResponseEntity<>(producto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable int id) {

        Optional<Product> existe = productoService.obtenerProductoPorId(id);

        if (existe.isPresent()) {
            productoService.eliminarProductoPorId(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


}
