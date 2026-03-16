package com.hackerrank.sample.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto de transferencia de datos que contiene el detalle completo de un
 * producto.
 * Se utiliza para mostrar la informacion pormenorizada incluyendo datos base,
 * clasificacion y atributos dinamicos.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailDTO {

    /**
     * Identificador unico del producto.
     */
    private Long id;

    /**
     * Nombre del producto.
     */
    private String name;

    /**
     * Resumen de las caracteristicas del producto.
     */
    private String description;

    /**
     * Costo o valor monetario.
     */
    private Double price;

    /**
     * Puntaje de satisfaccion del cliente.
     */
    private Double rating;

    /**
     * Direccion de la imagen del producto.
     */
    private String imageUrl;

    /**
     * Medidas o dimensiones fisicas.
     */
    private String size;

    /**
     * Masa del producto.
     */
    private String weight;

    /**
     * Variedad cromatica del articulo.
     */
    private String color;

    /**
     * Nombre de la categoria a la que pertenece.
     */
    private String category;

    /**
     * Mapa de caracteristicas tecnicas dinamicas.
     */
    private Map<String, String> specifications;
}