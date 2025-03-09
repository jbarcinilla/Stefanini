package com.example.demo.controlles;

import com.example.demo.entity.ProductEntity;
import com.example.demo.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:4200") // Permitir Angular
public class ProducController {

    @Autowired
    private ProductServices productoService;

    @PostMapping
    public ResponseEntity<Void> saveProduct(@RequestBody ProductEntity nuevoProducto) {
        //productoService.guardarProducto(nuevoProducto);
        productoService.saveProduct(nuevoProducto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> updateProduct( @RequestBody ProductEntity product) {
        ProductEntity update = productoService.saveProduct(product);
        if (update!=null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productoService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Void> getProductId(@PathVariable Long id) {
        Optional op= productoService.getProductById(id);
         if(op.isPresent()){
             return new ResponseEntity<>(HttpStatus.OK);
         }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


    @GetMapping
    public List<ProductEntity> list() {
        return productoService.getAllProducts();

    }




}
