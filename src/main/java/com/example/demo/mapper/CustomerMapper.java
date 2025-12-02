package com.example.demo.mapper;

import com.example.demo.dto.request.CustomerCreateDTO;
import com.example.demo.dto.response.CustomerResponseDTO;
import com.example.demo.dto.response.CustomerUpdateDTO;
import com.example.demo.entities.Customer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Mapper para conversiones entre DTOs y entidades Customer.
 *
 * PATRÓN: Mapper Pattern
 * RESPONSABILIDADES:
 * 1. Convertir DTOs a entidades (para persistencia)
 * 2. Convertir entidades a DTOs (para respuestas)
 * 3. Actualizar entidades con datos de DTOs (para updates)
 * 4. Transformar colecciones
 * 5. Aplicar lógica de conversión específica
 *
 * VENTAJAS:
 * - Centraliza lógica de conversión
 * - Separa estructura interna de externa
 * - Facilita testing unitario
 * - Permite transformaciones complejas
 * - Reutilizable en múltiples servicios
 *
 * ALTERNATIVAS:
 * - MapStruct (generación automática de mappers)
 * - ModelMapper (mapping por reflexión)
 * - Manual (como este ejemplo, más control)
 */

@Component // Spring: registra como bean para inyección de dependencias
public class CustomerMapper {

    /**
     * Convierte un DTO de creación a una entidad Customer.
     * <p>
     * TRANSFORMACIONES APLICADAS:
     * 1. Limpieza de strings (strip() para quitar espacios)
     * 2. Normalización de email (toLowerCase())
     * 3. Manejo de campos opcionales con Optional
     * 4. Establecimiento de valores por defecto (active=true)
     *
     * @param dto DTO con datos del cliente a crear
     * @return Customer entidad lista para persistir (sin ID)
     */
    public Customer toEntity(CustomerCreateDTO dto) {
        // === VALIDACIÓN DE ENTRADA ===
        if (dto == null) return null; // Defensive programming

        // === CONSTRUCCIÓN DE ENTIDAD ===
        return Customer.builder() // Patrón Builder (Lombok @SuperBuilder)
                // Campos obligatorios: limpiar espacios
                .firstname(dto.getFirstname().strip()) // Java 11: strip() mejor que trim()
                .lastname(dto.getLastname().strip())

                // Email: limpiar y normalizar a minúsculas
                .email(dto.getEmail().strip().toLowerCase()) // Normalización para consistencia

                // === CAMPOS OPCIONALES CON OPTIONAL ===
                // Pattern: Optional.ofNullable() para manejo elegante de nulls
                .phone(Optional.ofNullable(dto.getPhone()) // Si phone no es null
                        .map(String::strip) // Limpiar espacios
                        .filter(phone -> !phone.isBlank()) // Si no está vacío después de limpiar
                        .orElse(null)) // Sino, dejar como null

                .address(Optional.ofNullable(dto.getAddress())
                        .map(String::strip)
                        .filter(addr -> !addr.isBlank())
                        .orElse(null))

                // === VALORES POR DEFECTO ===
                // Siempre activo al crear
                .build();

        // NOTA: Los campos de auditoría (createdAt, createdBy) se establecen
        // automáticamente por los métodos @PrePersist de BaseEntity
    }

    /**
     * Convierte una entidad Customer a DTO de respuesta.
     *
     * TRANSFORMACIONES:
     * 1. Copia todos los campos relevantes
     * 2. Calcula campos derivados (fullName)
     * 3. Incluye metadata de auditoría
     * 4. Formatea fechas (manejado por @JsonFormat en el DTO)
     *
     * @param entity entidad Customer de la base de datos
     * @return CustomerResponseDTO para enviar al cliente
     */
    public CustomerResponseDTO toResponseDTO(Customer entity) {
        // === VALIDACIÓN DE ENTRADA ===
        if (entity == null) return null;

        // === CONSTRUCCIÓN MANUAL DEL DTO ===
        // Se usa constructor + setters en lugar de Builder para  mayor claridad
        var dto = new CustomerResponseDTO();

        // === CAMPOS BÁSICOS ===
        dto.setId(entity.getId());
        dto.setFirstname(entity.getFirstname());
        dto.setLastname(entity.getLastname());

        // === CAMPO CALCULADO ===
        // getFullName() es un método de conveniencia en la entidad
        dto.setFullName(entity.getFullName()); // "Juan Pérez"

        // === CAMPOS DE DATOS ===
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone()); // Puede ser null
        dto.setAddress(entity.getAddress()); // Puede ser null

