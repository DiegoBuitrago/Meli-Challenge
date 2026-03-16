package com.hackerrank.sample.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
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
 * Entidad principal que representa un producto en el catalogo.
 * Contiene informacion basica del articulo y su relacion con categorias
 * y atributos dinamicos.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    /**
     * Identificador unico del producto.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre comercial del producto.
     */
    private String name;

    /**
     * Descripcion detallada de las caracteristicas del producto.
     */
    private String description;

    /**
     * Precio de venta al publico.
     */
    private Double price;

    /**
     * Calificacion promedio otorgada por los usuarios.
     */
    private Double rating;

    /**
     * Enlace o ruta de la imagen representativa del producto.
     */
    private String imageUrl;

    /**
     * Dimensiones o talla del producto.
     */
    private String size;

    /**
     * Peso del producto en unidades estandar.
     */
    private String weight;

    /**
     * Color o acabado del producto.
     */
    private String color;

    /**
     * Categoria a la que pertenece el producto.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    /**
     * Lista de atributos dinamicos asociados al producto.
     * Utiliza JsonManagedReference para evitar ciclos en la serializacion.
     */
    @JsonManagedReference
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProductAttribute> attributes;
}