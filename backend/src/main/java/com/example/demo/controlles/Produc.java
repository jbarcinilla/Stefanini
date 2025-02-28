package com.example.demo.controlles;

import com.example.demo.services.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:4200") // Permitir Angular
public class Produc {

    @Autowired
    private Product productoService;

    @PostMapping("/productos")
    public ResponseEntity<Void> guardarProducto(@RequestBody com.example.demo.entity.Product nuevoProducto) {
        //productoService.guardarProducto(nuevoProducto);
        productoService.saveProduct(nuevoProducto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarProducto(@PathVariable int id, @RequestBody com.example.demo.entity.Product producto) {
        producto.setId(id); // Asegúrate de que el ID del producto a actualizar sea correcto
        //boolean actualizado = productoService.actualizarProducto(producto);
        com.example.demo.entity.Product actualizado = productoService.saveProduct(producto);
        if (actualizado!=null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping
    public List<com.example.demo.entity.Product> list() {
        return productoService.getAllProducts();

    }




}
