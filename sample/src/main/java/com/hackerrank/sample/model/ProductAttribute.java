package com.hackerrank.sample.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad que representa el valor concreto de un atributo para un producto.
 * Actua como vinculo entre un producto especifico y su definicion de atributo.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAttribute {

    /**
     * Identificador unico del valor del atributo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Valor asignado al atributo (ejemplo: 4000mAh, 128GB, Metalico).
     */
    @Column(name = "attr_value")
    private String value;

    /**
     * Producto al cual pertenece este atributo.
     * Se usa JsonBackReference para evitar recursividad infinita con la entidad
     * Product.
     */
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    /**
     * Referencia a la definicion del atributo que describe este valor.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attribute_definition_id")
    private CategoryAttributeDefinition attributeDefinition;
}