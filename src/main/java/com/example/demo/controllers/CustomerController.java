package com.example.demo.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.dto.request.CustomerCreateDTO;
import com.example.demo.dto.response.CustomerResponseDTO;
import com.example.demo.mapper.CustomerMapper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.Customer;
import com.example.demo.services.CustomerService;

import javax.validation.Valid;

//Seran el punto de acceso
@Slf4j
@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Validated
public class CustomerController  {
	

	private final CustomerService customerService;
	private final CustomerMapper customerMapper;

	// Crear cliente
	@PostMapping
	public ResponseEntity<CustomerResponseDTO> createCustomer(
			@Valid @RequestBody CustomerCreateDTO request) {

		log.info("Solicitud REST para crear un cliente con correo electronico: {}", request.getEmail());
		//log.info("REST request to create customer with email: {}", request.getEmail());

		Customer customer = customerService.create(request);
		CustomerResponseDTO response = customerMapper.toResponseDTO(customer);


		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}


	/**
	 * Obtiene todos los clientes
	 */
	@GetMapping
	@Operation(
			summary = "Obtener todos los clientes",
			description = "Retorna una lista paginada de todos los clientes registrados en el sistema"
	)

	public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {
		log.info("Solicitud para obtener todos los clientes");

		List<Customer> customers = customerService.findAll();



	//	List<CustomerResponseDTO> response = customers.stream().map(customerMapper::toResponseDTO).collect(Collectors.toList());
		List<CustomerResponseDTO> response = customerMapper.toResponseDTOList(customers);
		log.debug("Retornando {} clientes", response.size());
		log.debug("Cliente : {} ", response+"\n");
		return ResponseEntity.ok(response);
	}















/*
	@GetMapping("/api/customers")
	public List<Customer> getAll(){

		return service.getAll();
	
	}

	@GetMapping("/api/customers/{id}")
	public Customer getById(@PathVariable String id){

		return service.getById(Long.parseLong(id));

	}

	@DeleteMapping("/api/customers/{id}")
	public void remove(@PathVariable String id){
		service.remove(Long.parseLong(id));
	}

	@PostMapping("/api/customers")
	public void save(@RequestBody Customer customer){
		service.save(customer);
	}*/


}
