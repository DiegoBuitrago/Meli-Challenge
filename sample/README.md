# Product Comparison API

API RESTful desarrollada con **Spring Boot** para comparar productos de un catálogo de e-commerce. Permite consultar productos, obtener sus detalles y compararlos por todos sus atributos o por campos específicos. Este proyecto se toma la decision de ser desarrollado por capas, basado en controller, servicio, respositorio, y modelo. Esta arquitecura asigna responsabilidades a cada una de las capas

## Descripción del proyecto

El sistema simula un catálogo de productos similar a plataformas de e-commerce, donde cada producto pertenece a una categoría y cada categoría define sus propios **atributos dinámicos**.

La API expone funcionalidades para:

- Consultar todos los productos del catálogo
- Consultar el detalle de un producto por su ID
- Comparar múltiples productos con todos sus atributos
- Comparar múltiples productos filtrando únicamente los atributos de interés

---

## Arquitectura

La aplicación sigue una **arquitectura por capas (Layered Architecture)**, un patrón ampliamente adoptado en aplicaciones Spring Boot que favorece la separación de responsabilidades, el mantenimiento y la escalabilidad.

```
HTTP Request
   ↓
Controller Layer: Maneja peticiones HTTP
   ↓
Service Layer: Lógica de negocio
   ↓
Repository Layer: Acceso a datos (JPA)
   ↓
H2 In-Memory Database: Persistencia
```

### Componentes transversales

| Componente | Descripción |
|---|---|
| **DTOs** | Objetos de transferencia entre capas. Evitan exponer entidades directamente |
| **GlobalExceptionHandler** | Centraliza el manejo de errores con `@ControllerAdvice` |
| **Excepciones personalizadas** | `ResourceNotFoundException`, `InvalidComparisonRequestException` |

---

## Modelo de datos

El sistema implementa un modelo de **atributos dinámicos por categoría**, lo que permite que cada tipo de producto tenga sus propias especificaciones técnicas sin alterar el esquema de base de datos.

### Diagrama de contexto UML

El diagrama para entender el problema a solucionar, que fue desarrollado en Lucidchart, se encuentra en el proyecto al mismo nivel del Readme.

### Categorías y atributos disponibles

| Categoría | Atributos dinámicos |
|---|---|
| **Smartphone** | `battery`, `memory`, `storage`, `camera`, `operating_system` |
| **Laptop** | `cpu`, `ram`, `storage`, `gpu`, `operating_system` |
| **Smartwatch** | `battery_life`, `display_type`, `water_resistance`, `connectivity`, `health_features` |

---

## Endpoints de la API

Al mismo nivel de este docuemnto, se encuentra una colección Postman, con las Apis ordenadas y con ejemplos de consumo exitoso.

**Base URL:** `http://localhost:8080/api/products`
**Base URL hackerrank:** `https://vm-5bfcfafe-92d8-4d15-add0-c87439951fb6-8080.us-vmprovider.projects.hrcdn.net/api/products/`

### `GET /api/products` — Obtener todos los productos

Retorna el listado completo de productos con su información base.

**Respuesta `200 OK`:**
```json
[
  {
        "id": 1,
        "name": "iPhone 14",
        "description": "Apple flagship smartphone",
        "price": 999.0,
        "rating": 4.7,
        "imageUrl": "https://example.com/iphone14.jpg",
        "size": "6.1 inch",
        "weight": "172g",
        "color": "Black",
        "category": "Smartphone",
        "specifications": {
            "memory": "6GB",
            "operating_system": "iOS",
            "storage": "128GB",
            "battery": "3279mAh",
            "camera": "12MP Dual Camera"
        }
    }
]
```

---

### `GET /api/products/{id}` — Obtener producto por ID

Retorna el detalle completo de un producto específico.

**Ejemplo:** `GET /api/products/7`

**Respuesta `200 OK`:**
```json
{
    "id": 7,
    "name": "Apple Watch Series 9",
    "description": "Apple smartwatch with health tracking",
    "price": 499.0,
    "rating": 4.8,
    "imageUrl": "https://example.com/applewatch9.jpg",
    "size": "45mm",
    "weight": "51g",
    "color": "Midnight",
    "category": "Smartwatch",
    "specifications": {
        "display_type": "OLED",
        "water_resistance": "50m",
        "health_features": "ECG, Heart Rate",
        "connectivity": "Bluetooth/WiFi",
        "battery_life": "18 hours"
    }
}
```

**Respuesta `404 Not Found`:**
```json
{
  "status": 404,
  "message": "Product not found with id: 99",
  "timestamp": "2026-03-10T21:15:03"
}
```

---

### `GET /api/products/compare?ids={id1},{id2},...` — Comparar productos

