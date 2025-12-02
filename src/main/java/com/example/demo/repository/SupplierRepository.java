package com.example.demo.repository;

import com.example.demo.entities.Employee;
import com.example.demo.entities.Supplier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//Va estar la conexion con y la logica con la bd
@Repository
public interface SupplierRepository extends CrudRepository<Supplier, Long> {



}
