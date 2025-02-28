package com.example.demo.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class Product {

    private final com.example.demo.repository.Product productRepository;

    @Autowired
    public Product(com.example.demo.repository.Product productRepository) {
        this.productRepository = productRepository;
    }

    // Guardar un producto
    public com.example.demo.entity.Product saveProduct(com.example.demo.entity.Product product) {
        return productRepository.save(product);
    }

    // Eliminar un producto por ID
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // Listar todos los productos
    public List<com.example.demo.entity.Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Buscar un producto por ID
    public Optional<com.example.demo.entity.Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

}