Compara múltiples productos devolviendo todos sus atributos dinámicos.
Requiere **mínimo 2 IDs**.

**Ejemplo:** `GET /api/products/compare?ids=1,5,7`

**Respuesta `200 OK`:**
```json
{
    "products": [
        {
            "id": 1,
            "name": "iPhone 14",
            "imageUrl": "https://example.com/iphone14.jpg",
            "description": "Apple flagship smartphone",
            "price": 999.0,
            "rating": 4.7,
            "specifications": {
                "memory": "6GB",
                "operating_system": "iOS",
                "storage": "128GB",
                "battery": "3279mAh",
                "camera": "12MP Dual Camera"
            }
        },
        {
            "id": 5,
            "name": "Dell XPS 13",
            "imageUrl": "https://example.com/xps13.jpg",
            "description": "Dell ultrabook laptop",
            "price": 1999.0,
            "rating": 4.6,
            "specifications": {
                "cpu": "Intel i7",
                "operating_system": "Windows 11",
                "storage": "1TB SSD",
                "gpu": "Intel Iris Xe",
                "ram": "16GB"
            }
        },
        {
            "id": 7,
            "name": "Apple Watch Series 9",
            "imageUrl": "https://example.com/applewatch9.jpg",
            "description": "Apple smartwatch with health tracking",
            "price": 499.0,
            "rating": 4.8,
            "specifications": {
                "display_type": "OLED",
                "water_resistance": "50m",
                "health_features": "ECG, Heart Rate",
                "connectivity": "Bluetooth/WiFi",
                "battery_life": "18 hours"
            }
        }
    ]
}
```

---

### `GET /api/products/compare/fields?ids={id1},{id2}&fields={field1},{field2}` — Comparar con campos específicos

Permite enfocarse únicamente en los atributos relevantes para el análisis.
Requiere **mínimo 2 IDs** y al menos un campo.

**Ejemplo:** `GET /api/products/compare/fields?ids=1,2,8&fields=battery,battery_life,storage,camera,ram`

**Respuesta `200 OK`:**
```json
{
    "products": [
        {
            "id": 1,
            "name": "iPhone 14",
            "imageUrl": "https://example.com/iphone14.jpg",
            "description": "Apple flagship smartphone",
            "price": 999.0,
            "rating": 4.7,
            "specifications": {
                "storage": "128GB",
                "battery": "3279mAh",
                "camera": "12MP Dual Camera"
            }
        },
        {
            "id": 2,
            "name": "Samsung Galaxy S23",
            "imageUrl": "https://example.com/s23.jpg",
            "description": "Samsung premium smartphone",
            "price": 899.0,
            "rating": 4.6,
            "specifications": {
                "storage": "256GB",
                "battery": "3900mAh",
                "camera": "50MP Triple Camera"
            }
        },
        {
            "id": 8,
            "name": "Samsung Galaxy Watch 6",
            "imageUrl": "https://example.com/watch6.jpg",
            "description": "Samsung smartwatch with fitness tracking",
            "price": 399.0,
            "rating": 4.6,
            "specifications": {
                "battery_life": "40 hours"
            }
        }
    ]
}
```

---

## Tecnologías

| Tecnología | Versión | Uso |
|---|---|---|
| **Java** | 21 | Lenguaje principal |
| **Spring Boot** | 3.x | Framework base |
| **Spring Web** | — | Exposición de endpoints REST |
| **Spring Data JPA** | — | ORM y acceso a datos |
| **H2 Database** | — | Base de datos en memoria |
| **Lombok** | — | Reducción de boilerplate |
| **Maven** | 3.9.9 | Gestión de dependencias |
| **JUnit 5** | — | Framework de pruebas |
| **Mockito** | — | Mocking en pruebas unitarias |
| **AssertJ** | — | Aserciones fluidas |
| **MockMvc** | — | Pruebas de controladores |

---

## Cómo ejecutar el proyecto

### Requisitos previos

- Java 17+
- Maven 3.6+

### 1. Clonar el repositorio

```bash
git clone <repository-url>
cd product-comparison-api
```

### 2. Compilar el proyecto

```bash
mvn clean install
```

### 3. Ejecutar la aplicación

```bash
mvn spring-boot:run
```

La API quedará disponible en: **`http://localhost:8080`**
o si la prueba se hace en HackerRank, unicamente es ejecutar el sistema.
**`https://vm-5bfcfafe-92d8-4d15-add0-c87439951fb6-8080.us-vmprovider.projects.hrcdn.net/`**

### 4. Consola H2 (opcional)

Para inspeccionar la base de datos en memoria durante el desarrollo:

```
URL:      http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:testdb
Usuario:  sa
Password: (vacío)
```

---

## Catálogo de productos de prueba

