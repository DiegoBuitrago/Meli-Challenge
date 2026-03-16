package com.hackerrank.sample.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hackerrank.sample.dto.ProductComparisonResponse;
import com.hackerrank.sample.dto.ProductDetailDTO;
import com.hackerrank.sample.service.ProductService;

import lombok.RequiredArgsConstructor;

/**
 * Controlador REST que gestiona las operaciones relacionadas con productos.
 * Provee endpoints para consulta individual, listado total y comparacion
 * avanzada.
 */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * Obtiene la lista de todos los productos disponibles en el sistema.
     * * @return Lista de objetos ProductDetailDTO con la informacion detallada.
     */
    @GetMapping
    public List<ProductDetailDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    /**
     * Recupera el detalle de un producto especifico utilizando su identificador.
     * * @param id Identificador unico del producto a consultar.
     * 
     * @return Objeto ProductDetailDTO con los datos del producto encontrado.
     */
    @GetMapping("/{id}")
    public ProductDetailDTO getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    /**
     * Endpoint para comparar multiples productos de forma simultanea.
     *
     * Recibe una lista de identificadores y devuelve los detalles de cada producto
     * junto con sus especificaciones dinamicas para facilitar el analisis
     * comparativo.
     *
     * Ejemplo de uso: GET /api/products/compare?ids=1,2
     * * @param ids Lista de identificadores de los productos a comparar.
     * 
     * @return Respuesta estructurada con los productos y sus atributos de
     *         comparacion.
     */
    @GetMapping("/compare")
    public ProductComparisonResponse compareProducts(@RequestParam List<Long> ids) {
        return productService.compareProducts(ids);
    }

    /**
     * Realiza una comparacion de productos filtrando unicamente atributos
     * especificos.
     * Permite optimizar la respuesta devolviendo solo los campos de interes para el
     * usuario.
     *
     * Ejemplo de uso: /api/products/compare/fields?ids=1,2&fields=battery,memory
     * * @param ids Lista de identificadores de los productos.
     * 
     * @param fields Lista de nombres de los campos o atributos que se desean
     *               comparar.
     * @return Respuesta que contiene solo la informacion de los campos
     *         seleccionados.
     */
    @GetMapping("/compare/fields")
    public ProductComparisonResponse compareProductsWithFields(@RequestParam List<Long> ids,
            @RequestParam List<String> fields) {
        return productService.compareProductsWithFields(ids, fields);
    }
}