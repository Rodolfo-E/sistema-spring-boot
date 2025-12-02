package com.example.demo.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Data Transfer Object para actualizar un cliente existente.
 *
 * DIFERENCIAS CON CustomerCreateDTO:
 * - TODOS los campos son opcionales (sin @NotBlank)
 * - Solo se actualizan los campos que vienen en el request (no-null)
 * - Permite actualizaciones parciales (PATCH-style en PUT)
 * - Validaciones más permisivas pero manteniendo formato
 *
 * PATRÓN: Partial Update DTO
 * - Campos null = no actualizar
 * - Campos con valor = actualizar
 */

@Data
@Schema(description = "DTO para actualizar datos de un cliente existente. Todos los campos son opcionales.")
public class CustomerUpdateDTO {

    /**
     * Nombre del cliente (opcional para actualización).
     *
     * DIFERENCIAS CON CREATE:
     * - Sin @NotBlank: puede ser null (no actualizar)
     * - Si viene con valor: debe cumplir @Size
     * - null = mantener valor actual
     * - "" o "   " = error de validación
     */
    @Schema(
            description = "Nombre del cliente (opcional para actualización)",
            example = "Juan Carlos", // Ejemplo de cambio
            minLength = 2,
            maxLength = 50
    )
    @Size(min = 2, max = 50, message = "Firstname must be between 2 and 50 characters")
    private String firstname; // Opcional (sin @NotBlank)

    /**
     * Apellido del cliente (opcional para actualización).
     */
    @Schema(
            description = "Apellido del cliente (opcional para actualización)",
            example = "Pérez García",
            minLength = 2,
            maxLength = 50
    )
    @Size(min = 2, max = 50, message = "Lastname must be between 2 and 50 characters")
    private String lastname; // Opcional

    /**
     * Email del cliente (opcional para actualización).
     *
     * LÓGICA ESPECIAL:
     * - Si se proporciona, debe ser email válido
     * - El servicio verificará que no existe en otro cliente
     * - null = mantener email actual
     */
    @Schema(
            description = "Correo electrónico del cliente (opcional para actualización)",
            example = "juan.carlos.perez@email.com",
            format = "email"
    )
    @Email(message = "Invalid email format") // Validación solo si no es null
    private String email; // Opcional

    /**
     * Teléfono del cliente (opcional para actualización).
     */
    @Schema(
            description = "Número de teléfono (opcional para actualización)",
            example = "+51999888777",
            pattern = "^\\+?[1-9]\\d{1,14}$"
    )
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone format")
    private String phone; // Opcional

    /**
     * Dirección del cliente (opcional para actualización).
     */
    @Schema(
            description = "Dirección del cliente (opcional para actualización)",
            example = "Av. El Sol 456, Miraflores, Lima",
            maxLength = 200
    )
    @Size(max = 200, message = "Address must not exceed 200 characters")
    private String address; // Opcional
}