La base de datos se inicializa automáticamente con **9 productos** en 3 categorías, cargados desde `src/main/resources/data.sql`.

### Smartphones (IDs: 1, 2, 3)

| ID | Producto | Precio | Rating |
|---|---|---|---|
| 1 | iPhone 14 | $999 | 4.7 |
| 2 | Samsung Galaxy S23 | $899 | 4.6 |
| 3 | Google Pixel 8 | $799 | 4.5 |

### Laptops (IDs: 4, 5, 6)

| ID | Producto | Precio | Rating |
|---|---|---|---|
| 4 | MacBook Pro M3 | $2,499 | 4.8 |
| 5 | Dell XPS 13 | $1,999 | 4.6 |
| 6 | Lenovo ThinkPad X1 | $1,799 | 4.5 |

### Smartwatches (IDs: 7, 8, 9)

| ID | Producto | Precio | Rating |
|---|---|---|---|
| 7 | Apple Watch Series 9 | $499 | 4.8 |
| 8 | Samsung Galaxy Watch 6 | $399 | 4.6 |
| 9 | Garmin Venu 3 | $449 | 4.7 |

### Ejemplos de consultas útiles

```bash
# Comparar los tres smartphones
GET /api/products/compare?ids=1,2,3

# Comparar laptops solo por CPU y RAM
GET /api/products/compare/fields?ids=4,5,6&fields=cpu,ram

# Comparar smartwatches por batería y pantalla
GET /api/products/compare/fields?ids=7,8,9&fields=battery_life,display_type

# Ver detalle completo del MacBook Pro
GET /api/products/4
```

---

## Manejo de errores

La API retorna respuestas de error estructuradas y consistentes en todos los endpoints.

### Estructura de error

```json
{
  "status": 404,
  "message": "Product not found with id: 99",
  "timestamp": "2026-03-10T21:15:03"
}
```

### Tabla de errores

| Código HTTP | Excepción | Causa |
|---|---|---|
| `400 Bad Request` | `InvalidComparisonRequestException` | Se envían menos de 2 IDs en la comparación |
| `404 Not Found` | `ResourceNotFoundException` | El ID del producto no existe en la base de datos |
| `500 Internal Server Error` | `Exception` | Error inesperado en el servidor |

---

## Pruebas

Para ejecutar la suite completa de pruebas:

```bash
mvn test
```

El proyecto incluye pruebas para las capas **Controller** y **Service**.

| Herramienta | Propósito |
|---|---|
| **JUnit 5** | Framework base de pruebas |
| **Mockito** | Simulación de dependencias entre capas |
| **AssertJ** | Aserciones legibles y expresivas |
| **MockMvc** | Pruebas de integración de endpoints HTTP |

---

## Decisiones arquitectónicas

### 1. Atributos dinámicos por categoría

En lugar de crear una tabla separada por cada tipo de producto, se implementó un modelo de **atributos clave-valor** controlado por `CategoryAttributeDefinition`. Esto permite agregar nuevas categorías con sus propios atributos sin modificar el esquema de base de datos ni el código fuente.

### 2. Consulta optimizada con JOIN FETCH

Para evitar el problema de **N+1 queries** al cargar los atributos de múltiples productos, se implementó una consulta JPQL personalizada con `LEFT JOIN FETCH`. Esto garantiza que todos los datos necesarios para la comparación se obtengan en **una sola consulta**.

```java
@Query("""
    SELECT DISTINCT p
    FROM Product p
    LEFT JOIN FETCH p.attributes pa
    LEFT JOIN FETCH pa.attributeDefinition
    WHERE p.id IN :ids
""")
List<Product> findProductsWithAttributes(@Param("ids") List<Long> ids);
```

### 3. Separación entre entidades y DTOs

Las entidades JPA nunca se exponen directamente en la API. Se utilizan DTOs (`ProductDetailDTO`, `ProductComparisonDTO`) para controlar exactamente qué campos se serializan, desacoplando el modelo de persistencia del contrato público de la API.

### 4. Manejo centralizado de errores

Se implementó un `@ControllerAdvice` global (`GlobalExceptionHandler`) que intercepta excepciones personalizadas y genéricas, retornando siempre una respuesta con **estructura uniforme**. Esto evita que stack traces o mensajes internos lleguen al cliente.

### 5. Interfaz de servicio

La capa de servicio está definida mediante la interfaz `ProductService` e implementada en `ProductServiceImpl`. Este patrón facilita la escritura de pruebas unitarias con mocks y permite cambiar la implementación sin impactar al controlador. Estamdesición se alinea con los patrones SOLID.

---

## Autor

Diego Buitrago - Ingeniero de Sistemas y Computación.
Implementación backend desarrollada como parte de una prueba técnica para MercadoLibre.