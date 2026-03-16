package com.hackerrank.sample.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Objeto de transferencia de datos utilizado para representar la informacion
 * de un producto dentro de una respuesta de comparacion.
 * Incluye datos basicos y un mapa de atributos dinamicos.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductComparisonDTO {

    /**
     * Identificador unico del producto.
     */
    private Long id;

    /**
     * Nombre del producto.
     */
    private String name;

    /**
     * URL de la imagen del producto.
     */
    private String imageUrl;

    /**
     * Descripcion comercial del producto.
     */
    private String description;

    /**
     * Precio del producto.
     */
    private Double price;

    /**
     * Calificacion o rating del producto.
     */
    private Double rating;

    /**
     * Especificaciones dinamicas del producto.
     * key = nombre del atributo
     * value = valor del atributo
     */
    private Map<String, String> specifications;

}