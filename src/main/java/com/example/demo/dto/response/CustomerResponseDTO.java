package com.example.demo.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Data Transfer Object para respuestas de la API que incluyen información del cliente.
 *
 * PROPÓSITO:
 * - Controlar exactamente qué datos se envían al cliente
 * - Incluir campos calculados (como fullName)
 * - Formatear fechas de manera consistente
 * - Ocultar campos sensibles de la entidad
 * - Proporcionar metadata útil para el frontend
 *
 * CAMPOS INCLUIDOS:
 * - Todos los datos del cliente (excepto campos internos)
 * - Campos de auditoría (fechas, usuario)
 * - Campos calculados (fullName)
 * - Estado del registro (active)
 *
 * CAMPOS EXCLUIDOS:
 * - Password (si fuera una entidad User)
 * - Campos internos de JPA
 * - Información sensible del sistema
 */

@Data
@Schema(description = "DTO de respuesta con información completa del cliente")
public class CustomerResponseDTO {
    /**
     * ID único del cliente.
     *
     * CARACTERÍSTICAS:
     * - Solo lectura (se genera automáticamente en creación)
     * - Usado para operaciones posteriores (update, delete, get)
     * - Nunca se envía en requests, solo en responses
     */
    @Schema(
            description = "ID único del cliente",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY // Swagger: solo en respuestas
    )
    private Long id;

    /**
     * Nombre del cliente.
     * Copiado directamente de la entidad.
     */
    @Schema(
            description = "Nombre del cliente",
            example = "Juan"
    )
    private String firstname;

    /**
     * Apellido del cliente.
     */
    @Schema(
            description = "Apellido del cliente",
            example = "Pérez"
    )
    private String lastname;

    /**
     * Nombre completo del cliente.
     *
     * CAMPO CALCULADO:
     * - No existe en la entidad como campo persistido
     * - Se calcula usando el método getFullName() de la entidad
     * - El mapper se encarga de establecer este valor
     * - Útil para mostrar en el frontend sin concatenar
     */
    @Schema(
            description = "Nombre completo del cliente (campo calculado)",
            example = "Juan Pérez",
            accessMode = Schema.AccessMode.READ_ONLY // Solo calculado, nunca enviado
    )
    private String fullName; // Campo calculado

    /**
     * Email del cliente.
     */
    @Schema(
            description = "Correo electrónico del cliente",
            example = "juan.perez@email.com"
    )
    private String email;

    /**
     * Teléfono del cliente.
     * Puede ser null si no se proporcionó.
     */
    @Schema(
            description = "Número de teléfono del cliente",
            example = "+51987654321"
    )
    private String phone; // Puede ser null

    /**
     * Dirección del cliente.
     */
    @Schema(
            description = "Dirección del cliente",
            example = "Lima, Perú"
    )
    private String address; // Puede ser null

    /**
     * Estado activo/inactivo del cliente.
     *
     * INFORMACIÓN DE ESTADO:
     * - true: cliente activo (visible en consultas normales)
     * - false: cliente inactivo (soft delete)
     * - Útil para el frontend para mostrar estado visual
     */
    @Schema(
            description = "Estado del cliente (activo/inactivo)",
            example = "true"
    )
    private Boolean active;

    /**
     * Fecha y hora de creación del registro.
     *
     * FORMATEO JSON:
     * - @JsonFormat controla cómo se serializa la fecha a JSON
     * - Patrón: "yyyy-MM-dd HH:mm:ss"
     * - Ejemplo: "2024-01-15 10:30:00"
     * - Consistente en toda la API
     */
    @Schema(
            description = "Fecha y hora de creación del registro",
            example = "2024-01-15T10:30:00",
            accessMode = Schema.AccessMode.READ_ONLY // Solo lectura
    )
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // Formato de serialización JSON
    private LocalDateTime createdAt;

    /**
     * Fecha y hora de última actualización.
     */
    @Schema(
            description = "Fecha y hora de última actualización",
            example = "2024-01-16T15:45:30",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    /**
     * Usuario que creó el registro.
     *
     * AUDITORÍA:
     * - Información útil para auditoría y trazabilidad
     * - En sistema real sería el username del usuario autenticado
     * - Ayuda a rastrear quién hizo qué cambios
     */
    @Schema(
            description = "Usuario que creó el registro",
            example = "system",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private String createdBy;

    /**
     * Usuario que realizó la última actualización.
     */
    @Schema(
            description = "Usuario que realizó la última actualización",
            example = "admin",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private String updatedBy;
}
