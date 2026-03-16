package com.hackerrank.sample.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que define la estructura estandar de las respuestas de error.
 * Se utiliza para retornar mensajes claros y consistentes al cliente
 * cuando ocurre una excepcion en el sistema.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    /**
     * Codigo de estado HTTP asociado al error.
     */
    private int status;

    /**
     * Mensaje detallado que describe la naturaleza del error.
     */
    private String message;

    /**
     * Fecha y hora exacta en la que se produjo el error.
     */
    private LocalDateTime timestamp;
}