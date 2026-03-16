package com.hackerrank.sample.exception;

/**
 * Excepcion personalizada para indicar que un recurso solicitado no existe.
 * Se utiliza comunmente cuando un identificador de producto no es encontrado
 * en la base de datos.
 */
public class ResourceNotFoundException extends RuntimeException {
    /**
     * Constructor que permite definir el mensaje de error especifico.
     * @param message Descripcion que indica que recurso no fue hallado.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}