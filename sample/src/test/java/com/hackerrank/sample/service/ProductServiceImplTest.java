package com.hackerrank.sample.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hackerrank.sample.dto.ProductComparisonResponse;
import com.hackerrank.sample.dto.ProductDetailDTO;
import com.hackerrank.sample.exception.InvalidComparisonRequestException;
import com.hackerrank.sample.exception.ResourceNotFoundException;
import com.hackerrank.sample.model.Product;
import com.hackerrank.sample.model.Category;
import com.hackerrank.sample.repository.ProductRepository;

/**
 * Pruebas unitarias para la implementacion del servicio de productos.
 * Utiliza Mockito para aislar la logica de negocio de la capa de persistencia.
 */
@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

  @Mock
  private ProductRepository productRepository;

  @InjectMocks
  private ProductServiceImpl productService;

  private Product iphone;
  private Product samsung;

  /**
   * Configuracion previa a cada prueba.
   * Inicializa entidades de prueba y sus relaciones basicas.
   */
  @BeforeEach
  void setup() {

    Category smartphoneCategory = new Category();
    smartphoneCategory.setId(1L);
    smartphoneCategory.setName("Smartphone");

    iphone = new Product();
    iphone.setId(1L);
    iphone.setName("iPhone 14");
    iphone.setPrice(999.0);
    iphone.setCategory(smartphoneCategory);
    iphone.setAttributes(new ArrayList<>());

    samsung = new Product();
    samsung.setId(2L);
    samsung.setName("Samsung Galaxy S23");
    samsung.setPrice(899.0);
    samsung.setCategory(smartphoneCategory);
    samsung.setAttributes(new ArrayList<>());

  }

  /**
   * Verifica que se retornen todos los productos convertidos correctamente a DTO.
   */
  @Test
  void shouldReturnAllProducts() {

    when(productRepository.findAll())
        .thenReturn(List.of(iphone, samsung));

    List<ProductDetailDTO> result = productService.getAllProducts();

    assertThat(result)
        .hasSize(2)
        .extracting(ProductDetailDTO::getName)
        .contains("iPhone 14", "Samsung Galaxy S23");
  }

  /**
   * Valida que el servicio maneje correctamente una lista vacia de productos.
   */
  @Test
  void shouldReturnEmptyListWhenNoProducts() {

    when(productRepository.findAll())
        .thenReturn(List.of());

    List<ProductDetailDTO> result = productService.getAllProducts();

    assertThat(result).isEmpty();
  }

  /**
   * Prueba la busqueda exitosa de un producto por su identificador.
   */
  @Test
  void shouldReturnProductById() {

    when(productRepository.findById(1L))
        .thenReturn(Optional.of(iphone));

    ProductDetailDTO result = productService.getProductById(1L);

    assertThat(result.getId()).isEqualTo(1L);
    assertThat(result.getName()).isEqualTo("iPhone 14");
  }

  /**
   * Verifica que se lance una excepcion cuando el producto buscado no existe.
   */
  @Test
  void shouldThrowExceptionWhenProductNotFound() {

    when(productRepository.findById(99L))
        .thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class,
        () -> productService.getProductById(99L));
  }

  /**
   * Valida el proceso de comparacion exitoso de dos productos existentes.
   */
  @Test
  void shouldCompareProductsSuccessfully() {

    when(productRepository.findProductsWithAttributes(List.of(1L, 2L)))
        .thenReturn(List.of(iphone, samsung));

    ProductComparisonResponse response = productService.compareProducts(List.of(1L, 2L));

    assertThat(response.getProducts())
        .hasSize(2);
  }

  /**
   * Verifica que se rechace una solicitud de comparacion con menos de dos
   * identificadores.
   */
  @Test
  void shouldThrowExceptionWhenLessThanTwoProducts() {

    assertThrows(InvalidComparisonRequestException.class,
        () -> productService.compareProducts(List.of(1L)));
  }

  /**
   * Valida que se lance una excepcion si los productos a comparar no se hallan en
   * la base de datos.
   */
  @Test
  void shouldThrowExceptionWhenProductsNotFound() {

    when(productRepository.findProductsWithAttributes(List.of(10L, 20L)))
        .thenReturn(List.of());

    assertThrows(ResourceNotFoundException.class,
        () -> productService.compareProducts(List.of(10L, 20L)));
  }

  /**
   * Prueba la comparacion de productos filtrando por campos especificos.
   */
  @Test
  void shouldCompareProductsWithFields() {

    when(productRepository.findProductsWithAttributes(List.of(1L, 2L)))
        .thenReturn(List.of(iphone, samsung));

    ProductComparisonResponse response = productService.compareProductsWithFields(
        List.of(1L, 2L),
        List.of("battery", "memory"));

    assertThat(response.getProducts())
        .hasSize(2);
  }
}