package com.hackerrank.sample.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.hackerrank.sample.dto.ProductComparisonDTO;
import com.hackerrank.sample.dto.ProductComparisonResponse;
import com.hackerrank.sample.dto.ProductDetailDTO;
import com.hackerrank.sample.exception.InvalidComparisonRequestException;
import com.hackerrank.sample.exception.ResourceNotFoundException;
import com.hackerrank.sample.service.ProductService;

/**
 * Clase de pruebas unitarias para el controlador de productos.
 * Utiliza MockMvc para simular peticiones HTTP y verificar el comportamiento de
 * los endpoints.
 */
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private ProductDetailDTO iphone;
    private ProductDetailDTO samsung;

    private ProductComparisonDTO iphoneComparison;
    private ProductComparisonDTO samsungComparison;

    private ProductComparisonResponse comparisonResponse;

    /**
     * Configuracion inicial para los escenarios de prueba.
     * Crea objetos de prueba (Dummies) para simular respuestas del servicio.
     */
    @BeforeEach
    void setup() {

        iphone = ProductDetailDTO.builder()
                .id(1L)
                .name("iPhone 14")
                .price(999.0)
                .category("Smartphone")
                .specifications(Map.of("battery", "3279mAh"))
                .build();

        samsung = ProductDetailDTO.builder()
                .id(2L)
                .name("Samsung Galaxy S23")
                .price(899.0)
                .category("Smartphone")
                .specifications(Map.of("battery", "3900mAh"))
                .build();

        iphoneComparison = ProductComparisonDTO.builder()
                .id(1L)
                .name("iPhone 14")
                .price(999.0)
                .specifications(Map.of("battery", "3279mAh", "memory", "6GB"))
                .build();

        samsungComparison = ProductComparisonDTO.builder()
                .id(2L)
                .name("Samsung Galaxy S23")
                .price(899.0)
                .specifications(Map.of("battery", "3900mAh", "memory", "8GB"))
                .build();

        comparisonResponse = ProductComparisonResponse.builder()
                .products(List.of(iphoneComparison, samsungComparison))
                .build();
    }

    /**
     * Prueba el endpoint GET /api/products para retornar todos los productos.
     * Verifica que el estado sea OK y que el contenido del JSON sea el esperado.
     */
    @Test
    void shouldReturnAllProducts() throws Exception {

        when(productService.getAllProducts())
                .thenReturn(List.of(iphone, samsung));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("iPhone 14"))
                .andExpect(jsonPath("$[1].name").value("Samsung Galaxy S23"));

        verify(productService).getAllProducts();
    }

    /**
     * Verifica el comportamiento cuando no existen productos registrados.
     * Debe retornar una lista vacia con estado 200 OK.
     */
    @Test
    void shouldReturnEmptyListWhenNoProducts() throws Exception {

        when(productService.getAllProducts())
                .thenReturn(List.of());

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    /**
     * Prueba la obtencion de un producto mediante su identificador unico.
     */
    @Test
    void shouldReturnProductById() throws Exception {

        when(productService.getProductById(1L))
                .thenReturn(iphone);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("iPhone 14"));

        verify(productService).getProductById(1L);
    }

    /**
     * Valida la respuesta de error 404 cuando el producto solicitado no existe.
     */
    @Test
    void shouldReturn404WhenProductNotFound() throws Exception {

        when(productService.getProductById(99L))
                .thenThrow(new ResourceNotFoundException("Product not found"));

        mockMvc.perform(get("/api/products/99"))
                .andExpect(status().isNotFound());
    }

    /**
     * Verifica que el endpoint de comparacion devuelva los productos solicitados.
     */
    @Test
    void shouldCompareProducts() throws Exception {

        when(productService.compareProducts(List.of(1L, 2L)))
                .thenReturn(comparisonResponse);

        mockMvc.perform(get("/api/products/compare")
                .param("ids", "1", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products.length()").value(2));

        verify(productService).compareProducts(List.of(1L, 2L));
    }

    /**
     * Valida error Bad Request cuando se intentan comparar menos de dos productos.
     */
    @Test
    void shouldReturnBadRequestWhenLessThanTwoProducts() throws Exception {

        when(productService.compareProducts(List.of(1L)))
                .thenThrow(new InvalidComparisonRequestException("Invalid request"));

        mockMvc.perform(get("/api/products/compare")
                .param("ids", "1"))
                .andExpect(status().isBadRequest());
    }

    /**
     * Verifica error 404 en comparacion cuando los identificadores no existen.
     */
    @Test
    void shouldReturn404WhenProductsDoNotExist() throws Exception {

        when(productService.compareProducts(List.of(100L, 200L)))
                .thenThrow(new ResourceNotFoundException("Products not found"));

        mockMvc.perform(get("/api/products/compare")
                .param("ids", "100", "200"))
                .andExpect(status().isNotFound());
    }

    /**
     * Prueba la comparacion de productos filtrando por campos especificos.
     */
    @Test
    void shouldCompareProductsWithFields() throws Exception {

        when(productService.compareProductsWithFields(
                List.of(1L, 2L),
                List.of("battery", "memory")))
                .thenReturn(comparisonResponse);

        mockMvc.perform(get("/api/products/compare/fields")
                .param("ids", "1", "2")
                .param("fields", "battery", "memory"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products.length()").value(2))
                .andExpect(jsonPath("$.products[0].specifications.battery").value("3279mAh"))
                .andExpect(jsonPath("$.products[1].specifications.memory").value("8GB"));
    }

    /**
     * Valida que se devuelvan especificaciones vacias si el campo solicitado no
     * existe.
     */
    @Test
    void shouldReturnEmptySpecificationsWhenFieldDoesNotExist() throws Exception {

        ProductComparisonResponse emptyResponse = ProductComparisonResponse.builder()
                .products(List.of(
                        ProductComparisonDTO.builder()
                                .id(1L)
                                .name("iPhone 14")
                                .specifications(Map.of())
                                .build()))
                .build();

        when(productService.compareProductsWithFields(
                List.of(1L, 2L),
                List.of("invalid_field")))
                .thenReturn(emptyResponse);

        mockMvc.perform(get("/api/products/compare/fields")
                .param("ids", "1", "2")
                .param("fields", "invalid_field"))
                .andExpect(status().isOk());
    }
}