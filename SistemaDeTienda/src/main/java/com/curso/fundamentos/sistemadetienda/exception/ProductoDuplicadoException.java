package com.curso.fundamentos.sistemadetienda.exception;

/**
 * Indica que ya existe un elemento del mismo tipo con el mismo nombre.
 */
public class ProductoDuplicadoException extends ReglaNegocioException {

    public ProductoDuplicadoException(String message) {
        super(message);
    }
}