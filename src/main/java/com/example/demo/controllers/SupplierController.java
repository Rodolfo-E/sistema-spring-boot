package com.example.demo.controllers;

import java.util.List;

import com.example.demo.entities.Supplier;
import com.example.demo.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//Seran el punto de acceso
@RestController
public class SupplierController  {

    @Autowired
    private SupplierService service;


    @GetMapping("/api/suppliers")
    public List<Supplier> getAll(){

        return service.getAll();

    }

    @GetMapping("/api/suppliers/{id}")
    public Supplier getById(@PathVariable String id){

        return service.getById(Long.parseLong(id));

    }

    @DeleteMapping("/api/suppliers/{id}")
    public void remove(@PathVariable String id){
        service.remove(Long.parseLong(id));
    }

    @PostMapping("/api/suppliers")
    public void save(@RequestBody Supplier supplier){
        service.save(supplier);
    }
}
