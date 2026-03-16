package com.hackerrank.sample.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hackerrank.sample.model.Product;

/**
 * Repositorio para la gestion de persistencia de la entidad Product.
 * Proporciona metodos de acceso a datos utilizando Spring Data JPA.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Obtiene productos junto con sus atributos y definiciones de atributos
     * para evitar el problema N+1 al realizar comparaciones.
     * Se ejecuta una sola consulta a la base de datos mediante el uso de FETCH
     * JOIN.
     * La clausula DISTINCT asegura que no se retornen registros duplicados.
     *
     * @param ids Lista de identificadores de los productos a recuperar.
     * @return Lista de productos con su informacion relacionada cargada de forma
     *         optima.
     */
    @Query("""
                SELECT DISTINCT p
                FROM Product p
                LEFT JOIN FETCH p.attributes pa
                LEFT JOIN FETCH pa.attributeDefinition
                WHERE p.id IN :ids
            """)
    List<Product> findProductsWithAttributes(@Param("ids") List<Long> ids);
}