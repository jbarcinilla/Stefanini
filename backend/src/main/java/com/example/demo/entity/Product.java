package com.example.demo.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.stereotype.Component;



@Component
@Data  // Genera automáticamente getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor vacío (obligatorio para Jackson)
@AllArgsConstructor // Genera un constructor con todos los parámetros
@Entity
public class Product {

    @Id
    private int id;
    private String name;
    private double price;



}
