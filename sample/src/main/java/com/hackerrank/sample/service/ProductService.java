package com.hackerrank.sample.service;

import java.util.List;

import com.hackerrank.sample.dto.ProductComparisonResponse;
import com.hackerrank.sample.dto.ProductDetailDTO;

/**
 * Interfaz que define los contratos para la gestion y comparacion de productos.
 * Proporciona metodos para obtener detalles individuales y realizar
 * comparativas.
 */
public interface ProductService {
    /**
     * Obtiene la comparacion entre multiples productos.
     *
     * @param ids lista de identificadores de productos a comparar
     * @return estructura de comparacion de productos
     */
    ProductComparisonResponse compareProducts(List<Long> ids);

    /**
     * Obtiene la comparacion entre multiples productos filtrado por
     * especificaciones.
     *
     * @param ids    lista de identificadores de productos a comparar
     * @param fields lista de especificaciones o atributos de productos a comparar
     * @return estructura de comparacion de productos con campos filtrados
     */
    ProductComparisonResponse compareProductsWithFields(List<Long> ids, List<String> fields);

    /**
     * Recupera el listado completo de productos en formato DTO.
     * 
     * @return lista con todos los productos registrados
     */
    List<ProductDetailDTO> getAllProducts();

    /**
     * Busca la informacion detallada de un producto por su identificador unico.
     * 
     * @param id identificador del producto
     * @return objeto con el detalle del producto
     */
    ProductDetailDTO getProductById(Long id);
}