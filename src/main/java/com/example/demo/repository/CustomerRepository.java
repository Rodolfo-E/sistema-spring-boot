package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Customer;

import java.util.List;
import java.util.Optional;

//Va estar la conexion con y la logica con la bd
@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {



    // ===== BÚSQUEDAS BÁSICAS =====

    // Buscar solo clientes activos
    Page<Customer> findByActiveTrue(Pageable pageable);
    List<Customer> findByActiveTrueOrderByCreatedAtDesc();

    // Buscar por ID solo si está activo
    Optional<Customer> findByIdAndActiveTrue(Long id);

    // ===== VERIFICACIONES =====

    // Verificar si existe por email (solo activos)
    boolean existsByEmailAndActiveTrue(String email);

    // Verificar si existe por email (cualquier estado)
    boolean existsByEmail(String email);

    // ===== BÚSQUEDAS POR EMAIL =====

    // Buscar por email (solo activos)
    Optional<Customer> findByEmailAndActiveTrue(String email);

}
