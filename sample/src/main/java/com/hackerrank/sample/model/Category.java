package com.hackerrank.sample.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad que representa una categoria de productos dentro del sistema.
 * Permite agrupar productos y definir que atributos especificos tendran
 * segun su clasificacion.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    /**
     * Identificador unico de la categoria.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre descriptivo de la categoria.
     */
    private String name;

    /**
     * Relacion con las definiciones de atributos permitidos para esta categoria.
     * Incluye gestion en cascada para operaciones de persistencia.
     */
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CategoryAttributeDefinition> attributeDefinitions;

    /**
     * Listado de productos que pertenecen a esta categoria.
     */
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Product> products;
}