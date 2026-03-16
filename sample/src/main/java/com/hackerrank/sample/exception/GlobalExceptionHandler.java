package com.hackerrank.sample.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

/**
 * Clase encargada de capturar y gestionar las excepciones lanzadas por los
 * controladores.
 * Centraliza el manejo de errores para devolver respuestas uniformes al
 * usuario.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

        /**
         * Maneja solicitudes invalidas para comparacion de productos.
         * Se activa cuando los parametros de la comparativa no cumplen los requisitos.
         * 
         * @param ex Excepcion de solicitud de comparacion invalida.
         * @return ResponseEntity con el detalle del error y estado BAD_REQUEST.
         */
        @ExceptionHandler(InvalidComparisonRequestException.class)
        public ResponseEntity<ErrorResponse> handleInvalidComparison(
                        InvalidComparisonRequestException ex) {

                ErrorResponse error = ErrorResponse.builder()
                                .status(HttpStatus.BAD_REQUEST.value())
                                .message(ex.getMessage())
                                .timestamp(LocalDateTime.now())
                                .build();

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        /**
         * Maneja situaciones donde no se encuentran los recursos solicitados.
         * Captura la excepcion cuando un ID de producto no existe en la base de datos.
         * 
         * @param ex Excepcion de recurso no encontrado.
         * @return ResponseEntity con el detalle del error y estado NOT_FOUND.
         */
        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleNotFound(
                        ResourceNotFoundException ex) {

                ErrorResponse error = ErrorResponse.builder()
                                .status(HttpStatus.NOT_FOUND.value())
                                .message(ex.getMessage())
                                .timestamp(LocalDateTime.now())
                                .build();

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        /**
         * Manejo generico de errores inesperados en el servidor.
         * Actua como una red de seguridad para cualquier excepcion no controlada
         * especificamente.
         * 
         * @param ex Excepcion general capturada.
         * @return ResponseEntity con el detalle del error y estado
         *         INTERNAL_SERVER_ERROR.
         */
        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {

                ErrorResponse error = ErrorResponse.builder()
                                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .message(ex.getMessage())
                                .timestamp(LocalDateTime.now())
                                .build();

                return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }


        /**
         * Captura errores de @Validated en @RequestParam y @PathVariable.
         * Ejemplo: @Size(min=2) en List<Long> ids
         */
        @ExceptionHandler(ConstraintViolationException.class)
        public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {

                // Extrae el primer mensaje de violación
                String message = ex.getConstraintViolations()
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .findFirst()
                        .orElse("Parámetros de entrada inválidos");

                ErrorResponse error = ErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message(message)
                        .timestamp(LocalDateTime.now())
                        .build();

                return ResponseEntity.badRequest().body(error);
        }

        /**
         * Captura errores de @Valid sobre un @RequestBody (DTO).
         * Ejemplo: campos nulos u obligatorios en un objeto JSON
         */
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

                String message = ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(error -> error.getField() + ": " + error.getDefaultMessage())
                        .findFirst()
                        .orElse("Error de validación");

                ErrorResponse error = ErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message(message)
                        .timestamp(LocalDateTime.now())
                        .build();

                return ResponseEntity.badRequest().body(error);
        }
}