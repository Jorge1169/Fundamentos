package com.curso.fundamentos.sistemadetienda.exception;

/**
 * Indica que un dato ingresado no cumple una regla de negocio.
 */
public class DatoInvalidoException extends ReglaNegocioException {

    public DatoInvalidoException(String message) {
        super(message);
    }
}