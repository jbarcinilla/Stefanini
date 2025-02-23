package com.example.demo.services;


import com.example.demo.entity.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServicesProducto {

    public List<Product> leerJsonJackson() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Product> productos = List.of();
        try (InputStream inputStream = Product.class.getClassLoader().getResourceAsStream("data.json")) {
            if (inputStream == null) {
                throw new IOException("El archivo data.json no se encontró en resources.");
            }

            productos = Arrays.asList(objectMapper.readValue(inputStream, Product[].class));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return productos;
    }

    public Optional<Product> obtenerProductoPorId(int id) {
        List<Product> productos = leerJsonJackson();
        return productos.stream()
                .filter(producto -> producto.getId() == id)
                .findFirst();
    }

    public List<Product> eliminarProductoPorId(int id) {
        List<Product> productos = leerJsonJackson();
        productos = productos.stream()
                .filter(producto -> producto.getId() != id)
                .collect(Collectors.toList());

        // Actualiza el archivo data.json con la nueva lista de productos
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(Product.class.getClassLoader().getResource("data.json").getFile()), productos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return productos;

    }

    public boolean actualizarProducto(Product nuevoProducto) {
        List<Product> productos = leerJsonJackson();
        boolean exists = productos.stream().anyMatch(producto -> producto.getId() == nuevoProducto.getId());
        if (!exists) {
            return false;
        }

        productos = productos.stream()
                .map(producto -> {
                    if (producto.getId() == nuevoProducto.getId()) {
                        return nuevoProducto;
                    }
                    return producto;
                })
                .collect(Collectors.toList());

        // Actualiza el archivo data.json con la nueva lista de productos
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(Product.class.getClassLoader().getResource("data.json").getFile()), productos);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }

    public void guardarProducto(Product nuevoProducto) {
        List<Product> productos = new ArrayList<>(leerJsonJackson());
        productos.add(nuevoProducto);

        // Actualiza el archivo data.json con la nueva lista de productos
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(Product.class.getClassLoader().getResource("data.json").getFile()), productos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
