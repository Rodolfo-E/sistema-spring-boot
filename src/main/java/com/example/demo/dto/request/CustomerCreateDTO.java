package com.example.demo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


/**
 * Data Transfer Object para crear un nuevo cliente.
 *
 * PATRÓN: DTO (Data Transfer Object)
 * PROPÓSITO:
 * - Separar la representación externa (API) de la interna (entidad)
 * - Controlar exactamente qué datos se reciben del cliente
 * - Aplicar validaciones específicas para creación
 * - Evitar exposición de campos internos (como ID, fechas de auditoría)
 *
 * DIFERENCIAS CON LA ENTIDAD:
 * - No tiene ID (se genera automáticamente)
 * - No tiene campos de auditoría (createdAt, updatedAt, etc.)
 * - Validaciones más estrictas (todos los campos requeridos para creación)
 * - Incluye documentación Swagger para la API
 */

@Data    // Lombok: genera getters, setters, toString, equals, hashCode automáticamente
@Schema(description = "DTO para crear un nuevo cliente") // Swagger: documentación del modelo
public class CustomerCreateDTO {

    /**
     * Nombre del cliente.
     *
     * VALIDACIONES:
     * - @NotBlank: no puede ser null, vacío ("") o solo espacios ("   ")
     * - @Size: longitud entre 2 y 50 caracteres
     *
     * SWAGGER:
     * - example: valor de ejemplo en la documentación
     * - required: indica si es obligatorio
     * - minLength/maxLength: restricciones de longitud para el frontend
     */
    @Schema (
            description = "Nombre del cliente", // Descripción en documentación
            example = "Juan", // Ejemplo que aparece en Swagger UI
            required = true, // Indica que es obligatorio
            minLength = 2, // Longitud mínima
            maxLength = 50 // Longitud máxima
    )
    @NotBlank(message = "Firstname is required") // Bean Validation: campo obligatorio
    @Size(min = 2, max = 50, message = "Firstname must be between 2 and 50 characters")
    private String firstname;

    /**
     * Apellido del cliente.
     * Validaciones similares al firstname para consistencia.
     */
    @Schema(
            description = "Apellido del cliente",
            example = "Pérez",
            required = true,
            minLength = 2,
            maxLength = 50
    )
    @NotBlank(message = "Lastname is required")
    @Size(min = 2, max = 50, message = "Lastname must be between 2 and 50 characters")
    private String lastname;

    /**
     * Correo electrónico del cliente.
     *
     * VALIDACIONES ESPECIALES:
     * - @Email: valida formato de email (usa regex interno de Bean Validation)
     * - @NotBlank: obligatorio para creación
     *
     * REGLA DE NEGOCIO: Los emails deben ser únicos en el sistema
     * (esta validación se hace en el servicio, no en el DTO)
     */
    @Schema(
            description = "Correo electrónico único del cliente",
            example = "juan.perez@email.com",
            required = true,
            format = "email" // Swagger: indica que es formato email para el frontend
    )
    @Email(message = "Invalid email format") // Validación de formato email
    @NotBlank(message = "Email is required")
    private String email;

    /**
     * Número de teléfono (opcional).
     *
     * VALIDACIONES:
     * - @Pattern: regex personalizado para formato internacional
     * - NO tiene @NotBlank (campo opcional)
     *
     * REGEX EXPLICADO:
     * ^\\+?[1-9]\\d{1,14}$
     * - ^: inicio de string
     * - \\+?: signo + opcional (formato internacional)
     * - [1-9]: primer dígito del 1 al 9 (no puede empezar con 0)
     * - \\d{1,14}: entre 1 y 14 dígitos adicionales
     * - $: fin de string
     *
     * EJEMPLOS VÁLIDOS: +51987654321, 987654321, +1234567890
     */
    @Schema(
            description = "Número de teléfono internacional",
            example = "+51987654321",
            pattern = "^\\+?[1-9]\\d{1,14}$" // Documenta el patrón esperado
    )
    @Pattern(
            regexp = "^\\+?[1-9]\\d{1,14}$",
            message = "Invalid phone format" // Mensaje de error personalizado
    )
    private String phone; // Opcional (sin @NotBlank)

    /**
     * Dirección completa del cliente (opcional).
     *
     * VALIDACIÓN:
     * - @Size: máximo 200 caracteres (sin mínimo porque es opcional)
     * - Sin @NotBlank: campo completamente opcional
     */
    @Schema(
            description = "Dirección completa del cliente",
            example = "Av. Javier Prado 123, San Isidro, Lima",
            maxLength = 200 // Solo máximo, sin mínimo
    )
    @Size(max = 200, message = "Address must not exceed 200 characters")
    private String address; // Opcional
}
