package com.example.demo.entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.example.demo.entities.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Entidad que representa un Cliente en el sistema.
 * Extiende de BaseEntity para heredar campos comunes como id, fechas de auditoría, etc.
 *
 * Utiliza JPA para mapeo objeto-relacional y Lombok para generar código automáticamente.
 */

@Entity // Indica a JPA que esta clase es una entidad que se mapea a una tabla de BD
@Table(name = "customers", indexes = { // Define el nombre de la tabla y los índices para mejor performance
		@Index(name = "idx_customer_email", columnList = "email"), // Índice en email para búsquedas rápidas
		@Index(name = "idx_customer_active", columnList = "active") // Índice en active para filtros por estado
})

// === ANOTACIONES DE LOMBOK (generan código automáticamente) ===
@Data // Genera getters, setters, toString, equals, hashCode automáticamente
@EqualsAndHashCode (callSuper = true) // Incluye campos de la clase padre en equals/hashCode
@NoArgsConstructor // Genera constructor sin parámetros (requerido por JPA)
@AllArgsConstructor // Genera constructor con todos los parámetros
@SuperBuilder // Permite usar patrón Builder incluso con herencia
public class Customer extends BaseEntity {
	/**
	 * Nombre del cliente.
	 * Campo obligatorio con validaciones de longitud.
	 */
	@NotBlank // Valida que no sea null, vacío o solo espacios en blanco
	@Size(min = 2, max = 50) // Longitud mínima 2, máxima 50 caracteres
	@Column(name = "firstname", nullable = false, length = 50) // Mapeo a columna de BD
	private String firstname;

	/**
	 * Apellido del cliente.
	 * Campo obligatorio con validaciones similares al firstname.
	 */
	@NotBlank // Validación: campo obligatorio
	@Size(min = 2, max = 50) // Validación: longitud entre 2 y 50 caracteres
	@Column(name = "lastname", nullable = false, length = 50) // nullable=false hace la columna NOT NULL
	private String lastname;
	/**
	 * Email del cliente.
	 * Campo único y obligatorio con validación de formato email.
	 */
	@Email // Valida que tenga formato de email válido
	@NotBlank // Campo obligatorio
	@Column(name = "email", nullable = false, unique = true, length = 100) // unique=true crea constraint único
	private String email;

	/**
	 * Número de teléfono del cliente.
	 * Campo opcional con validación de longitud.
	 */
	@Size(max = 20) // Máximo 20 caracteres (campo opcional, no se valida si es null)
	@Column(name = "phone", length = 20) // Campo opcional (nullable por defecto es true)
	private String phone;

	/**
	 * Dirección del cliente.
	 * Campo opcional para almacenar la dirección completa.
	 */
	@Size(max = 200) // Validación: máximo 200 caracteres
	@Column(name = "address", length = 200) // Columna de hasta 200 caracteres
	private String address;

	/**
	 * Método de conveniencia para obtener el nombre completo del cliente.
	 * Combina firstname y lastname.
	 *
	 * @return String con el nombre completo "firstname lastname"
	 */
	public String getFullName() {
		return String.format("%s %s", firstname, lastname);
	}
}
