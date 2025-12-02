package com.example.demo.services.imp;

import java.util.List;

import com.example.demo.dto.request.CustomerCreateDTO;
import com.example.demo.dto.response.CustomerUpdateDTO;
import com.example.demo.exeption.BusinessException;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.services.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Customer;
import com.example.demo.repository.CustomerRepository;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;
	private final CustomerMapper customerMapper;
	


	@Override
	public Customer create(CustomerCreateDTO request) {
		log.info("Creando cliente con correo electrónico: {}", request.getEmail());

		var email = request.getEmail().strip();

		if (customerRepository.existsByEmailAndActiveTrue(email)) {
			log.warn("Attempt to create customer with existing email: {}", email);
			throw new BusinessException("Customer with email already exists: " + email);
		}

		var customer = customerMapper.toEntity(request);
		var savedCustomer = customerRepository.save(customer);

		log.info("Cliente creado correctamente con ID: {}", savedCustomer.getId());
		return savedCustomer;
	}

	@Override
	public Customer findById(Long id) {
		return null;
	}

	@Override
	public Page<Customer> findAll(Pageable pageable) {
		return null;
	}

	@Override
	public List<Customer> findAll() {
		return (List<Customer>)customerRepository.findAll();
	}

	@Override
	public Customer update(Long id, CustomerUpdateDTO request) {
		return null;
	}

	@Override
	public void deleteById(Long id) {

	}

	@Override
	public Page<Customer> searchCustomers(String query, Pageable pageable) {
		return null;
	}

	@Override
	public boolean existsByEmail(String email) {
		return false;
	}

	@Override
	public List<Customer> findCustomersWithValidPhone() {
		return List.of();
	}

	@Override
	public Customer toggleStatus(Long id) {
		return null;
	}

	@Override
	public long countActiveCustomers() {
		return 0;
	}

	@Override
	public List<Customer> findRecentCustomers(int limit) {
		return List.of();
	}
}
