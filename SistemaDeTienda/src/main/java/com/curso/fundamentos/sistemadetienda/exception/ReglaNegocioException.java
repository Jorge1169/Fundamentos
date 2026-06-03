package com.curso.fundamentos.sistemadetienda.exception;

/**
 * Base para los errores controlados del dominio de la tienda.
 */
public class ReglaNegocioException extends Exception {

    public ReglaNegocioException(String message) {
        super(message);
    }
}