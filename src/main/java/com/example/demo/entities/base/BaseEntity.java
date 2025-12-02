package com.example.demo.entities.base;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Clase base que contiene campos comunes para todas las entidades.
 * Implementa el patrón "Table per Class" para auditoría y campos comunes.
 *
 * Al usar @MappedSuperclass, los campos se incluyen en las tablas de las clases hijas,
 * pero esta clase no genera su propia tabla.
 */
@Data // Lombok: genera getters, setters, toString, equals, hashCode
@MappedSuperclass // JPA: indica que es una superclase cuyos campos se mapean en las tablas hijas

@NoArgsConstructor // Genera constructor sin parámetros (requerido por JPA)
@AllArgsConstructor // Genera constructor con todos los parámetros
@SuperBuilder
public class BaseEntity {
    /**
     * Identificador único de la entidad.
     * Se genera automáticamente usando estrategia IDENTITY (auto-increment).
     */
    @Id // Indica que es la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment en la BD
    private Long id;

    /**
     * Indica si el registro está activo.
     * Se usa para "soft delete" - en lugar de eliminar físicamente, se marca como inactivo.
     */
    @Column(name = "active", nullable = false) // Columna NOT NULL en BD
    private Boolean active = true; // Valor por defecto: activo

    /**
     * Fecha y hora de creación del registro.
     * Se establece automáticamente cuando se crea la entidad.
     */
    @CreationTimestamp // Hibernate establece automáticamente la fecha de creación
    @Column(name = "created_at", updatable = false) // updatable=false impide modificaciones posteriores
    private LocalDateTime createdAt;

    /**
     * Fecha y hora de última actualización.
     * Se actualiza automáticamente cada vez que se modifica la entidad.
     */
    @UpdateTimestamp // Hibernate actualiza automáticamente en cada modificación
    @Column(name = "updated_at") // Se puede actualizar (updatable=true por defecto)
    private LocalDateTime updatedAt;

    /**
     * Usuario que creó el registro.
     * En un sistema real, se obtendría del contexto de seguridad.
     */
    @Column(name = "created_by", length = 50)
    private String createdBy;

    /**
     * Usuario que realizó la última actualización.
     */
    @Column(name = "updated_by", length = 50)
    private String updatedBy;

    /**
     // Método que se ejecuta automáticamente antes de persistir la entidad.
     * Se usa para establecer valores por defecto.
     */
    @PrePersist // JPA: se ejecuta antes del INSERT
    protected void onCreate() {
        // Si active es null, establecer como true
        if (active == null) {
            active = true;
        }
        // En producción, obtener el usuario del SecurityContext
        if (createdBy == null) {
            createdBy = "system"; // Usuario por defecto
        }
    }

    /**
     * Método que se ejecuta automáticamente antes de actualizar la entidad.
     */
    @PreUpdate // JPA: se ejecuta antes del UPDATE
    protected void onUpdate() {
        // En producción, obtener el usuario actual del SecurityContext
        if (updatedBy == null) {
            updatedBy = "system"; // Usuario por defecto
        }
    }
}


