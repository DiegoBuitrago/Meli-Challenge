package com.hackerrank.sample.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hackerrank.sample.dto.ProductComparisonDTO;
import com.hackerrank.sample.dto.ProductComparisonResponse;
import com.hackerrank.sample.dto.ProductDetailDTO;
import com.hackerrank.sample.exception.InvalidComparisonRequestException;
import com.hackerrank.sample.exception.ResourceNotFoundException;
import com.hackerrank.sample.model.Product;
import com.hackerrank.sample.model.ProductAttribute;
import com.hackerrank.sample.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

/**
 * Implementacion de la interfaz de servicios para la gestion de productos.
 * Contiene la logica de negocio para consultas, validaciones y
 * transformaciones.
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

        private final ProductRepository productRepository;

        /**
         * Recupera todos los productos y los transforma a formato DTO detallado.
         * 
         * @return Lista de ProductDetailDTO con la informacion completa.
         */
        @Override
        public List<ProductDetailDTO> getAllProducts() {

                List<Product> products = productRepository.findAll();

                return products.stream()
                                .map(this::mapToProductDetailDTO)
                                .toList();
        }

        /**
         * Busca un producto por su identificador unico.
         * 
         * @param id Identificador del producto.
         * @return Detalle del producto encontrado.
         * @throws ResourceNotFoundException si el id no existe.
         */
        @Override
        public ProductDetailDTO getProductById(Long id) {

                Product product = productRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

                return mapToProductDetailDTO(product);
        }

        /**
         * Obtiene productos con sus atributos y construye la estructura de comparacion.
         * Verifica que existan al menos dos identificadores y que todos sean validos.
         * 
         * @param ids Lista de identificadores a comparar.
         * @return Respuesta estructurada para la comparativa.
         */
        @Override
        public ProductComparisonResponse compareProducts(List<Long> ids) {

                // Validacion de solicitud
                if (ids == null || ids.size() < 2) {
                        throw new InvalidComparisonRequestException(
                                        "At least two product IDs are required for comparison");
                }

                // Consulta optimizada con JOIN FETCH
                List<Product> products = productRepository.findProductsWithAttributes(ids);

                // Validacion: ningun producto encontrado
                if (products.isEmpty()) {
                        throw new ResourceNotFoundException(
                                        "No products found for the provided IDs");
                }

                // Validacion: algunos IDs no existen
                if (products.size() != ids.size()) {
                        throw new ResourceNotFoundException(
                                        "Some products were not found for the provided IDs: " + ids);
                }

                // Transformacion de entidades a DTO
                List<ProductComparisonDTO> productDTOs = products.stream()
                                .map(this::mapToDTO)
                                .toList();

                // Construccion de la respuesta final
                return ProductComparisonResponse.builder()
                                .products(productDTOs)
                                .build();
        }

        /**
         * Compara productos filtrando atributos especificos segun una lista de campos.
         * 
         * @param ids    Lista de identificadores de productos.
         * @param fields Nombres de los atributos dinamicos a incluir.
         * @return Respuesta de comparacion con datos filtrados.
         */
        @Override
        public ProductComparisonResponse compareProductsWithFields(List<Long> ids, List<String> fields) {

                if (ids == null || ids.size() < 2) {
                        throw new InvalidComparisonRequestException(
                                        "At least two product IDs are required for comparison");
                }

                List<Product> products = productRepository.findProductsWithAttributes(ids);

                // Validacion: ningun producto encontrado
                if (products.isEmpty()) {
                        throw new ResourceNotFoundException(
                                        "No products found for the provided IDs");
                }

                // Validacion: algunos IDs no existen
                if (products.size() != ids.size()) {
                        throw new ResourceNotFoundException(
                                        "Some products were not found for the provided IDs: " + ids);
                }

                List<ProductComparisonDTO> productDTOs = products.stream()
                                .map(product -> mapToDTOFiltered(product, fields))
                                .toList();

                return ProductComparisonResponse.builder()
                                .products(productDTOs)
                                .build();
        }

        /**
         * Metodo privado para mapear la entidad Product a ProductDetailDTO.
         * Convierte los atributos dinamicos en un mapa de especificaciones.
         */
        private ProductDetailDTO mapToProductDetailDTO(Product product) {

                Map<String, String> specs = product.getAttributes()
                                .stream()
                                .collect(Collectors.toMap(
                                                attr -> attr.getAttributeDefinition().getName(),
                                                ProductAttribute::getValue));

                return ProductDetailDTO.builder()
                                .id(product.getId())
                                .name(product.getName())
                                .description(product.getDescription())
                                .price(product.getPrice())
                                .rating(product.getRating())
                                .imageUrl(product.getImageUrl())
                                .size(product.getSize())
                                .weight(product.getWeight())
                                .color(product.getColor())
                                .category(product.getCategory().getName())
                                .specifications(specs)
                                .build();
        }

        /**
         * Convierte una entidad Product en DTO de comparacion basico.
         */
        private ProductComparisonDTO mapToDTO(Product product) {
                // Transformamos los atributos dinamicos en un Map
                Map<String, String> specs = product.getAttributes()
                                .stream()
                                .collect(Collectors.toMap(
                                                attr -> attr.getAttributeDefinition().getName(),
                                                ProductAttribute::getValue));

                return ProductComparisonDTO.builder()
                                .id(product.getId())
                                .name(product.getName())
                                .imageUrl(product.getImageUrl())
                                .description(product.getDescription())
                                .price(product.getPrice())
                                .rating(product.getRating())
                                .specifications(specs)
                                .build();
        }

        /**
         * Convierte una entidad Product en DTO de comparacion filtrando los atributos.
         */
        private ProductComparisonDTO mapToDTOFiltered(Product product, List<String> fields) {

                Map<String, String> specs = product.getAttributes()
                                .stream()
                                .filter(attr -> fields.contains(
                                                attr.getAttributeDefinition().getName()))
                                .collect(Collectors.toMap(
                                                attr -> attr.getAttributeDefinition().getName(),
                                                ProductAttribute::getValue));

                return ProductComparisonDTO.builder()
                                .id(product.getId())
                                .name(product.getName())
                                .imageUrl(product.getImageUrl())
                                .description(product.getDescription())
                                .price(product.getPrice())
                                .rating(product.getRating())
                                .specifications(specs)
                                .build();
        }
}