package com.example.demo.controllers;

import java.util.List;

import com.example.demo.entities.Employee;
import com.example.demo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//Seran el punto de acceso
@RestController
public class EmployeeController  {

    @Autowired
    private EmployeeService service;


    @GetMapping("/api/employees")
    public List<Employee> getAll(){

        return service.getAll();

    }

    @GetMapping("/api/employees/{id}")
    public Employee getById(@PathVariable String id){

        return service.getById(Long.parseLong(id));

    }

    @DeleteMapping("/api/employees/{id}")
    public void remove(@PathVariable String id){
        service.remove(Long.parseLong(id));
    }

    @PostMapping("/api/employees")
    public void save(@RequestBody Employee employee){
        service.save(employee);
    }
}