        // === METADATA ===
        dto.setActive(entity.getActive()); // Estado del registro

        // === CAMPOS DE AUDITORÍA ===
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setUpdatedBy(entity.getUpdatedBy());

        return dto;
    }

    /**
     * Actualiza una entidad existente con datos de un DTO de actualización.
     *
     * LÓGICA DE ACTUALIZACIÓN PARCIAL:
     * - Solo actualiza campos que NO son null en el DTO
     * - Campos null en el DTO = mantener valor actual de la entidad
     * - Aplica las mismas transformaciones que en creación
     * - Usa Optional para código más expresivo
     *
     * PATRÓN: Partial Update / Merge Pattern
     *
     * @param dto DTO con campos a actualizar (campos null se ignoran)
     * @param entity entidad existente a modificar (se modifica in-place)
     */
    public void updateEntityFromDTO(CustomerUpdateDTO dto, Customer entity) {
        // === VALIDACIONES DE ENTRADA ===
        if (dto == null || entity == null) return;

        // === ACTUALIZACIÓN CONDICIONAL CON OPTIONAL ===
        // Pattern: Optional.ofNullable(campo).filter().map().ifPresent()

        // Firstname: solo si no es null y no está vacío después de limpiar
        Optional.ofNullable(dto.getFirstname()) // Si dto.firstname no es null
                .filter(name -> !name.isBlank()) // Y no está vacío
                .map(String::strip) // Limpiarlo
                .ifPresent(entity::setFirstname); // Actualizarlo en la entidad

        // Lastname: misma lógica
        Optional.ofNullable(dto.getLastname())
                .filter(name -> !name.isBlank())
                .map(String::strip)
                .ifPresent(entity::setLastname);

        // Email: limpiar y normalizar
        Optional.ofNullable(dto.getEmail())
                .filter(email -> !email.isBlank())
                .map(String::strip)
                .map(String::toLowerCase) // Normalización a minúsculas
                .ifPresent(entity::setEmail);

        // Phone: permitir establecer a null (para "borrar" el teléfono)
        Optional.ofNullable(dto.getPhone())
                .map(String::strip)
                .filter(phone -> !phone.isBlank()) // Solo si no está vacío
                .ifPresent(entity::setPhone);

        // Address: misma lógica que phone
        Optional.ofNullable(dto.getAddress())
                .map(String::strip)
                .filter(address -> !address.isBlank())
                .ifPresent(entity::setAddress);

        // NOTA: Los campos de auditoría (updatedAt, updatedBy) se actualizan
        // automáticamente por @PreUpdate de BaseEntity
    }

    /**
     * Convierte una lista de entidades a lista de DTOs de respuesta.
     *
     * PROGRAMACIÓN FUNCIONAL:
     * - Usa Java Streams para transformación
     * - map() aplica la transformación a cada elemento
     * - collect() convierte Stream de vuelta a List
     *
     * @param entities lista de entidades Customer
     * @return lista de CustomerResponseDTO
     */
    public List<CustomerResponseDTO> toResponseDTOList(List<Customer> entities) {
        // === VALIDACIÓN DE ENTRADA ===
        if (entities == null) return List.of(); // Java 11: List.of() para lista vacía inmutable

        // === TRANSFORMACIÓN CON STREAMS ===
        return entities.stream() // Convertir List a Stream
                .map(this::toResponseDTO) // Aplicar toResponseDTO a cada Customer
                .collect(Collectors.toList()); // Convertir Stream de vuelta a List

        // EQUIVALENTE IMPERATIVO (menos expresivo):
        // List<CustomerResponseDTO> result = new ArrayList<>();
        // for (Customer entity : entities) {
        //     result.add(toResponseDTO(entity));
        // }
        // return result;
    }

}