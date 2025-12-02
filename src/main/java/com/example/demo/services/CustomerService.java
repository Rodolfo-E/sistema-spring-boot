package com.example.demo.services;

import java.util.List;

import com.example.demo.dto.request.CustomerCreateDTO;
import com.example.demo.dto.response.CustomerUpdateDTO;
import com.example.demo.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interfaz que define el contrato para los servicios de gestión de clientes.
 *
 * PATRÓN: Service Layer Pattern
 * - Separa la lógica de negocio del controlador
 * - Define operaciones de alto nivel sobre la entidad Customer
 * - Permite múltiples implementaciones (ej: database, cache, mock)
 *
 * BENEFICIOS:
 * - Testeable (se pueden crear mocks fácilmente)
 * - Intercambiable (inyección de dependencias)
 * - Documentación clara de operaciones disponibles
 */
public interface CustomerService {




        /**
         * Crea un nuevo cliente en el sistema.
         *
         * @param request DTO con los datos del cliente a crear
         * @return Customer entidad creada con ID asignado
         * @throws BusinessException si el email ya existe
         */
        Customer create(CustomerCreateDTO request);

        /**
         * Busca un cliente por su ID único.
         *
         * @param id ID del cliente a buscar
         * @return Customer encontrado
         * @throws EntityNotFoundException si el cliente no existe o está inactivo
         */
        Customer findById(Long id);

        /**
         * Obtiene todos los clientes activos con paginación.
         *
         * @param pageable configuración de paginación y ordenamiento
         * @return Page con los clientes encontrados
         */
        Page<Customer> findAll(Pageable pageable);

        /**
         * Obtiene todos los clientes activos sin paginación.
         * ¡CUIDADO! Solo usar cuando se sabe que hay pocos registros.
         *
         * @return Lista completa de clientes activos
         */
        List<Customer> findAll();

        /**
         * Actualiza un cliente existente.
         *
         * @param id ID del cliente a actualizar
         * @param request DTO con los campos a actualizar (campos null se ignoran)
         * @return Customer actualizado
         // @throws EntityNotFoundException si el cliente no existe
         // @throws BusinessException si el nuevo email ya existe
         */
        Customer update(Long id, CustomerUpdateDTO request);

        /**
         * Elimina lógicamente un cliente (soft delete).
         * Marca el cliente como inactivo en lugar de eliminarlo físicamente.
         *
         * @param id ID del cliente a eliminar
         //@throws EntityNotFoundException si el cliente no existe
         */
        void deleteById(Long id);

        /**
         * Busca clientes por texto libre en nombre, apellido o email.
         *
         * @param query término de búsqueda
         * @param pageable configuración de paginación
         * @return Page con resultados de búsqueda
         */
        Page<Customer> searchCustomers(String query, Pageable pageable);

        /**
         * Verifica si existe un cliente con el email dado.
         *
         * @param email email a verificar
         * @return true si existe, false si no
         */
        boolean existsByEmail(String email);

        /**
         * Encuentra clientes que tienen teléfono válido.
         *
         * @return Lista de clientes con teléfono
         */
        List<Customer> findCustomersWithValidPhone();

        /**
         * Cambia el estado activo/inactivo de un cliente.
         *
         * @param id ID del cliente
         * @return Customer con estado cambiado
         // @throws EntityNotFoundException si el cliente no existe
         */
        Customer toggleStatus(Long id);

        /**
         * Cuenta la cantidad de clientes activos.
         *
         * @return número de clientes activos
         */
        long countActiveCustomers();

        /**
         * Obtiene los clientes más recientes.
         *
         * @param limit cantidad máxima de clientes a retornar
         * @return Lista de clientes más recientes
         */
        List<Customer> findRecentCustomers(int limit);
    }
