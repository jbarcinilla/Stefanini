package com.example.demo.entity;


import lombok.*;
import org.springframework.stereotype.Component;



@Component
@Data  // Genera automáticamente getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor vacío (obligatorio para Jackson)
@AllArgsConstructor // Genera un constructor con todos los parámetros
public class Product {
    private int id;
    private String nombre;
    private double precio;



}
