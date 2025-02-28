package com.example.demo.repository;



import com.example.demo.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    // Puedes agregar métodos de consulta personalizados aquí si es necesario.
}


