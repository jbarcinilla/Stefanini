package com.example.demo.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Product extends JpaRepository<com.example.demo.entity.Product, Long> {
    // Puedes agregar métodos de consulta personalizados aquí si es necesario.
}


