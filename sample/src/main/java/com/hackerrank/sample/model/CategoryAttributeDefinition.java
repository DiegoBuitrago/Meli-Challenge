package com.hackerrank.sample.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad que define los nombres de los atributos o caracteristicas
 * dinamicas permitidas para una categoria de productos.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryAttributeDefinition {

    /**
     * Identificador unico de la definicion del atributo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del atributo tecnico (ejemplo: capacidad, resolucion, material).
     */
    private String name;

    /**
     * Categoria a la cual pertenece esta definicion de atributo.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    /**
     * Lista de valores de atributos de productos asociados a esta definicion.
     */
    @OneToMany(mappedBy = "attributeDefinition", fetch = FetchType.LAZY)
    private List<ProductAttribute> productAttributes;
}