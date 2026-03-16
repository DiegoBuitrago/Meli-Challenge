package com.hackerrank.sample.exception;

/**
 * Excepcion personalizada para gestionar peticiones de comparacion no validas.
 * Se lanza cuando los parametros de entrada no cumplen con los requisitos 
 * minimos de la logica de negocio.
 */
public class InvalidComparisonRequestException extends RuntimeException {
    /**
     * Constructor que recibe un mensaje detallado sobre el error de validacion.
     * @param message Descripcion del motivo por el cual la peticion es invalida.
     */
    public InvalidComparisonRequestException(String message) {
        super(message);
    }
}