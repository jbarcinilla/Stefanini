package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.example.demo.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductTest {

    @Mock
    private com.example.demo.repository.Product productRepository;

    @InjectMocks
    private com.example.demo.services.Product productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product(1, "Producto de ejemplo", 100.0);
    }

    @Test
    void saveProduct() {
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.saveProduct(product);
        assertNotNull(savedProduct);
        assertEquals(product.getId(), savedProduct.getId());
    }

    @Test
    void deleteProduct() {
        doNothing().when(productRepository).deleteById(1L);
        productService.deleteProduct(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void getAllProducts() {
        List<Product> products = Arrays.asList(product);
        when(productRepository.findAll()).thenReturn(products);
        List<Product> result = productService.getAllProducts();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(product.getId(), result.get(0).getId());
    }

    @Test
    void getProductById() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Optional<Product> foundProduct = productService.getProductById(1L);
        assertTrue(foundProduct.isPresent());
        assertEquals(product.getId(), foundProduct.get().getId());
    }
}

