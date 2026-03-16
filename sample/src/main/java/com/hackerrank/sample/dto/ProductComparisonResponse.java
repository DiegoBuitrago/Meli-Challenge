package com.hackerrank.sample.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto que representa la respuesta final en una operacion de comparacion.
 * Agrupa una lista de productos con sus respectivos detalles y atributos
 * para ser enviados al cliente.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductComparisonResponse {
    /**
     * Lista de productos procesados para la comparativa.
     */
    private List<ProductComparisonDTO> products;
}