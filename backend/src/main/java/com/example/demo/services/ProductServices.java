package com.example.demo.services;


import com.example.demo.entity.ProductEntity;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class ProductServices {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServices(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Guardar un producto
    public ProductEntity saveProduct(ProductEntity product) {
        return productRepository.save(product);
    }

    // Eliminar un producto por ID
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // Listar todos los productos
    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    // Buscar un producto por ID
    public Optional<ProductEntity> getProductById(Long id) {
        return productRepository.findById(id);
    }

}
