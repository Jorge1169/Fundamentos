package com.curso.refactor.enums;

import com.curso.refactor.exception.RecursoInvalidoException;

public enum FormatoDocumento {
    PDF,
    EPUB;

    public static FormatoDocumento desdeTexto(String valor) throws RecursoInvalidoException {
        try {
            return FormatoDocumento.valueOf(valor.trim().toUpperCase());
        } catch (Exception e) {
            throw new RecursoInvalidoException("El formato de documento solo puede ser PDF o EPUB.");
        }
    }
}

